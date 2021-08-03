package com.redgunner.ilostthis.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.redgunner.ilostthis.R
import com.redgunner.ilostthis.state.PostClick
import com.redgunner.ilostthis.utils.LostItem
import kotlinx.android.synthetic.main.i_lost_this_layout.view.*

class ILostThisAdapter(val postClick: (PostClick) -> Unit):ListAdapter<LostItem,ILostThisAdapter.ILostViewHolder>(ILostListComparator()) {


    inner class ILostViewHolder(itemView: View, private val context: Context) : RecyclerView.ViewHolder(itemView) {


        private val title = itemView.iLostTitle
        private val place = itemView.iLostPlace
        private val category = itemView.iLostcategory
        private val date = itemView.iLostTime
        private val image=itemView.iLostItemImage

        init {
            title.setOnClickListener {

                postClick(PostClick.ItemLost(getItem(adapterPosition).postId,true))

            }

            image.setOnClickListener {
                postClick(PostClick.ItemLost(getItem(adapterPosition).postId,true))


            }
        }

        fun bind(lost: LostItem) {


            title.text = lost.title
            place.text = lost.place
            category.text = lost.category
            date.text = lost.date
            Glide.with(context)
                    .load(lost.imageUrl).into(image)

        }


    }


    class ILostListComparator() : DiffUtil.ItemCallback<LostItem>() {
        override fun areItemsTheSame(oldItem: LostItem, newItem: LostItem): Boolean {
            return false
        }

        override fun areContentsTheSame(oldItem: LostItem, newItem: LostItem): Boolean {
            return false
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ILostViewHolder {

        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.i_lost_this_layout, parent, false)

        return ILostViewHolder(view,parent.context)
    }

    override fun onBindViewHolder(holder: ILostViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current)    }
}