package dev.aaa1115910.biliapi.repositories

import dev.aaa1115910.biliapi.entity.CarouselData
import dev.aaa1115910.biliapi.entity.ugc.UgcType
import dev.aaa1115910.biliapi.entity.ugc.UgcTypeV2
import dev.aaa1115910.biliapi.entity.ugc.region.UgcFeedData
import dev.aaa1115910.biliapi.entity.ugc.region.UgcFeedPage
import dev.aaa1115910.biliapi.entity.ugc.region.UgcRegionData
import dev.aaa1115910.biliapi.entity.ugc.region.UgcRegionListData
import dev.aaa1115910.biliapi.http.BiliHttpApi
import org.koin.core.annotation.Single

@Single
class UgcRepository(
    private val authRepository: AuthRepository
) {
    suspend fun getCarousel(ugcType: UgcTypeV2): CarouselData {
        val regionBanner = BiliHttpApi.getRegionBanner(ugcType.tid).getResponseData()
        val carouselData = CarouselData.fromRegionBanner(regionBanner)
        return carouselData
    }

    @Deprecated("User getRegionFeedRcmd instead")
    suspend fun getRegionData(ugcType: UgcType): UgcRegionData {
        val responseData = BiliHttpApi.getRegionDynamic(
            rid = ugcType.rid,
            accessKey = authRepository.accessToken ?: "",
        ).getResponseData()
        val data = UgcRegionData.fromRegionDynamic(responseData)
        return data
    }

    @Deprecated("User getRegionFeedRcmd instead")
    suspend fun getRegionMoreData(ugcType: UgcType): UgcRegionListData {
        val responseData = BiliHttpApi.getRegionDynamicList(
            rid = ugcType.rid,
            accessKey = authRepository.accessToken ?: "",
        ).getResponseData()
        val data = UgcRegionListData.fromRegionDynamicList(responseData)
        return data
    }

    suspend fun getRegionFeedRcmd(ugcType: UgcTypeV2, page: UgcFeedPage): UgcFeedData {
        val responseData = BiliHttpApi.getRegionFeedRcmd(
            displayId = page.nextPage,
            fromRegion = ugcType.tid,
            sessData = authRepository.sessionData
        ).getResponseData()
        val ugcFeedData = UgcFeedData.fromRegionFeedRcmd(responseData)
        ugcFeedData.nextPage = UgcFeedPage(page.nextPage + 1)
        return ugcFeedData
    }
}
