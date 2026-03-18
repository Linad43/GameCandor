package com.example.gamecandor.model

import android.os.Parcel
import android.os.Parcelable

data class Card(
    val id: Int,
    val category: Category,
    val questionIds: List<Int>
) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        Category.valueOf(parcel.readString()!!),
        mutableListOf<Int>().apply {
//            parcel.readList(this as List<*>, Int::class.java.classLoader)
            parcel.createStringArrayList()
        }
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(category.name)
        parcel.writeList(questionIds)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Card> {

        override fun createFromParcel(parcel: Parcel): Card {
            return Card(parcel)
        }

        override fun newArray(size: Int): Array<Card?> {
            return arrayOfNulls(size)
        }
    }
}