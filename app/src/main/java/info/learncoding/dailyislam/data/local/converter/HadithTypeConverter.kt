package info.learncoding.dailyislam.data.local.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import info.learncoding.dailyislam.data.model.HadithInfo

class HadithTypeConverter {
    @TypeConverter
    fun hadithInfosToJson(hadithInfos: List<HadithInfo>): String? {
        return Gson().toJson(hadithInfos)
    }

    @TypeConverter
    fun hadithInfosFromJson(json: String?): List<HadithInfo>? {
        return Gson().fromJson(json, object : TypeToken<List<HadithInfo>>() {}.type)
    }
}