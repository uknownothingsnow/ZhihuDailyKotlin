package com.github.lzyzsd.zhihudailykotlin

import android.os.Parcel
import android.os.Parcelable
import java.util.*

/**
 * Created by bruce on 7/17/15.
 */
public data class Story : Parcelable {

    var images: List<String> = ArrayList<String>()
    var type: Int = -1
    var id: Int = -1
    var title: String = ""

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeStringList(images)
        dest.writeInt(type)
        dest.writeInt(id)
        dest.writeString(title)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object {
        public val CREATOR: Parcelable.Creator<Story> = object : Parcelable.Creator<Story> {
            override fun createFromParcel(parcelIn: Parcel): Story {
                val story = Story()
                parcelIn.readStringList(story.images)
                story.type = parcelIn.readInt()
                story.id = parcelIn.readInt()
                story.title = parcelIn.readString()

                return story
            }

            override fun newArray(size: Int): Array<Story> {
                return Array(size, { i -> Story() })
            }
        }
    }
}