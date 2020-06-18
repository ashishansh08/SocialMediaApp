package com.example.socialmediademo.ui.article

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.RequestManager
import com.example.socialmediademo.R
import com.example.socialmediademo.ViewModelProviderFactory
import com.example.socialmediademo.models.Articles
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class ArticleFragment : DaggerFragment(), ArticleAdapter.OnArticleClick{

    @Inject
    lateinit var mViewModelProviderFactory: ViewModelProviderFactory
    @Inject
    lateinit var mName:String
    @Inject
    lateinit var mRequestManager: RequestManager

    private var mView: View? = null
    private var mViewModel: ArticleViewModel? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (mView == null) {
            mView = inflater.inflate(R.layout.fragment_home, container, false)
        }
        return mView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("Try:", mName)
        mViewModel = ViewModelProviders.of(this, mViewModelProviderFactory).get<ArticleViewModel>(ArticleViewModel::class.java)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initObserver()
        mViewModel?.getUser(2)
    }


    private fun initObserver() {
        mViewModel?.mutableList?.observe(requireActivity() , Observer { it ->
            Toast.makeText(requireActivity(), "RESPONSE : LIST SIZE : "+ it?.size.toString(), Toast.LENGTH_LONG).show()
            if (it.isNullOrEmpty().not()) {
                setChangeLanguageAdapter(it)
            }
        })
    }

    private fun setChangeLanguageAdapter(articleList: ArrayList<Articles>) {
        val layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        val mRecyclerViewArticle = mView?.findViewById(R.id.recyclerViewArticles) as androidx.recyclerview.widget.RecyclerView
        mRecyclerViewArticle.addItemDecoration(DividerItemDecoration(this.activity, LinearLayout.VERTICAL))
        mRecyclerViewArticle.layoutManager = layoutManager
        mRecyclerViewArticle.adapter = ArticleAdapter(requireActivity(), articleList, mRequestManager, this)
    }

    override fun onArticleItemClicked(languageKey: String, languageValue: String) {
        //DO nothing for now
    }
}