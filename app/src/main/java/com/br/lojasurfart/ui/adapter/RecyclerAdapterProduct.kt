package com.br.lojasurfart.ui.adapter

import android.graphics.Paint.STRIKE_THRU_TEXT_FLAG
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.br.lojasurfart.R
import com.br.lojasurfart.model.ProductVariant
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.product_layout_list.view.*
import java.text.DecimalFormat

class RecyclerAdapterProduct (
    val products: List<ProductVariant>,
    val onClick: (ProductVariant) -> Unit): RecyclerView.Adapter<RecyclerAdapterProduct.ProductsViewHolder>() {

    class ProductsViewHolder(view: View): RecyclerView.ViewHolder(view) {

        val productName: TextView = itemView.txvNameProduct
        val productDescription: TextView = itemView.txvDescription
        val productOldPrice: TextView = itemView.txvOldPrice
        val productPrice: TextView = itemView.txvPrice
        val productImage: ImageView = itemView.imvProduct
    }

    override fun getItemCount() = this.products.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.product_layout_list, parent, false)

        return ProductsViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductsViewHolder, position: Int) {
        val context = holder.itemView.context

        val product = products[position]

        val decimalFormat = DecimalFormat("0.00")

        holder.productName.text = product.title
        holder.productDescription.text = "Quantidade ${product.quantity}"

        holder.productOldPrice.text = "R$" + decimalFormat.format(product.price)
        holder.productOldPrice.paintFlags = holder.productOldPrice.paintFlags or STRIKE_THRU_TEXT_FLAG
        holder.productPrice.text = "R$" + decimalFormat.format(product.price)

        val defaultOptions = RequestOptions()
            .error(R.drawable.ic_launcher_background)
        Glide.with(context)
            .setDefaultRequestOptions(defaultOptions)
            .load(products[position].images?.get(0))
            .into(holder.productImage)

        holder.itemView.setOnClickListener { onClick(product) }
    }
}