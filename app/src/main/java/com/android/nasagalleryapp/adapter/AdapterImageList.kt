package com.android.nasagalleryapp.adapter

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.android.nasagalleryapp.R
import com.android.nasagalleryapp.databinding.ImageListItemBinding
import com.android.nasagalleryapp.model.DetailDataResponseItem
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget

class AdapterImageList(private val context: Context) :
    RecyclerView.Adapter<AdapterImageList.ActivityHolder>() {

    private var itemClickListener: ItemClickListener? = null

    fun setItemClickListener(itemClickListener: ItemClickListener?) {
        this.itemClickListener = itemClickListener
    }

    interface ItemClickListener {
        fun onImageItemClick(position: Int)
    }

    var responseItemList: List<DetailDataResponseItem>? = null

    fun setData(
        imageDataList: List<DetailDataResponseItem>
    ) {
        this.responseItemList = imageDataList
        notifyDataSetChanged()
    }


    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActivityHolder {
        val binding = DataBindingUtil.inflate<ImageListItemBinding>(
            LayoutInflater.from(parent.context),
            R.layout.image_list_item,
            parent,
            false
        )

        return ActivityHolder(binding).listen { position, type ->
            itemClickListener?.onImageItemClick(position)
        }
    }

    override fun getItemCount(): Int {
        if (responseItemList != null) {
            return responseItemList!!.size
        }
        return 0
    }

    override fun onBindViewHolder(holder: ActivityHolder, position: Int) {


        holder.itemView.setOnClickListener(View.OnClickListener {
            if (itemClickListener != null) itemClickListener!!.onImageItemClick(position)
        })

        Glide.with(context)
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
                    holder.binding.nasaImageView.setImageBitmap(resource)
                }

                override fun onLoadCleared(placeholder: Drawable?) {
                }
            })

        holder.binding.titleTxt.text = responseItemList?.get(position)?.title!!


    }

    inner class ActivityHolder(val binding: ImageListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }

    fun <T : RecyclerView.ViewHolder> T.listen(event: (position: Int, type: Int) -> Unit): T {
        itemView.setOnClickListener {
            event.invoke(adapterPosition, itemViewType)
        }
        return this
    }


}