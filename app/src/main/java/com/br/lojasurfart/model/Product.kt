package com.br.lojasurfart.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Product(
    @SerializedName("id") val id: Int?,
    // @SerializedName("category") val category: List<Category>?,
    @SerializedName("created_at") val createdAt: String?,
    @SerializedName("updated_at") val updatedAt: String?,
    @SerializedName("tags") val tags: List<String>?,
    @SerializedName("price") val price: Double?,
    @SerializedName("rate_stars") val rateStars: Int?,
    // @SerializedName("variants") val variants: List<ProductVariant>?,
    @SerializedName("title") val title: String?,
    @SerializedName("image") val image: String?
)  : Parcelable

/* data class Product(
    @SerializedName("id") val id: Int?,
    @SerializedName("title") val name: String?,
    @SerializedName("price") val price: Double?,
    @SerializedName("category") val category: String?,
    @SerializedName("description") val description: String?,
    @SerializedName("image") val image_url: String?
) */

/* data class Product(
    @SerializedName("id") val id : Int?,
    @SerializedName("name") val name : String?,
    @SerializedName("description") val description: String?,
    @SerializedName("price") val price : Double?,
    @SerializedName("old_price") val old_price : Double?,
    @SerializedName("image_url") val image_url : Double?,
    @SerializedName("tags") val tags : Array<String>?,
    @SerializedName("rate_stars") val rate_stars : Int?,
    @SerializedName("category") val category : String?
) */

fun Product.toModel() = Product(
    this.id,
    this.createdAt,
    this.updatedAt,
    this.tags,
    this.price,
    this.rateStars,
    this.title,
    this.image
)