package com.android.nasagalleryapp.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.android.nasagalleryapp.R
import com.android.nasagalleryapp.model.DetailDataResponse
import com.android.nasagalleryapp.model.DetailDataResponseItem
import com.android.nasagalleryapp.utils.AppUtils
import com.google.gson.Gson

class ViewModelImageData(application: Application) : AndroidViewModel(application) {

    private val TAG = "ViewModelImageData"
    private var mContext: Context

    var getImagesListLiveData: MutableLiveData<List<DetailDataResponseItem>> =
        MutableLiveData<List<DetailDataResponseItem>>()

    init {
        mContext = application
    }


    fun getImageDataList() {
        val myJson = AppUtils.inputStreamToString(mContext.resources.openRawResource(R.raw.data))
        val detailDataResponse: DetailDataResponse = Gson().fromJson(myJson, DetailDataResponse::class.java)
        val imageDetailList = detailDataResponse?.detailDataResponseItemList as MutableList<DetailDataResponseItem>
        getImagesListLiveData.postValue(imageDetailList)

    }


}