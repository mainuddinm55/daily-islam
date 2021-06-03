package info.learncoding.dailyislam.ui.hadith

import info.learncoding.dailyislam.R
import info.learncoding.dailyislam.data.model.Hadith
import info.learncoding.dailyislam.databinding.RowItemHadithBinding
import info.learncoding.dailyislam.ui.base.BaseRecyclerAdapter
import java.lang.Exception

class HadithRecyclerAdapter : BaseRecyclerAdapter<Hadith, RowItemHadithBinding>() {
    override fun getLayout(): Int {
        return R.layout.row_item_hadith
    }

    override fun onBindViewHolder(holder: BaseViewHolder<RowItemHadithBinding>, position: Int) {
        try {
            val item = getItem(position)
            val name = StringBuilder(item.hadithNumber).append(".\n")
            for (hadith in item.hadith ?: mutableListOf()) {
                if ("en" == hadith.lang) {
                    if (!hadith.chapterTitle.isNullOrBlank()) {
                        name.append(hadith.chapterTitle)
                        break
                    }
                } else if ("ar" == hadith.lang) {
                    name.append(hadith.chapterTitle)
                }
            }
            holder.binding.hadithTextView.text = name
        } catch (e: Exception) {

        }
        holder.setIsRecyclable(false)
    }
}