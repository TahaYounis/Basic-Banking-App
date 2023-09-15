package taha.younis.basicbankingapp.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "history_table")
@Parcelize
data class History(
    @PrimaryKey(autoGenerate = true)
    val transfer_id: Int = 0,
    val sender: String,
    val receiver: String,
    val amount: String,
) : Parcelable {
}