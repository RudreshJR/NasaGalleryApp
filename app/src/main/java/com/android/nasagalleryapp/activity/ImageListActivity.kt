package com.android.nasagalleryapp.activity

import android.content.Intent
import android.os.Bundle
import android.view.animation.AnimationUtils
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.android.nasagalleryapp.BaseActivity
import com.android.nasagalleryapp.R
import com.android.nasagalleryapp.adapter.AdapterImageList
import com.android.nasagalleryapp.databinding.ActivityImageListBinding
import com.android.nasagalleryapp.utils.AppUtils
import com.android.nasagalleryapp.viewmodel.ViewModelImageData


class ImageListActivity : BaseActivity(), AdapterImageList.ItemClickListener {

    private lateinit var binding: ActivityImageListBinding
    private lateinit var viewModelImageData: ViewModelImageData
    private var adapterImageList: AdapterImageList? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        binding = ActivityImageListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModelImageData = ViewModelProvider(this)[ViewModelImageData::class.java]
        initToolbar()
        initViews()
    }

    private fun initViews() {
        binding.imageListRecyclerView.layoutManager = GridLayoutManager(this, 2)

        adapterImageList = AdapterImageList(this@ImageListActivity)
        adapterImageList?.setItemClickListener(this)
        getData()
    }


    private fun initToolbar() {
        binding.toolbar.toolbarTitle.text = getString(R.string.imageList)
        binding.toolbar.toolbarBackArrow.setOnClickListener {
            finish()
        }
    }

    private fun getData() {

        if (AppUtils.isNetConnected(this)) {
            showProgress()
            viewModelImageData.getImageDataList()
        } else {
            showNoInternetMessage()
        }

        viewModelImageData.getImagesListLiveData.observe(
            this
        ) { imageList ->
            adapterImageList?.setData(imageList.reversed())
            binding.imageListRecyclerView.adapter = adapterImageList
            binding.imageListRecyclerView.layoutAnimation =
                AnimationUtils.loadLayoutAnimation(this, R.anim.layout_anim)
            dismissProgress()
        }

    }

    override fun onImageItemClick(position: Int) {
        navigateToImageDetailActivity(position)

    }

    private fun navigateToImageDetailActivity(position: Int) {
        val intent = Intent(this, ImageDetailActivity::class.java)
        intent.putExtra("IMAGE_POSITION", position)
        startActivity(intent)
        overridePendingTransition(
            R.anim.slide_in_right,
            R.anim.slide_out_left
        )
    }

}