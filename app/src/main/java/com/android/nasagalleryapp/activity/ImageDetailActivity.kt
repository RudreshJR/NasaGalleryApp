package com.android.nasagalleryapp.activity

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.ViewPager
import com.android.nasagalleryapp.BaseActivity
import com.android.nasagalleryapp.R
import com.android.nasagalleryapp.adapter.AdapterImageDetail
import com.android.nasagalleryapp.databinding.ActivityImageDetailBinding
import com.android.nasagalleryapp.model.DetailDataResponseItem
import com.android.nasagalleryapp.utils.AppUtils
import com.android.nasagalleryapp.viewmodel.ViewModelImageData


class ImageDetailActivity : BaseActivity() {
    private lateinit var binding: ActivityImageDetailBinding
    private var pager: ViewPager? = null
    private lateinit var viewModelImageData: ViewModelImageData
    private var adapterImageDetail: AdapterImageDetail? = null
    var position = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityImageDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModelImageData = ViewModelProvider(this)[ViewModelImageData::class.java]
        if (intent != null) {
            position =
                intent.getIntExtra("IMAGE_POSITION", 0)
        }
        initToolbar()
        initViews()
    }

    private fun initViews() {
        pager = binding.pagerContainer.viewPager
        adapterImageDetail = AdapterImageDetail(this)
        getData()
    }

    private fun initToolbar() {
        binding.toolbar.toolbarTitle.text = getString(R.string.imageDetail)
        binding.toolbar.toolbarBackArrow.setOnClickListener {
            onBackPressed()
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
            this,
            Observer<List<DetailDataResponseItem>> { imageList ->
                adapterImageDetail?.setData(imageList.reversed())
                pager?.adapter = adapterImageDetail
                //Necessary or the pager will only have one extra page to show
                // make this at least however many pages you can see
                pager?.offscreenPageLimit = adapterImageDetail?.count!!
                //A little space between pages
                pager?.pageMargin = 15
                //If hardware acceleration is enabled, you should also remove
                // clipping on the pager for its children.
                pager?.clipChildren = false
                pager?.setCurrentItem(position, true)
                dismissProgress()
            })

    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(
            R.anim.slide_in_left,
            R.anim.slide_out_right
        )
    }
}