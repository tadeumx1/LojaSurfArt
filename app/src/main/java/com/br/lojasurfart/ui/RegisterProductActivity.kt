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
import com.br.lojasurfart.model.Product
import com.br.lojasurfart.model.ProductRegister
import com.br.lojasurfart.service.ProductService
import com.jaredrummler.android.colorpicker.ColorPickerDialogListener
import java.lang.Integer.parseInt


class RegisterProductActivity : DebugActivity(), ColorPickerDialogListener,
    AdapterView.OnItemSelectedListener {

    private lateinit var productResponse: Product

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
        if(edtProductTitle.text.toString() != "" && edtProductCategoryId.text.toString() != "" && edtProductTags.text.toString() != "") {
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

    private fun saveProduct(productRegister: ProductRegister) {
        Thread {
            productResponse = ProductService.createProduct(productRegister)
            runOnUiThread {

                if(productResponse.id != null) {

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
