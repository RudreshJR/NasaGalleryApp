package com.android.nasagalleryapp

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.nasagalleryapp.dialog.LoadingDialog

open class BaseActivity : AppCompatActivity() {
    var progressDialog: LoadingDialog?=null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        progressDialog= LoadingDialog(this)
    }

    fun showProgress(){
        if (!isFinishing && progressDialog != null && !progressDialog?.isShowing!!) {
            progressDialog?.setTitle(getString(R.string.please_wait))
            progressDialog!!.setCancelable(false)
            progressDialog!!.setCanceledOnTouchOutside(false)
            progressDialog!!.show()
        }
    }

    fun showProgresswithMsg(msg: String){
        if(!isFinishing && progressDialog!=null ) {
            progressDialog?.setTitle(msg)
            progressDialog!!.setCancelable(false)
            progressDialog!!.setCanceledOnTouchOutside(false)
            progressDialog!!.show()
        }
    }

    fun dismissProgress(){
        if(!isFinishing && !isDestroyed) {
            if (progressDialog != null) {
                if (progressDialog!!.isShowing) {
                    progressDialog!!.dismiss()
                }
            }
        }
    }

    fun showNoInternetMessage(){
        if(!isFinishing) {
            Toast.makeText(this, getString(R.string.please_check_network), Toast.LENGTH_LONG).show()
        }
    }

    fun showToastMessage(msg: String){
        if(!isFinishing) {
            Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
        }
    }


}