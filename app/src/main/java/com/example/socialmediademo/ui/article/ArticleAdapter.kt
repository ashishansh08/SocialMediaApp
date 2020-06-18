package com.example.socialmediademo.ui.article

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.example.socialmediademo.R
import com.example.socialmediademo.convertNumber
import com.example.socialmediademo.models.Articles
import com.example.socialmediademo.models.Media
import kotlinx.android.synthetic.main.item_article.view.*


class ArticleAdapter (private  val context: Context,
                      private val mArticleList: ArrayList<Articles>,
                      private val mRequestManager:RequestManager,
                      private val onArticleClick: OnArticleClick) : RecyclerView.Adapter<ArticleAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_article, parent, false))
    }

    override fun getItemCount(): Int {
        return mArticleList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        setDataToViews(holder, mRequestManager, position)
    }

    private fun setDataToViews(holder: ViewHolder, requestManager: RequestManager, position: Int) {
        val userData = mArticleList[position].user
        val mediaData = mArticleList[position].media

        if (mediaData.isNullOrEmpty().not()) {
            setMediaImage(mediaData, requestManager, holder.imageViewArticleImage)
            setDataToTextView(mediaData?.get(0)?.title, holder.textViewArticleTitle)
            setDataToTextView(mediaData?.get(0)?.url, holder.textViewArticleUrl)
        }

        if(userData.isNullOrEmpty().not()){
            setDataToTextView(userData?.get(0)?.name, holder.textViewArticleUserName)
            setDataToTextView(userData?.get(0)?.designation, holder.textViewArticleDesignation
            )
            if (userData?.get(0)?.avatar.isNullOrBlank().not()) {
                requestManager.load(userData?.get(0)?.avatar).into(holder.imageViewArticleProfilePic)
            }
        }

        setDataToTextView(mArticleList[position].content, holder.textViewArticleComment)
        setDataToTextView(mArticleList[position].likes, holder.textViewArticleLikesCount, true, context.getString(R.string.likes))
        setDataToTextView(mArticleList[position].comments, holder.textViewArticleCommentCount, true, context.getString(R.string.comments))
    }



    private fun setMediaImage(mediaData: ArrayList<Media>?, requestManager: RequestManager, imageViewArticleImage: ImageView) {
        if (mediaData.isNullOrEmpty().not()) {
            requestManager.load(mediaData?.get(0)?.image).into(imageViewArticleImage)
        } else {
            imageViewArticleImage.visibility = View.GONE
        }
    }

    private fun setDataToTextView(data: String?, textView: TextView, isForCount: Boolean=false, postFixString:String="") {
        if (data.isNullOrBlank().not()) {
            if (isForCount) {
                textView.text = data?.toLong()?.convertNumber().plus(" $postFixString")
            }else{
                textView.text = data
            }
        }else{
            textView.visibility =View.GONE
        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageViewArticleProfilePic = view.imageViewArticleProfilePic
        val textViewArticleUserName = view.textViewArticleUserName
        val textViewArticleDesignation = view.textViewArticleDesignation
        val textViewTime=view.textViewTime
        val imageViewArticleImage=view.imageViewArticleImage
        val textViewArticleComment=view.textViewArticleComment
        val textViewArticleUrl=view.textViewArticleUrl
        val textViewArticleTitle=view.textViewArticleTitle
        val textViewArticleLikesCount=view.textViewArticleLikesCount
        val textViewArticleCommentCount=view.textViewArticleCommentCount
    }

    interface OnArticleClick{
        fun onArticleItemClicked(languageKey:String, languageValue:String)
    }
}

