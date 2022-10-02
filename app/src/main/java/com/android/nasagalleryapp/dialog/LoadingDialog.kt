package com.android.nasagalleryapp.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Window
import com.android.nasagalleryapp.R

class LoadingDialog (context: Context) : Dialog(context) {
    init {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE)
        this.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        this.setCancelable(false)
        this.setCanceledOnTouchOutside(false)
        this.setContentView(R.layout.loading_dialog)
    }
}