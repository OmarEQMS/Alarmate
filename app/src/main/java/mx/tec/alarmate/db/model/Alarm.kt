package mx.tec.alarmate.db.model

import android.annotation.SuppressLint
import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Time

@Entity
data class Alarm(
    @PrimaryKey(autoGenerate = true)
    @NonNull
    var idAlarm: Long,
    var name: String,
    var active : Boolean,
    var hour: String,
    var monday: Boolean,
    var tuesday: Boolean,
    var wednesday: Boolean,
    var thursday: Boolean,
    var friday: Boolean,
    var saturday: Boolean,
    var sunday: Boolean,
    var vibration: Boolean,
    var flash: Boolean
): Parcelable{
    constructor(source: Parcel):this(
        source.readLong(),
        source.readString()!!,
        source.readInt()>0,
        source.readString()!!,
        source.readInt()>0,
        source.readInt()>0,
        source.readInt()>0,
        source.readInt()>0,
        source.readInt()>0,
        source.readInt()>0,
        source.readInt()>0,
        source.readInt()>0,
        source.readInt()>0

    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(idAlarm)
        parcel.writeString(name)
        parcel.writeInt(if (active) 1 else 0)
        parcel.writeString(hour)
        parcel.writeInt(if (monday) 1 else 0)
        parcel.writeInt(if (tuesday) 1 else 0)
        parcel.writeInt(if (wednesday) 1 else 0)
        parcel.writeInt(if (thursday) 1 else 0)
        parcel.writeInt(if (friday) 1 else 0)
        parcel.writeInt(if (saturday) 1 else 0)
        parcel.writeInt(if (sunday) 1 else 0)
        parcel.writeInt(if (vibration) 1 else 0)
        parcel.writeInt(if (flash) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Alarm> {
        override fun createFromParcel(parcel: Parcel): Alarm {
            return Alarm(parcel)
        }

        override fun newArray(size: Int): Array<Alarm?> {
            return arrayOfNulls(size)
        }
    }
}
