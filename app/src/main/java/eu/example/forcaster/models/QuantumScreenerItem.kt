package eu.example.forcaster.models

import android.os.Parcel
import android.os.Parcelable
import java.util.Date


data class QuantumScreenerItem(
    val stockSymbol:String,
    val stockIcon: Int,
    val stockId:String,
    val stockCorrelation: Double,
    val stockYearCorrelation: String,
    val stockWinRate: Double,
    val stockDirection: String,
    val stockAvgReturn: Double,
    val stockOpenDate: Date,
    val stockCloseDate: Date
):Parcelable{
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readDouble(),
        parcel.readString()!!,
        parcel.readDouble(),
        parcel.readString()!!,
        parcel.readDouble(),
        Date(parcel.readLong()),
        Date(parcel.readLong())
    ) {
    }

    override fun describeContents(): Int =0

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(stockSymbol)
        dest.writeInt(stockIcon)
        dest.writeString(stockId)
        dest.writeDouble(stockCorrelation)
        dest.writeString(stockYearCorrelation)
        dest.writeDouble(stockWinRate)
        dest.writeString(stockDirection)
        dest.writeDouble(stockAvgReturn)
        dest.writeLong(stockOpenDate.time) // Serialize Date as Long
        dest.writeLong(stockCloseDate.time)
    }

    companion object CREATOR : Parcelable.Creator<QuantumScreenerItem> {
        override fun createFromParcel(parcel: Parcel): QuantumScreenerItem {
            return QuantumScreenerItem(parcel)
        }

        override fun newArray(size: Int): Array<QuantumScreenerItem?> {
            return arrayOfNulls(size)
        }
    }

}