package com.example.socialmediademo.ui.article

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.example.socialmediademo.R
import com.example.socialmediademo.common.convertNumber
import com.example.socialmediademo.common.listener.OnListItemClickListener
import com.example.socialmediademo.common.listener.OnLoadMoreListener
import com.example.socialmediademo.models.Articles
import com.example.socialmediademo.models.Media
import kotlinx.android.synthetic.main.item_article.view.*


class ArticleAdapter (private  val mContext: Context,
                      private var mArticleList: ArrayList<Articles>,
                      private val mRequestManager:RequestManager,
                      private val mOnListItemClickListener: OnListItemClickListener,
                      private val mOnLoadMoreListener: OnLoadMoreListener
) : RecyclerView.Adapter<ArticleAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_article, parent, false))
    }

    override fun getItemCount(): Int {
        return mArticleList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        setDataToViews(holder, mRequestManager, position)
        setListeners(holder, position)
    }

    private fun setListeners(holder: ViewHolder, position: Int) {
        holder.layoutArticleMain.setOnClickListener {
            mOnListItemClickListener.onUserItemClicked(position)
        }

        if (position == mArticleList.size-1) {
            mOnLoadMoreListener.onLoadMore(position)
        }
    }

    private fun setDataToViews(holder: ViewHolder, requestManager: RequestManager, position: Int) {
        val userData = mArticleList[position].user
        val mediaData = mArticleList[position].media

        setMediaRelatedDataToViews(mediaData, holder)

        if(userData.isNullOrEmpty().not()){
            setDataToTextView(userData?.get(0)?.name, holder.textViewArticleUserName)
            setDataToTextView(userData?.get(0)?.designation, holder.textViewArticleDesignation
            )
            if (userData?.get(0)?.avatar.isNullOrBlank().not()) {
                requestManager.load(userData?.get(0)?.avatar).into(holder.imageViewArticleProfilePic)
            }
        }

        setDataToTextView(mArticleList[position].content, holder.textViewArticleComment)
        setDataToTextView(mArticleList[position].likes, holder.textViewArticleLikesCount, true, mContext.getString(R.string.likes))
        setDataToTextView(mArticleList[position].comments, holder.textViewArticleCommentCount, true, mContext.getString(R.string.comments))
        holder.textViewTime.text=mArticleList[position].id.toString()
    }

    private fun setMediaRelatedDataToViews(mediaData: ArrayList<Media>?, holder: ViewHolder) {
        if (mediaData.isNullOrEmpty().not()) {
            setViewVisibilty(holder, View.VISIBLE)
            mRequestManager.load(mediaData?.get(0)?.image).into(holder.imageViewArticleImage)
            setDataToTextView(mediaData?.get(0)?.title, holder.textViewArticleTitle)
            setDataToTextView(mediaData?.get(0)?.url, holder.textViewArticleUrl)
        }else{
            holder.imageViewArticleImage.visibility=View.GONE
            holder.textViewArticleTitle.visibility=View.GONE
            setViewVisibilty(holder, View.GONE)
        }
    }

    private fun setViewVisibilty(holder: ViewHolder, visibility: Int) {
        holder.imageViewArticleImage.visibility=visibility
        holder.textViewArticleTitle.visibility=visibility
        holder.textViewArticleUrl.visibility=visibility
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

    fun updateList(articlesList: ArrayList<Articles>) {
        mArticleList = articlesList
        notifyDataSetChanged()
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
        val layoutArticleMain=view.layoutArticleMain
    }
}

