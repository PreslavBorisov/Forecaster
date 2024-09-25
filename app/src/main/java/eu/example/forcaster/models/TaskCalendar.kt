package eu.example.forcaster.models

import android.os.Parcel
import android.os.Parcelable

data class TaskCalendar(
    val name: String = "",
    val description: String =""
):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()?:"",
        parcel.readString()?:""
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) = with(parcel) {
        parcel.writeString(name)
        parcel.writeString(description)

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<TaskCalendar> {
        override fun createFromParcel(parcel: Parcel): TaskCalendar {
            return TaskCalendar(parcel)
        }

        override fun newArray(size: Int): Array<TaskCalendar?> {
            return arrayOfNulls(size)
        }
    }
}
