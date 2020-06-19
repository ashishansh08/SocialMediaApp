package com.example.socialmediademo.ui.users

import android.os.Bundle
import android.provider.SyncStateContract
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.RequestManager
import com.example.socialmediademo.ViewModelProviderFactory
import com.example.socialmediademo.models.Users
import dagger.android.support.DaggerFragment
import javax.inject.Inject
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.socialmediademo.AppConstants
import com.example.socialmediademo.R
import kotlinx.android.synthetic.main.fragment_users.*

class UserFragment: DaggerFragment(), UserAdapter.OnUserItemClick, UserAdapter.OnLoadMoreListener {

    @Inject
    lateinit var mViewModelProviderFactory: ViewModelProviderFactory
    @Inject
    lateinit var mName: String
    @Inject
    lateinit var mRequestManager: RequestManager

    private var mView: View? = null
    private var mViewModel: UserViewModel? = null
    private var mUsersList: ArrayList<Users>? = ArrayList()
    private var mUserAdapter: UserAdapter? = null
    private lateinit var mLinearLayoutManager: LinearLayoutManager
    private var mPageIndexCount = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (mView == null) {
            mView = inflater.inflate(R.layout.fragment_users, container, false)
        }
        return mView
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("Try the injected value:", mName)
        mViewModel = ViewModelProvider(this, mViewModelProviderFactory).get<UserViewModel>(UserViewModel::class.java)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initObserver()
        getUsersFromApi()
    }

    //This will be called when we scrolled to some position and we want to load the next data.
    override fun onLoadMore(position: Int) {
        getUsersFromApi()
    }

    //OnClick of list item, this interface will be fired.
    override fun onUserItemClicked(position: Int) {
        mUsersList?.let {
            val bundle = bundleOf(AppConstants.KEY to it[position])
            mView?.findNavController()
                ?.navigate(R.id.action_navigation_dashboard_to_navigation_notifications, bundle)
        }
    }

    //set observor which will keep observing for data.
    private fun initObserver() {
        mViewModel?.mutableList?.observe(requireActivity(), Observer { it ->
            if (it.isNullOrEmpty().not()) {
                mUsersList?.addAll(it)
                setUsersAdapter()
            }
        })
    }

    //Set adapter to recyclerview
    private fun setUsersAdapter() {
        mUsersList?.let {
        if (mUserAdapter == null) {
            mLinearLayoutManager  = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
            recyclerViewUsers.addItemDecoration(DividerItemDecoration(requireActivity(), LinearLayout.VERTICAL))
            recyclerViewUsers.layoutManager = mLinearLayoutManager
            mUserAdapter = UserAdapter(requireActivity(), it, mRequestManager, this, this)
            recyclerViewUsers.adapter = mUserAdapter
        }else{
            mUserAdapter?.updateList(it)
            }
        }
    }

    //Call API for user data.
    private fun getUsersFromApi(){
        mPageIndexCount++
        mViewModel?.getUsers(mPageIndexCount, AppConstants.LIMIT)
    }
}
