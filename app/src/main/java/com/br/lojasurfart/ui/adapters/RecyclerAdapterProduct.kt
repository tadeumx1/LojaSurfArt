package com.br.lojasurfart.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.br.lojasurfart.R
import com.br.lojasurfart.model.Product
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.product_layout_list.view.*
import android.graphics.Paint.STRIKE_THRU_TEXT_FLAG

// Product

class RecyclerAdapterProduct(
    private val mContext: Context,
    private val mProductList: List<Product> = listOf(),
    private val onProductListener: OnProductListener
) : RecyclerView.Adapter<RecyclerAdapterProduct.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(com.br.lojasurfart.R.layout.product_layout_list, parent, false)
        return ViewHolder(view, onProductListener)

    }

    override fun getItemCount(): Int = mProductList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val product = mProductList[position]

        holder.mProductName.text = product.title
        holder.mProductDescription.text = product.tags.toString()
            .replace("[", "")
            .replace("]", "")
        holder.mProductOldPrice.text = "R$" + product.price.toString()
        holder.mProductOldPrice.paintFlags = holder.mProductOldPrice.paintFlags or STRIKE_THRU_TEXT_FLAG
        holder.mProductPrice.text = "R$" + product.price.toString()

        val defaultOptions = RequestOptions()
            .error(R.drawable.ic_launcher_background)
        Glide.with(mContext)
            .setDefaultRequestOptions(defaultOptions)
            .load(mProductList[position].image)
            .into(holder.mProductImage)

    }

    class ViewHolder(itemView: View, productListener: OnProductListener) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        val mProductName: TextView = itemView.txvNameProduct
        val mProductDescription: TextView = itemView.txvDescription
        val mProductOldPrice: TextView = itemView.txvOldPrice
        val mProductPrice: TextView = itemView.txvPrice
        val mProductImage: ImageView = itemView.imvProduct
        private val mProductButton: Button = itemView.btnBuyProduct
        private var productListenerClick: OnProductListener

        init {
            mProductButton.setOnClickListener(this)
            productListenerClick = productListener
        }

        override fun onClick(view: View?) {
            productListenerClick.onProductClick(adapterPosition)
        }
    }

    interface OnProductListener {
        fun onProductClick(position: Int)
    }

}