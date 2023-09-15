package taha.younis.basicbankingapp.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize


@Entity(tableName = "customer_table")
@Parcelize
data class Customer(
    @PrimaryKey(autoGenerate = false)
    val phone: String,
    val name: String,
    val email: String,
    val AccountNo: String,
    var balance: Int,
) : Parcelable {
    constructor() : this("", "", "", "", 0)

}