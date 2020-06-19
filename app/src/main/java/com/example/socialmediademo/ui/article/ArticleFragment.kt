package com.example.socialmediademo.ui.article

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.RequestManager
import com.example.socialmediademo.R
import com.example.socialmediademo.common.AppConstants
import com.example.socialmediademo.common.ViewModelProviderFactory
import com.example.socialmediademo.common.isInternetAvailable
import com.example.socialmediademo.common.listener.OnListItemClickListener
import com.example.socialmediademo.common.listener.OnLoadMoreListener
import com.example.socialmediademo.models.Articles
import com.example.socialmediademo.models.Users
import com.example.socialmediademo.ui.users.UserAdapter
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_articles.*
import kotlinx.android.synthetic.main.fragment_users.*
import javax.inject.Inject

class ArticleFragment : DaggerFragment(), OnListItemClickListener, OnLoadMoreListener {

    @Inject
    lateinit var mViewModelProviderFactory: ViewModelProviderFactory
    @Inject
    lateinit var mName: String
    @Inject
    lateinit var mRequestManager: RequestManager

    private var mView: View? = null
    private var mViewModel: ArticleViewModel? = null
    private var mArticleList: ArrayList<Articles>? = ArrayList()
    private var mArticleAdapter: ArticleAdapter? = null
    private lateinit var mLinearLayoutManager: LinearLayoutManager
    private var mPageIndexCount = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (mView == null) {
            mView = inflater.inflate(R.layout.fragment_articles, container, false)
        }
        return mView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("Try:", mName)
        mViewModel = ViewModelProvider(this, mViewModelProviderFactory).get<ArticleViewModel>(ArticleViewModel::class.java)
        getArticlesFromApi()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initObserver()
    }


    //This will be called when we scrolled to some position and we want to load the next data.
    override fun onLoadMore(position: Int) {
        getArticlesFromApi()
    }

    //OnClick of list item, this interface will be fired.
    override fun onUserItemClicked(position: Int) {
        /*mArticleList?.let {
            val bundle = bundleOf(AppConstants.KEY to it[position])
            mView?.findNavController()
                ?.navigate(R.id.action_navigation_dashboard_to_navigation_notifications, bundle)
        }*/
    }


    //Call API for user data.
    private fun getArticlesFromApi(){
        if (isInternetAvailable(requireActivity())) {
            mPageIndexCount++
            mViewModel?.getArticle(mPageIndexCount, AppConstants.LIMIT)
        }else{
            if (mArticleList.isNullOrEmpty()){
//                mArticleList = mViewModel?.getUsersFromDb() as ArrayList<Users>
//                setUI()
            }
        }
    }


    //set observer which will keep observing for data.
    private fun initObserver() {
        mViewModel?.mutableList?.observe(requireActivity(), Observer { it ->
            if (it.isNullOrEmpty().not()) {
                mArticleList?.addAll(it)
                setArticleAdapter()
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
}