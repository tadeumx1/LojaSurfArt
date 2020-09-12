package com.br.lojasurfart.ui

import android.graphics.Paint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import com.br.lojasurfart.R
import com.br.lojasurfart.model.Product
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.activity_product_detail.*

class ProductDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_detail)

        if (supportActionBar != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }

        val product = intent.getParcelableExtra<Product>("product")

        if (product != null) {
            initView(product)
        } else {
            // Mostrar que houve um erro ao carregar o produto
        }
    }

    private fun initView(productSelected: Product) {

        carouselView.pageCount = 1
        carouselView.setImageListener { _, imageView ->

            imageView.adjustViewBounds = true
            imageView.scaleType = ImageView.ScaleType.FIT_CENTER

            val defaultOptions = RequestOptions()
                .error(R.drawable.ic_launcher_background)
            Glide.with(this)
                .setDefaultRequestOptions(defaultOptions)
                .load(productSelected.image)
                .into(imageView)
        }

        txvNameProduct.text = productSelected.title
        txvOldPrice.text = "R$ " + productSelected.price.toString()
        txvOldPrice.paintFlags = txvOldPrice.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
        txvPrice.text = "R$ " + productSelected.price.toString()

        txvDetail.setOnClickListener {
            if(layout_detail.visibility == View.GONE) {
                layout_detail.visibility = View.VISIBLE
            } else {
                layout_detail.visibility = View.GONE
            }
        }

        txvInformation.setOnClickListener {
            if(layout_information.visibility == View.GONE) {
                layout_information.visibility = View.VISIBLE
            } else {
                layout_information.visibility = View.GONE
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
