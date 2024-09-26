package eu.example.forcaster.models

import android.os.Parcel
import android.os.Parcelable

data class RankingItem(
    val image: String = "",
    val tag: String = "",
    val title: String = ""
):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) = with(parcel) {
        parcel.writeString(image)
        parcel.writeString(tag)
        parcel.writeString(title)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<RankingItem> {
        override fun createFromParcel(parcel: Parcel): RankingItem {
            return RankingItem(parcel)
        }

        override fun newArray(size: Int): Array<RankingItem?> {
            return arrayOfNulls(size)
        }
    }
}
