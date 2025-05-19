package dev.aaa1115910.biliapi.http.entity.region

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RegionBanner(
    @SerialName("region_banner_list")
    val regionBannerList: List<UgcRegionBannerItem>
)

@Serializable
data class UgcRegionBannerItem(
    val image: String,
    val title: String,
    @SerialName("sub_title")
    val subTitle: String,
    val url: String,
    val rid: Int
)