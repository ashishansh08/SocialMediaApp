package com.example.socialmediademo.ui.article

import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.example.socialmediademo.R
import com.example.socialmediademo.common.AppConstants
import com.example.socialmediademo.common.ViewModelProviderFactory
import com.example.socialmediademo.common.isInternetAvailable
import com.example.socialmediademo.common.listener.OnListItemClickListener
import com.example.socialmediademo.common.listener.OnLoadMoreListener
import com.example.socialmediademo.db.ArticleDao
import com.example.socialmediademo.db.UserDao
import com.example.socialmediademo.models.Articles
import com.example.socialmediademo.models.Users
import com.example.socialmediademo.ui.BaseFragment
import com.example.socialmediademo.ui.users.UserAdapter
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_articles.*
import kotlinx.android.synthetic.main.fragment_users.*
import javax.inject.Inject

class ArticleFragment : BaseFragment(), OnListItemClickListener, OnLoadMoreListener {

    @Inject
    lateinit var mViewModelProviderFactory: ViewModelProviderFactory
    @Inject
    lateinit var mName: String
    @Inject
    lateinit var mRequestManager: RequestManager
    @Inject
    lateinit var mArticleDao: ArticleDao

    private var mView: View? = null
    private var mViewModel: ArticleViewModel? = null
    private var mArticleList: ArrayList<Articles>? = ArrayList()
    private var mArticleAdapter: ArticleAdapter? = null
    private lateinit var mLinearLayoutManager: LinearLayoutManager
    private var mPageIndexCount = 1
    private var mIsFirstTime=true
    var mNetworkReceiver: NetworkReceiver = NetworkReceiver()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (mView == null) {
            mView = inflater.inflate(R.layout.fragment_articles, container, false)
            mViewModel = ViewModelProvider(this, mViewModelProviderFactory).get<ArticleViewModel>(ArticleViewModel::class.java)
            initObserver()
        }
        return mView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("Try:", mName)
        mIsFirstTime = true
        getArticlesFromApi()
    }

    override fun onResume() {
        super.onResume()
        val intentFilter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        requireActivity().registerReceiver(mNetworkReceiver, intentFilter)
    }
    override fun onPause() {
        super.onPause()
        requireActivity().unregisterReceiver(mNetworkReceiver);
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    //This will be called when we scrolled to some position and we want to load the next data.
    override fun onLoadMore(position: Int) {
        if(mArticleList?.size?.rem(AppConstants.LIMIT)==0) {
            getArticlesFromApi()
        }
    }

    //OnClick of list item, this interface will be fired.
    override fun onUserItemClicked(position: Int) {
        //Do nothing
    }

    override fun isInternetChanged() {
        if (!mIsFirstTime) {
            showAlertDialog()
        }
        mIsFirstTime=false
    }

    //Call API for user data.
    private fun getArticlesFromApi(){
        if (isInternetAvailable(requireActivity())) {
            if(mArticleList.isNullOrEmpty().not()) {
                mPageIndexCount = (mArticleList?.size!! / AppConstants.LIMIT)+1
            }
            mViewModel?.getArticle(mPageIndexCount, AppConstants.LIMIT)
        }else{
            if (mArticleList.isNullOrEmpty()){
                mViewModel?.getArticlesFromDb()?.let {
                    mArticleList= it as ArrayList<Articles>
                    setUI()
                    setArticleAdapter()
                }
            }
        }
    }

    //set observer which will keep observing for data.
    private fun initObserver() {
        mViewModel?.mutableList?.observe(requireActivity(), Observer { it ->
            if (it.isNullOrEmpty().not()) {
                mArticleList?.let { userList -> mViewModel?.deleteAllAfterId(userList.size!!) }
                mArticleList?.addAll(it)
                setArticleAdapter()
                mArticleList?.let { mViewModel?.insertArticles(mArticleList!!) }
                setUI()
            }
        })

    }

    //Set user data to adapter and recyclerview.
    private fun setArticleAdapter() {
        mArticleList?.let {
            if (mArticleAdapter == null) {
                mLinearLayoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
                recyclerViewArticles?.addItemDecoration(DividerItemDecoration(requireActivity(), LinearLayout.VERTICAL))
                recyclerViewArticles?.layoutManager = mLinearLayoutManager
                mArticleAdapter = ArticleAdapter(requireActivity(), it, mRequestManager, this, this)
                recyclerViewArticles?.adapter = mArticleAdapter
            }else{
                mArticleAdapter?.updateList(it)
            }
        }
    }

    private fun showAlertDialog() {
        val dialogBuilder = AlertDialog.Builder(requireActivity())
        dialogBuilder.setMessage(requireActivity().getString(R.string.internet_status_change))
            .setCancelable(false)
            .setPositiveButton(requireActivity().getString(R.string.message_ok)) { dialog, id ->
                if (isInternetAvailable(requireActivity())){
                    getArticlesFromApi()
                }
                dialog.dismiss()
            }

        val alert = dialogBuilder.create()
        alert.setTitle(requireActivity().getString(R.string.app_name))
        alert.show()
    }

    //Hide and show view accoring to data.
    private fun setUI() {
        if (mArticleList.isNullOrEmpty()){
            mView?.findViewById<TextView>(R.id.textViewArticleNoRecordFound)?.visibility=View.VISIBLE
            mView?.findViewById<RecyclerView>(R.id.recyclerViewArticles)?.visibility=View.GONE
        }else{
            mView?.findViewById<TextView>(R.id.textViewUserNoRecordFound)?.visibility=View.GONE
            mView?.findViewById<RecyclerView>(R.id.recyclerViewArticles)?.visibility=View.VISIBLE
        }
    }
}