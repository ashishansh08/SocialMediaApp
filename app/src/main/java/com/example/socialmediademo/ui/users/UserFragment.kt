package com.example.socialmediademo.ui.users

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.RequestManager
import com.example.socialmediademo.ViewModelProviderFactory
import com.example.socialmediademo.models.Users
import dagger.android.support.DaggerFragment
import javax.inject.Inject
import androidx.navigation.findNavController
import com.example.socialmediademo.R

class UserFragment: DaggerFragment(), UserAdapter.OnUserItemClick{

    @Inject
    lateinit var mViewModelProviderFactory: ViewModelProviderFactory
    @Inject
    lateinit var mName:String
    @Inject
    lateinit var mRequestManager: RequestManager

    private var mView: View? = null
    private var mViewModel: UserViewModel? = null
    private var mUsersList : ArrayList<Users>? = ArrayList()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (mView == null) {
            mView = inflater.inflate(R.layout.fragment_users, container, false)
        }
        return mView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("Try:", mName)
        mViewModel = ViewModelProviders.of(this, mViewModelProviderFactory).get<UserViewModel>(
            UserViewModel::class.java)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initObserver()
        mViewModel?.getUsers(1,5)
    }


    override fun onUserItemClicked(pos: Int) {
        mUsersList.let {
            val bundle= bundleOf("key" to it!![pos])
            mView?.findNavController()?.navigate(R.id.action_navigation_dashboard_to_navigation_notifications,bundle)
        }
    }

    private fun initObserver() {
        mViewModel?.mutableList?.observe(requireActivity() , Observer { it ->
            if (it.isNullOrEmpty().not()) {
                mUsersList?.addAll(it)
                setUsersAdapter(it)
            }
        })
    }

    private fun setUsersAdapter(userList: ArrayList<Users>) {
        val layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        val mRecyclerViewUsers = mView?.findViewById(R.id.recyclerViewUsers) as androidx.recyclerview.widget.RecyclerView
        mRecyclerViewUsers.addItemDecoration(DividerItemDecoration(this.activity, LinearLayout.VERTICAL))
        mRecyclerViewUsers.layoutManager = layoutManager
        mRecyclerViewUsers.adapter = UserAdapter(requireActivity(), userList, mRequestManager, this)
    }
}
