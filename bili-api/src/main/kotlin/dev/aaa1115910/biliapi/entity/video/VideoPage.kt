package dev.aaa1115910.biliapi.entity.video

data class VideoPage(
    var cid: Long,
    val index: Int,
    val title: String,
    val duration: Int,
    val dimension: Dimension
) {
    companion object {
        fun fromViewPage(viewPage: bilibili.app.view.v1.ViewPage) = VideoPage(
            cid = viewPage.page.cid,
            index = viewPage.page.page,
            title = viewPage.page.part,
            duration = viewPage.page.duration.toInt(),
            dimension = Dimension.fromDimension(viewPage.page.dimension)
        )

        fun fromVideoPage(videoPage: dev.aaa1115910.biliapi.http.entity.video.VideoPage) =
            VideoPage(
                cid = videoPage.cid,
                index = videoPage.page,
                title = videoPage.part,
                duration = videoPage.duration,
                dimension = Dimension.fromDimension(videoPage.dimension)
            )

        fun fromPage(page: bilibili.app.archive.v1.Page) = VideoPage(
            cid = page.cid,
            index = page.page,
            title = page.part,
            duration = page.duration.toInt(),
            dimension = Dimension.fromDimension(page.dimension)
        )
    }
}
