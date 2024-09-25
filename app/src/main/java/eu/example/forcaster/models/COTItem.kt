package eu.example.forcaster.models

import android.os.Parcel
import android.os.Parcelable

data class COTItem(
    val title: String
):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) = with(parcel) {
        parcel.writeString(title)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<COTItem> {
        override fun createFromParcel(parcel: Parcel): COTItem {
            return COTItem(parcel)
        }

        override fun newArray(size: Int): Array<COTItem?> {
            return arrayOfNulls(size)
        }
    }
}
