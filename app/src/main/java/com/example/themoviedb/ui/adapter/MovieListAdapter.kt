package com.example.themoviedb.ui.adapter


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.themoviedb.R
import com.example.themoviedb.data.model.Results
import com.example.themoviedb.databinding.ItemBinding

internal class MovieListAdapter internal constructor(
    context: Context,
    private val resource: Int,
    private val itemList: ArrayList<Results>
) : ArrayAdapter<MovieListAdapter.ItemViewHolder>(context, resource) {

    private val inflater: LayoutInflater = LayoutInflater.from(context)

    override fun getCount(): Int {
        return if (this.itemList != null) this.itemList.size else 0
    }

    override fun getView(position: Int, view: View?, parent: ViewGroup): View {
        var convertView = view
        val holder: ItemViewHolder

        if (convertView == null) {
          var  itemBinding = ItemBinding.inflate(inflater)
            convertView = itemBinding.root
            holder = ItemViewHolder()
            holder.name = itemBinding.title
            holder.icon = itemBinding.imageViewAvatar
            convertView.tag = holder
        } else {
            holder = convertView.tag as ItemViewHolder
        }
        holder.name!!.text = this.itemList!![position].title
        if (this.itemList[position].posterPath?.isEmpty() != true) {
            Glide.with(holder.icon!!.context)
                .load(this.itemList[position].posterPath)
                .into(holder.icon!!)
        }

        return convertView
    }

    internal class ItemViewHolder {
        var name: TextView? = null
        var icon: ImageView? = null
    }
}
