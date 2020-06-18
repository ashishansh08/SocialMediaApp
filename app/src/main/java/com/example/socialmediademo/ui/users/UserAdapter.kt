package com.example.socialmediademo.ui.users

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.example.socialmediademo.R
import com.example.socialmediademo.models.Media
import com.example.socialmediademo.models.Users
import kotlinx.android.synthetic.main.item_users.view.*


class UserAdapter (private  val context: Context,
                   private val mUsersList: ArrayList<Users>,
                   private val mRequestManager:RequestManager,
                   private val mOnUserItemClick: OnUserItemClick) : RecyclerView.Adapter<UserAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_users, parent, false))
    }

    override fun getItemCount(): Int {
        return mUsersList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        setDataToViews(holder, mRequestManager, position)
    }

    private fun setDataToViews(holder: ViewHolder, requestManager: RequestManager, position: Int) {
        setMediaImage(mUsersList[position].avatar, requestManager, holder.imageViewUserProfilePic)
    }

    private fun setMediaImage(mediaUrl: String?, requestManager: RequestManager, imageViewArticleImage: ImageView) {
        if (mediaUrl.isNullOrBlank().not()) {
            requestManager.load(mediaUrl).into(imageViewArticleImage)
        } else {
            imageViewArticleImage.visibility = View.GONE
        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageViewUserProfilePic = view.imageViewUserProfilePic
        val textViewUserName = view.textViewUserName
        val textViewUserDesignation = view.textViewUserDesignation
    }

    interface OnUserItemClick{
        fun onUserItemClicked(languageKey:String, languageValue:String)
    }
}

