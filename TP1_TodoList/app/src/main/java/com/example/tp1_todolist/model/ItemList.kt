package com.example.tp1_todolist.model

import android.os.Parcel
import android.os.Parcelable

data class ItemList(val title: String?) : Parcelable {
    var myTask= mutableListOf<ItemTask>()
    constructor(parcel: Parcel) : this (parcel.readString()) {

    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(title)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ItemList> {
        override fun createFromParcel(parcel: Parcel): ItemList {
            return ItemList(parcel)
        }

        override fun newArray(size: Int): Array<ItemList?> {
            return arrayOfNulls(size)
        }
    }
}

