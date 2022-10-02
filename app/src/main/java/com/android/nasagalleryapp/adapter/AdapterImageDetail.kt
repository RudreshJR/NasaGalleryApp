package com.android.nasagalleryapp.adapter

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.text.SpannableString
import android.text.Spanned
import android.text.style.StyleSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import androidx.viewpager.widget.PagerAdapter
import com.android.nasagalleryapp.R
import com.android.nasagalleryapp.model.DetailDataResponseItem
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget


class AdapterImageDetail(private var mContext: Context) : PagerAdapter() {
    private var mLayoutInflater: LayoutInflater =
        mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    private var responseItemList: List<DetailDataResponseItem>? = null

    fun setData(
        imageDataList: List<DetailDataResponseItem>
    ) {
        this.responseItemList = imageDataList
        notifyDataSetChanged()
    }

    override fun getCount(): Int {
        if (responseItemList != null) {
            return responseItemList!!.size
        }
        return 0
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object` as ScrollView
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val itemView: View = mLayoutInflater.inflate(R.layout.pager_item, container, false)
        val imageView: ImageView = itemView.findViewById<View>(R.id.nasa_imageView) as ImageView
        val titleTextView: TextView = itemView.findViewById<View>(R.id.title) as TextView
        val descTextView: TextView = itemView.findViewById<View>(R.id.description) as TextView
        val dateTextView: TextView = itemView.findViewById<View>(R.id.date) as TextView
        val copyRightTextView: TextView = itemView.findViewById<View>(R.id.copyRight) as TextView



        titleTextView.text = responseItemList?.get(position)?.title ?: ""
        descTextView.text = responseItemList?.get(position)?.explanation ?: ""

        if (responseItemList?.get(position)?.date != null) {
            val ss = SpannableString( "${mContext.getString(R.string.date)} ${responseItemList?.get(position)?.date}")
            val boldSpan = StyleSpan(Typeface.BOLD)
            ss.setSpan(boldSpan, 0, 5, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            dateTextView.text = ss
        }

        if (responseItemList?.get(position)?.copyright != null) {

            val ss = SpannableString( "${mContext.getString(R.string.copyright)} ${responseItemList?.get(position)?.copyright}")
            val boldSpan = StyleSpan(Typeface.BOLD)
            ss.setSpan(boldSpan, 0, 11, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            copyRightTextView.text = ss
        }
        Glide.with(mContext)
            .asBitmap()
            .load(
                responseItemList?.get(
                    position
                )!!.url!!
            )
            .skipMemoryCache(true)
            .into(object : CustomTarget<Bitmap?>() {
                override fun onResourceReady(
                    resource: Bitmap,
                    transition: com.bumptech.glide.request.transition.Transition<in Bitmap?>?
                ) {
                    imageView.setImageBitmap(resource)
                }

                override fun onLoadCleared(placeholder: Drawable?) {
                }
            })

        container.addView(itemView)
        return itemView
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as LinearLayout)
    }
}
