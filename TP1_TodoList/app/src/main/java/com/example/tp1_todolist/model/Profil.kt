package com.example.tp1_todolist.model

import android.os.Parcel
import android.os.Parcelable

data class Profil(private val pseudoName:String?, private val password :String?): Parcelable {
    var myList= mutableListOf<ItemList>()

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString()
    ) {

    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(pseudoName)
        parcel.writeString(password)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Profil> {
        override fun createFromParcel(parcel: Parcel): Profil {
            return Profil(parcel)
        }

        override fun newArray(size: Int): Array<Profil?> {
            return arrayOfNulls(size)
        }
    }


}