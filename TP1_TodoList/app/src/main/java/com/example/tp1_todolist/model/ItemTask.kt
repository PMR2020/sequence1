package com.example.tp1_todolist.model

import android.os.Parcel
import android.os.Parcelable

data class ItemTask(val description:String?) : Parcelable {
    var fait =false  //private属性没有getter、setter

    constructor(parcel: Parcel) : this(parcel.readString()) {

    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(description)
        parcel.writeByte(if (fait) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ItemTask> {
        override fun createFromParcel(parcel: Parcel): ItemTask {
            return ItemTask(parcel)
        }

        override fun newArray(size: Int): Array<ItemTask?> {
            return arrayOfNulls(size)
        }
    }


}