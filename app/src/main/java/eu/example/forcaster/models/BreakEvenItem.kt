package eu.example.forcaster.models

import android.os.Parcel
import android.os.Parcelable

data class BreakEvenItem(
    val stockIcon: Int,
    val stockID: String,
    val stockName: String,
    val countryFlag: Int,
    val countryName: String,
    val sector: String,
    val marketCap: String,
    val breakEvenQuarter: String
):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!
    ) {
    }

    override fun describeContents() = 0


    override fun writeToParcel(parcel: Parcel, flags: Int) = with(parcel) {
        parcel.writeInt(stockIcon)
        parcel.writeString(stockID)
        parcel.writeString(stockName)
        parcel.writeInt(countryFlag)
        parcel.writeString(countryName)
        parcel.writeString(sector)
        parcel.writeString(marketCap)
        parcel.writeString(breakEvenQuarter)
    }

    companion object CREATOR : Parcelable.Creator<BreakEvenItem> {
        override fun createFromParcel(parcel: Parcel): BreakEvenItem {
            return BreakEvenItem(parcel)
        }

        override fun newArray(size: Int): Array<BreakEvenItem?> {
            return arrayOfNulls(size)
        }
    }
}
