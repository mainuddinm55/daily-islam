package info.learncoding.dailyislam.data.local.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import info.learncoding.dailyislam.data.model.HadithBookInfo

class HadithBookTypeConverter {
    @TypeConverter
    fun hadithBookInfosToJson(hadithBookInfos: List<HadithBookInfo>): String? {
        return Gson().toJson(hadithBookInfos)
    }

    @TypeConverter
    fun hadithBookInfosFromJson(json: String?): List<HadithBookInfo>? {
        return Gson().fromJson(json, object : TypeToken<List<HadithBookInfo>>() {}.type)
    }
}