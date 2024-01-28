package co.ehsansetayesh.data_remote.networking.product

import co.ehsansetayesh.domain.entity.Rating
import com.squareup.moshi.Json

data class ProductApiModel(
    @Json(name = "id") val id: Long,
    @Json(name = "title") val title: String,
    @Json(name = "description") val description: String,
    @Json(name = "category") val category: String,
    @Json(name = "image") val image: String,
    @Json(name = "price") val price: Double,
    @Json(name = "rating") val rating: Rating,
)