package com.redgunner.ilostthis.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.redgunner.ilostthis.R
import com.redgunner.ilostthis.utils.LostItem
import kotlinx.android.synthetic.main.i_lost_this_layout.view.*

class ILostThisAdapter(val postClick: () -> Unit):ListAdapter<LostItem,ILostThisAdapter.ILostViewHolder>(ILostListComparator()) {


    inner class ILostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


        private val title = itemView.iLostTitle
        private val place = itemView.iLostPlace
        private val category = itemView.iLostcategory
        private val date = itemView.iLostTime

        init {
            title.setOnClickListener {

                postClick()

            }
        }

        fun bind(lost: LostItem) {

            Log.d("zbi","mlk")

            title.text = lost.title
            place.text = lost.place
            category.text = lost.category
            date.text = lost.date

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

        return ILostViewHolder(view)
    }

    override fun onBindViewHolder(holder: ILostViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current)    }
}