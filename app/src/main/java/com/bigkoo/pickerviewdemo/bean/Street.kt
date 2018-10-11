package com.bigkoo.pickerviewdemo.bean


import android.os.Parcel
import android.os.Parcelable
import com.contrarywind.interfaces.IPickerViewData

/**
 * Created by liyuanbiao on 2018/9/19.
 */

data class Street(
    var city: String? = null,
    var district: String? = null,
    var street: String? = null,
    var province: String? = null,
    var sortWeight: Int = 0,
    var id: String? = null
) : Parcelable, IPickerViewData {
    override fun getPickerViewText(): String? {
        return this.street
    }

    constructor(source: Parcel) : this(
        source.readString(),
        source.readString(),
        source.readString(),
        source.readString(),
        source.readInt(),
        source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(city)
        writeString(district)
        writeString(street)
        writeString(province)
        writeInt(sortWeight)
        writeString(id)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<Street> = object : Parcelable.Creator<Street> {
            override fun createFromParcel(source: Parcel): Street = Street(source)
            override fun newArray(size: Int): Array<Street?> = arrayOfNulls(size)
        }
    }
}
