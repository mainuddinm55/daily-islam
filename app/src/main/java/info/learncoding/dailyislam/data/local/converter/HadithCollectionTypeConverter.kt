package info.learncoding.dailyislam.data.local.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import info.learncoding.dailyislam.data.model.HadithCollectionInfo

class HadithCollectionTypeConverter {
    @TypeConverter
    fun hadithCollectionInfosToJson(hadithCollectionInfos: List<HadithCollectionInfo>): String? {
        return Gson().toJson(hadithCollectionInfos)
    }

    @TypeConverter
    fun hadithCollectionInfosFromJson(json: String?):List<HadithCollectionInfo>? {
        return Gson().fromJson(json, object : TypeToken<List<HadithCollectionInfo>>() {}.type)
    }
}