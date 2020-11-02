package com.br.lojasurfart.ui

import android.graphics.Color
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.widget.Toolbar
import com.br.lojasurfart.R
import com.jaredrummler.android.colorpicker.ColorPickerDialog
import kotlinx.android.synthetic.main.activity_product_category.drawer_menu_layout
import kotlinx.android.synthetic.main.activity_product_category.nav_view
import kotlinx.android.synthetic.main.activity_register_product.*
import android.widget.Toast
import com.br.lojasurfart.model.*
import com.br.lojasurfart.service.ProductService
import com.jaredrummler.android.colorpicker.ColorPickerDialogListener
import java.lang.Double.parseDouble
import java.lang.Integer.parseInt


class RegisterProductActivity : DebugActivity(), ColorPickerDialogListener,
        AdapterView.OnItemSelectedListener {

    private lateinit var productResponse: Product
    private lateinit var productVariantResponse: ProductVariant

    companion object {
        const val DIALOG_ID = 0
        const val TAG = "RegisterProductActivity"
    }

    private var productSizeList = arrayOf("PP", "P", "M", "G", "GG")
    private var productSizeListSelected = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_product)

        val toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)

        this.genericLayoutMenu = drawer_menu_layout
        this.genericMenuLateral = nav_view

        spinnerSizeProduct.onItemSelectedListener = this

        val spinnerAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, productSizeList)
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerSizeProduct.adapter = spinnerAdapter

        window.setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
        )

        setupMenuDrawer()

        setupListener()
    }

    private fun setupListener() {

        btnSendProduct.setOnClickListener {
            registerProduct()
        }

        btnSendProductVariant.setOnClickListener {
            registerProductVariant()
        }

        edtProductVariantColor.inputType = InputType.TYPE_NULL
        edtProductVariantColor.setOnFocusChangeListener { _, hasFocus ->
            openColorPicker(hasFocus)
        }

    }

    override fun onNothingSelected(adapterView: AdapterView<*>?) {
        // nothing to do
    }

    override fun onItemSelected(adapterView: AdapterView<*>?, view: View?, position: Int, id: Long) {
        productSizeListSelected = productSizeList[position]
    }

    private fun registerProduct() {
        if (edtProductTitle.text.trim().toString() != "" &&
                edtProductCategoryId.text.trim().toString() != "" &&
                edtProductTags.text.trim().toString() != "") {
            val productTitle = edtProductTitle.text.toString()
            val productCategoryId = parseInt(edtProductCategoryId.text.toString())
            val productTags = edtProductTags.text.toString().split(",").toList()

            val productRegister = ProductRegister(
                    title = productTitle,
                    category = productCategoryId,
                    tags = productTags
            )

            saveProduct(productRegister)

        } else {
            Toast.makeText(this, "Preencha os campos", Toast.LENGTH_LONG).show()
        }

    }

    private fun registerProductVariant() {

        if (edtProductId.text.trim().toString() != "" &&
                edtProductVariantTitle.text.trim().toString() != "" &&
                edtProductVariantOldPrice.text.trim().toString() != "" &&
                edtProductVariantPrice.text.trim().toString() != "" &&
                edtProductVariantQuantity.text.trim().toString() != "" &&
                edtProductVariantColor.text.trim().toString() != "" &&
                edtProductVariantLength.text.trim().toString() != "" &&
                edtProductVariantWidth.text.trim().toString() != "" &&
                edtProductVariantImage.text.trim().toString() != "" &&
                edtProductVariantHeight.text.trim().toString() != "" &&
                edtProductVariantWeight.text.trim().toString() != "") {

            val productVariantTitle = edtProductVariantTitle.text.toString()
            val productId = parseInt(edtProductId.text.toString())
            val productVariantHeight = parseDouble(edtProductVariantHeight.text.toString())
            val productVariantWeight = parseDouble(edtProductVariantWeight.text.toString())
            val productVariantPrice = parseDouble(edtProductVariantPrice.text.toString())
            val productVariantOldPrice = parseDouble(edtProductVariantOldPrice.text.toString())
            val productVariantQuantity = parseInt(edtProductVariantQuantity.text.toString())
            val productVariantAvailableStock = parseInt(edtProductVariantQuantity.text.toString())
            val productVariantImages = edtProductVariantImage.text.toString().split(",").toList()
            val productVariantColor = edtProductVariantColor.text.toString()
            val productVariantLength = parseDouble(edtProductVariantLength.text.toString())
            val productVariantWidth = parseDouble(edtProductVariantWidth.text.toString())

            val colorInformation = ColorInformation(
                    title = "Cor Teste",
                    code = productVariantColor
            )
            2
            val productVariantRegister = ProductVariantRegister(
                    title = productVariantTitle,
                    productId = productId,
                    size = productSizeListSelected,
                    promotion = checkBoxProductVariant.isChecked,
                    height = productVariantHeight,
                    weight = productVariantWeight,
                    price = productVariantPrice,
                    oldPrice = productVariantOldPrice,
                    quantity = productVariantQuantity,
                    availableStock = productVariantAvailableStock,
                    images = productVariantImages,
                    color = colorInformation,
                    length = productVariantLength,
                    width = productVariantWidth
            )

            saveProductVariant(productVariantRegister)

        } else {
            Toast.makeText(this, "Preencha os campos", Toast.LENGTH_LONG).show()
        }
    }

    private fun saveProduct(productRegister: ProductRegister) {
        Thread {
            productResponse = ProductService.createProduct(productRegister)
            runOnUiThread {

                if (productResponse.id != null) {

                    edtProductTitle.setText("")
                    edtProductCategoryId.setText("")
                    edtProductTags.setText("")
                    Toast.makeText(this, "Produto salvo com sucesso", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(this, "Erro ao registrar produto", Toast.LENGTH_LONG).show()
                }

            }
        }.start()
    }

    private fun saveProductVariant(productVariantRegister: ProductVariantRegister) {
        Thread {
            productVariantResponse = ProductService.createProductVariant(productVariantRegister)
            runOnUiThread {

                if (productVariantResponse.id != null) {

                    edtProductVariantTitle.setText("")
                    edtProductVariantOldPrice.setText("")
                    edtProductVariantPrice.setText("")
                    edtProductVariantQuantity.setText("")
                    edtProductVariantColor.setText("")
                    edtProductVariantLength.setText("")
                    edtProductVariantWidth.setText("")
                    edtProductVariantImage.setText("")
                    edtProductVariantHeight.setText("")
                    edtProductVariantWeight.setText("")
                    Toast.makeText(this, "A variante do produto foi cadastrada com sucesso", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(this, "Erro ao registrar a variante do produto", Toast.LENGTH_LONG).show()
                }

            }
        }.start()
    }

    private fun openColorPicker(hasFocus: Boolean) {
        if (hasFocus) {
            ColorPickerDialog.newBuilder()
                .setDialogType(ColorPickerDialog.TYPE_CUSTOM)
                .setAllowPresets(false)
                .setDialogId(DIALOG_ID)
                .setColor(Color.BLACK)
                .setShowAlphaSlider(true)
                .show(this)
        }
    }

    override fun onColorSelected(dialogId: Int, color: Int) {
        when (dialogId) {
            DIALOG_ID -> {
                // We got result from the dialog that is shown when clicking on the icon in the action bar.
                /* Toast.makeText(
                    this@RegisterProductActivity,
                    "Selected Color: #" + Integer.toHexString(color),
                    Toast.LENGTH_SHORT
                ).show() */

                edtProductVariantColor.setText("#" + Integer.toHexString(color))
            }
        }

    }

    override fun onDialogDismissed(dialogId: Int) {
        Log.d(TAG, "onDialogDismissed() called with: dialogId = [$dialogId]")
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
