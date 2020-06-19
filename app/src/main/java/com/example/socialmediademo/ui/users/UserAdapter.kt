package com.example.socialmediademo.ui.users

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.example.socialmediademo.R
import com.example.socialmediademo.common.listener.OnLoadMoreListener
import com.example.socialmediademo.common.listener.OnListItemClickListener
import com.example.socialmediademo.models.Users
import com.example.socialmediademo.common.setMediaImage
import com.example.socialmediademo.common.setDataToTextView
import kotlinx.android.synthetic.main.item_users.view.*

class UserAdapter (private  val context: Context,
                   private var mUsersList: ArrayList<Users>,
                   private val mRequestManager:RequestManager,
                   private val mOnListItemClickListener: OnListItemClickListener,
                   private val mOnLoadMoreListener: OnLoadMoreListener
) : RecyclerView.Adapter<UserAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_users, parent, false))
    }

    override fun getItemCount(): Int {
        return mUsersList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        setDataToViews(holder, mRequestManager, position)
        holder.layoutUserMain.setOnClickListener {
            mOnListItemClickListener.onUserItemClicked(position)
        }

        if (position == mUsersList.size-1) {
            mOnLoadMoreListener.onLoadMore(position)
        }
    }

    private fun setDataToViews(holder: ViewHolder, requestManager: RequestManager, position: Int) {
        holder.imageViewUserProfilePic.setMediaImage(mUsersList[position].avatar, requestManager)
        holder.textViewUserName.setDataToTextView(mUsersList[position].name)
        holder.textViewUserDesignation.setDataToTextView(mUsersList[position].designation)
        holder.textViewUserCity.setDataToTextView(mUsersList[position].city)
    }

    fun updateList(userList: ArrayList<Users>) {
        mUsersList = userList
        notifyDataSetChanged()
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageViewUserProfilePic = view.imageViewUserProfilePic
        val textViewUserName = view.textViewUserName
        val textViewUserDesignation = view.textViewUserDesignation
        val textViewUserCity = view.textViewUserCity
        val layoutUserMain = view.layoutUserMain
    }
}

