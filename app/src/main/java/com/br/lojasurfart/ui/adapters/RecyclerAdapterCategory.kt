package com.br.lojasurfart.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.br.lojasurfart.R
import kotlinx.android.synthetic.main.category_layout_list.view.*

// Category

class RecyclerAdapterCategory(
    private val mContext: Context,
    private val mCategoryList: List<String> = listOf(),
    private val onCategoryListener: OnCategoryListener
) : RecyclerView.Adapter<RecyclerAdapterCategory.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.category_layout_list, parent, false)
        return ViewHolder(view, onCategoryListener)

    }

    override fun getItemCount(): Int = mCategoryList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val category = mCategoryList[position]
        holder.mCategoryName.text = category
    }

    class ViewHolder(itemView: View, categoryListener: OnCategoryListener) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        val mCategoryName: TextView = itemView.txvNameCategory
        private val mLayoutCategory: ConstraintLayout = itemView.layout_category
        private var categoryListenerClick: OnCategoryListener

        init {
            mLayoutCategory.setOnClickListener(this)
            categoryListenerClick = categoryListener
        }

        override fun onClick(view: View?) {
            categoryListenerClick.onCategoryClick(adapterPosition)
        }
    }

    interface OnCategoryListener {
        fun onCategoryClick(position: Int)
    }

}