package com.redgunner.ilostthis.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.redgunner.ilostthis.R
import com.redgunner.ilostthis.utils.FoundItem
import kotlinx.android.synthetic.main.i_found_this_layout.view.*

class IFoundThisAdapter(val postClick: () -> Unit):ListAdapter<FoundItem,IFoundThisAdapter.IFoundViewHolder> (IFoundListComparator()){


    inner class IFoundViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val title = itemView.iFoundTitle
        private val place = itemView.iFoundPlace
        private val category = itemView.IFoundcategory
        private val date = itemView.iFoundTime


        init {
            title.setOnClickListener {

                postClick()

            }
        }

        fun bind(found: FoundItem) {

            title.text = found.title
            place.text = found.place
            category.text = found.category
            date.text = found.date

        }


    }


    class IFoundListComparator() : DiffUtil.ItemCallback<FoundItem>() {
        override fun areItemsTheSame(oldItem: FoundItem, newItem: FoundItem): Boolean {
            return true
        }

        override fun areContentsTheSame(oldItem: FoundItem, newItem: FoundItem): Boolean {
            return true
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IFoundViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.i_found_this_layout, parent, false)

        return IFoundViewHolder(view)
    }

    override fun onBindViewHolder(holder: IFoundViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current)
    }
}