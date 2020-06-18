package com.example.socialmediademo.ui.user_details

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.RequestManager
import com.example.socialmediademo.R
import com.example.socialmediademo.models.Users
import com.example.socialmediademo.setMediaImage
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_user_details.*
import javax.inject.Inject

class UserDetailsFragment : DaggerFragment() {

    @Inject
    lateinit var requestManager:RequestManager

    private var mUserDetails:Users?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mUserDetails = arguments?.getParcelable<Users>("key")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_user_details, container, false)
    }

    @SuppressLint("SetTextI18n")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        imageViewUserDerailsProfilePic.setMediaImage(mUserDetails?.avatar, requestManager)
        textViewUserDetailFullName.text = "${mUserDetails?.name}  ${mUserDetails?.lastname}"
        textViewUserDetailDesignation.text = mUserDetails?.designation
        textViewUserDetailCity.text = mUserDetails?.city
        textViewUserDetailComments.text = mUserDetails?.about
    }
}
