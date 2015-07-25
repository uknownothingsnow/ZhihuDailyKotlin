package com.github.lzyzsd.zhihudailykotlin

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import java.util.*

/**
 * Created by bruce on 7/25/15.
 */
public data class StoryDetail {
    public var body: String? = null
    @SerializedName("image_source")
    public var imageSource: String? = null
    public var title: String? = null
    public var image: String? = null
    @SerializedName("share_url")
    public var shareUrl: String? = null
    public var js: List<String> = ArrayList<String>()
    @SerializedName("ga_prefix")
    public var gaPrefix: String? = null
    public var type: Int? = null
    public var id: Long? = null
    public var css: List<String> = ArrayList<String>()
}