package info.learncoding.dailyislam.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize


@Entity(tableName = "hadith_book")
@Parcelize
data class HadithBook(
    @PrimaryKey
    var bookNumber: String,
    var collection: String?,
    var book: List<HadithBookInfo>? = mutableListOf(),
    var hadithStartNumber: String?,
    var hadithEndNumber: String?,
    var numberOfHadith: String?
):Parcelable
