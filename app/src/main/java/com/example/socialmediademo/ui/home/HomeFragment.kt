package com.example.socialmediademo.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.socialmediademo.R
import com.example.socialmediademo.ViewModelProviderFactory
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class HomeFragment : DaggerFragment(){
    private var mView: View? = null
    var viewModel: HomeViewModel? = null

    @Inject
    lateinit var viewModelProviderFactory: ViewModelProviderFactory

    @Inject
    lateinit var name:String


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        if (mView == null) {
            mView = inflater.inflate(R.layout.fragment_home, container, false)
        }
        return mView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("aaaaaPost", name)
        viewModel = ViewModelProviders.of(this, viewModelProviderFactory).get<HomeViewModel>(HomeViewModel::class.java)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initObserver()
        viewModel?.getUser(2)
    }


    private fun initObserver() {
        viewModel?.mutableList?.observe(requireActivity() , Observer { it ->
            Toast.makeText(requireActivity(), "RESPONSE : LIST SIZE : "+ it?.size.toString(), Toast.LENGTH_LONG).show()
        })
    }
}


/*

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        val textView: TextView = root.findViewById(R.id.text_home)
       */
/* homeViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
*//*

        Toast.makeText(requireActivity(), "Home", Toast.LENGTH_SHORT).show()
        return root
    }
}
*/
