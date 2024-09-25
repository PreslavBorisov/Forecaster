package eu.example.forcaster.models

import android.os.Parcel
import android.os.Parcelable

data class Favorite(
    val name: String,
    val tag: String,
    val image: String,
    val assignedTo: ArrayList<String> = ArrayList(),
    var documentId:String =""
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.createStringArrayList()!!,
        parcel.readString()!!
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) = with(parcel) {
        parcel.writeString(name)
        parcel.writeString(tag)
        parcel.writeString(image)
        parcel.writeStringList(assignedTo)
        parcel.writeString(documentId)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Favorite> {
        override fun createFromParcel(parcel: Parcel): Favorite {
            return Favorite(parcel)
        }

        override fun newArray(size: Int): Array<Favorite?> {
            return arrayOfNulls(size)
        }
    }
}
