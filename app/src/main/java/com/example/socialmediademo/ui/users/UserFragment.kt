package com.example.socialmediademo.ui .users

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
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.RequestManager
import com.example.socialmediademo.common.ViewModelProviderFactory
import com.example.socialmediademo.models.Users
import javax.inject.Inject
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.socialmediademo.common.AppConstants
import com.example.socialmediademo.R
import com.example.socialmediademo.common.isInternetAvailable
import com.example.socialmediademo.common.listener.OnLoadMoreListener
import com.example.socialmediademo.common.listener.OnListItemClickListener
import com.example.socialmediademo.db.UserDao
import com.example.socialmediademo.ui.BaseFragment

class UserFragment: BaseFragment(), OnListItemClickListener, OnLoadMoreListener {

    @Inject
    lateinit var mViewModelProviderFactory: ViewModelProviderFactory
    @Inject
    lateinit var mName: String
    @Inject
    lateinit var mRequestManager: RequestManager
    @Inject
    lateinit var mUserDao:UserDao

    private var mView: View? = null
    private var mViewModel: UserViewModel? = null
    private var mUsersList: ArrayList<Users>? = ArrayList()
    private var mUserAdapter: UserAdapter? = null
    private lateinit var mLinearLayoutManager: LinearLayoutManager
    private var mPageIndexCount = 1
    private var mIsFirstTime=true
    var networkReceiver:NetworkReceiver= NetworkReceiver()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (mView == null) {
            mView = inflater.inflate(R.layout.fragment_users, container, false)
            mViewModel = ViewModelProvider(this, mViewModelProviderFactory).get<UserViewModel>(UserViewModel::class.java)
            initObserver()
        }
        return mView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mIsFirstTime = true
        Log.d("Try the injected value:", mName)
        getUsersFromApi()
    }

    override fun onResume() {
        super.onResume()
        val intentFilter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        requireActivity().registerReceiver(networkReceiver, intentFilter)
        mIsFirstTime=true
    }
    override fun onPause() {
        super.onPause()
        requireActivity().unregisterReceiver(networkReceiver);
    }

    //This will be called when we scrolled to some position and we want to load the next data.
    override fun onLoadMore(position: Int) {
        if(mUsersList?.size?.rem(AppConstants.LIMIT)==0) {
                getUsersFromApi()
        }
    }

    //OnClick of list item, this interface will be fired.
    override fun onItemClicked(position: Int) {
        mUsersList?.let {
            val bundle = bundleOf(AppConstants.KEY to it[position])
            mView?.findNavController()
                ?.navigate(R.id.action_navigation_dashboard_to_navigation_notifications, bundle)
        }
    }

    override fun isInternetChanged() {
        if (!mIsFirstTime) {
            showAlertDialog()
        }
        mIsFirstTime=false
    }

    private fun showAlertDialog() {
        val dialogBuilder = AlertDialog.Builder(requireActivity())
        dialogBuilder.setMessage(requireActivity().getString(R.string.internet_status_change))
            .setCancelable(false)
            .setPositiveButton(requireActivity().getString(R.string.message_ok)) { dialog, id ->
                if (isInternetAvailable(requireActivity())){
                    getUsersFromApi()
                }
                dialog.dismiss()
            }

        val alert = dialogBuilder.create()
        alert.setTitle(requireActivity().getString(R.string.app_name))
        alert.show()
    }

    //Set adapter to recyclerview
    private fun setUsersAdapter() {
        mUsersList?.let {
        if (mUserAdapter == null) {
            mLinearLayoutManager  = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
            val recyclerView= mView?.findViewById<RecyclerView>(R.id.recyclerViewUsers)
            recyclerView?.addItemDecoration(DividerItemDecoration(requireActivity(), LinearLayout.VERTICAL))
            recyclerView?.layoutManager = mLinearLayoutManager
            mUserAdapter = UserAdapter(requireActivity(), it, mRequestManager, this, this)
            recyclerView?.adapter = mUserAdapter
        }else{
            mUserAdapter?.updateList(it)
            }
        }
    }

    /**
     * Observer to observe the data,
     * delete all the previously loaded data whose id>mUserList.size else it will always show more then previously old loaded data when get from db
     * Insert the new list data.
     * */
    private fun initObserver() {
        mViewModel?.mutableUsersList?.observe(requireActivity(), Observer { it ->
            if (it.isNullOrEmpty().not()) {
                mUsersList?.let { userList -> mViewModel?.deleteAllAfterId(userList.size!!) }
                mUsersList?.addAll(it)
                setUsersAdapter()
                mUsersList?.let { userList -> mViewModel?.insertUser(mUsersList!!) }
                setUI()
            }
        })
    }

    /**
     * Call API and get data if internet available
     * else
     * get data from db and set it to adapter
    * */
    private fun getUsersFromApi(){
        if (isInternetAvailable(requireActivity())) {
            if(mUsersList.isNullOrEmpty().not()) {
                mPageIndexCount = (mUsersList?.size!! / AppConstants.LIMIT)+1
            }
            mViewModel?.getUsers(mPageIndexCount, AppConstants.LIMIT)
        }else{
            if (mUsersList.isNullOrEmpty()){
                mViewModel?.getUsersFromDb()?.let {
                    mUsersList= it as ArrayList<Users>
                    setUI()
                    setUsersAdapter()
                }
            }
        }
    }

    private fun setUI() {
        if (mUsersList.isNullOrEmpty()){
            mView?.findViewById<TextView>(R.id.textViewUserNoRecordFound)?.visibility=View.VISIBLE
            mView?.findViewById<RecyclerView>(R.id.recyclerViewUsers)?.visibility=View.GONE
        }else{
            mView?.findViewById<TextView>(R.id.textViewUserNoRecordFound)?.visibility=View.GONE
            mView?.findViewById<RecyclerView>(R.id.recyclerViewUsers)?.visibility=View.VISIBLE
        }
    }
}
