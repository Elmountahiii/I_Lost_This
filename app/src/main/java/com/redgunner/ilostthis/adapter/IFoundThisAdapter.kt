package com.redgunner.ilostthis.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.redgunner.ilostthis.R
import com.redgunner.ilostthis.state.PostClick
import com.redgunner.ilostthis.utils.FoundItem
import kotlinx.android.synthetic.main.i_found_this_layout.view.*

class IFoundThisAdapter(val postClick: (PostClick) -> Unit):ListAdapter<FoundItem,IFoundThisAdapter.IFoundViewHolder> (IFoundListComparator()){


    inner class IFoundViewHolder(itemView: View, private val context: Context) : RecyclerView.ViewHolder(itemView) {

        private val title = itemView.iFoundTitle
        private val place = itemView.iFoundPlace
        private val category = itemView.IFoundcategory
        private val date = itemView.iFoundTime
        private val image=itemView.iFoundItemImage


        init {
            title.setOnClickListener {

                postClick(PostClick.ItemFound(getItem(adapterPosition).postId,false))

            }

            image.setOnClickListener {
                postClick(PostClick.ItemFound(getItem(adapterPosition).postId,false))

            }
        }

        fun bind(found: FoundItem) {

            title.text = found.title
            place.text = found.place
            category.text = found.category
            date.text = found.date
            Glide.with(context)
                    .load(found.imageUrl).into(image)

        }


    }


    class IFoundListComparator() : DiffUtil.ItemCallback<FoundItem>() {
        override fun areItemsTheSame(oldItem: FoundItem, newItem: FoundItem): Boolean {
            return false
        }

        override fun areContentsTheSame(oldItem: FoundItem, newItem: FoundItem): Boolean {
            return false
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IFoundViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.i_found_this_layout, parent, false)

        return IFoundViewHolder(view,parent.context)
    }

    override fun onBindViewHolder(holder: IFoundViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current)
    }
}