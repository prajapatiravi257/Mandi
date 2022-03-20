package com.planetnoobs.mandi.main.models


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class ApiResponse(
    val active: String, // 1
    @SerializedName("catalog_uuid")
    val catalogUuid: String, // 6141ea17-a69d-4713-b600-0a43c8fd9a6c
    val count: Int, // 10
    val created: Int, // 1627939168
    @SerializedName("created_date")
    val createdDate: String, // 2021-08-02T21:19:28Z
    val desc: String, // Current Daily Price of Various Commodities from Various Markets (Mandi)
    @SerializedName("external_ws")
    val externalWs: Int, // 0
    @SerializedName("external_ws_url")
    val externalWsUrl: String,
    val `field`: List<Field>,
    @SerializedName("index_name")
    val indexName: String, // 9ef84268-d588-465a-a308-a864a43d0070
    val limit: String, // 10
    val message: String, // Resource lists
    val offset: String, // 0
    val org: List<String>,
    @SerializedName("org_type")
    val orgType: String, // Central
    val records: List<Record>,
    val sector: List<String>,
    val source: String, // data.gov.in
    val status: String, // ok
    @SerializedName("target_bucket")
    val targetBucket: TargetBucket,
    val title: String, // Current Daily Price of Various Commodities from Various Markets (Mandi)
    val total: Int, // 2369
    val updated: Int, // 1647502526
    @SerializedName("updated_date")
    val updatedDate: String, // 2022-03-17T13:05:26Z
    val version: String, // 2.2.0
    val visualizable: String // 1
) {
    @Keep
    data class Field(
        val id: String, // state
        val name: String, // State
        val type: String // keyword
    )

    @Keep
    data class Record(
        @SerializedName("arrival_date")
        val arrivalDate: String, // 17/03/2022
        val commodity: String, // Bottle gourd
        val district: String, // South Andaman
        val market: String, // Port Blair
        @SerializedName("max_price")
        val maxPrice: String, // 8000
        @SerializedName("min_price")
        val minPrice: String, // 4000
        @SerializedName("modal_price")
        val modalPrice: String, // 6000
        val state: String, // Andaman and Nicobar
        val variety: String // Other
    )

    @Keep
    data class TargetBucket(
        val `field`: String, // 9ef84268-d588-465a-a308-a864a43d0070
        val index: String, // daily_mandi
        val type: String // 6141ea17-a69d-4713-b600-0a43c8fd9a6c
    )
}