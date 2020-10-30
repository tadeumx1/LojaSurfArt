package com.br.lojasurfart.ui.adapter

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.br.lojasurfart.R
import com.br.lojasurfart.model.Category
import kotlinx.android.synthetic.main.category_layout_list.view.*

class RecyclerAdapterCategory (
    val categories: List<Category>,
    val onClick: (Category) -> Unit): RecyclerView.Adapter<RecyclerAdapterCategory.CategoriesViewHolder>() {

    class CategoriesViewHolder(view: View): RecyclerView.ViewHolder(view) {

        val categoryName: TextView = itemView.txvCategoryTitle
        val categoryDescription: TextView = itemView.txvCategoryDescription

    }

    override fun getItemCount() = this.categories.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriesViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.category_layout_list, parent, false)

        return CategoriesViewHolder(view)
    }

    override fun onBindViewHolder(holder: CategoriesViewHolder, position: Int) {
        val context = holder.itemView.context

        val category = categories[position]

        holder.categoryName.text = category.name
        holder.categoryDescription.text = category.description

        holder.itemView.setOnClickListener { onClick(category) }
    }
}