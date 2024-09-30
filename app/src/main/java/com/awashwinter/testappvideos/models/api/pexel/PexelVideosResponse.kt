package com.awashwinter.testappvideos.models.api.pexel

import com.google.gson.annotations.SerializedName


data class PexelVideosResponse (

    @SerializedName("page"          ) var page         : Int?              = null,
    @SerializedName("per_page"      ) var perPage      : Int?              = null,
    @SerializedName("total_results" ) var totalResults : Int?              = null,
    @SerializedName("url"           ) var url          : String?           = null,
    @SerializedName("videos"        ) var videos       : ArrayList<Videos> = arrayListOf()

)

data class VideoFiles (

    @SerializedName("id"        ) var id       : Int?    = null,
    @SerializedName("quality"   ) var quality  : String? = null,
    @SerializedName("file_type" ) var fileType : String? = null,
    @SerializedName("width"     ) var width    : Int?    = null,
    @SerializedName("height"    ) var height   : Int?    = null,
    @SerializedName("link"      ) var link     : String? = null

)

data class VideoPictures (

    @SerializedName("id"      ) var id      : Int?    = null,
    @SerializedName("picture" ) var picture : String? = null,
    @SerializedName("nr"      ) var nr      : Int?    = null

)

data class Videos (

    @SerializedName("id"             ) var id            : Int?                     = null,
    @SerializedName("width"          ) var width         : Int?                     = null,
    @SerializedName("height"         ) var height        : Int?                     = null,
    @SerializedName("url"            ) var url           : String?                  = null,
    @SerializedName("image"          ) var image         : String?                  = null,
    @SerializedName("duration"       ) var duration      : Int?                     = null,
    @SerializedName("video_files"    ) var videoFiles    : ArrayList<VideoFiles>    = arrayListOf(),
    @SerializedName("video_pictures" ) var videoPictures : ArrayList<VideoPictures> = arrayListOf()

)