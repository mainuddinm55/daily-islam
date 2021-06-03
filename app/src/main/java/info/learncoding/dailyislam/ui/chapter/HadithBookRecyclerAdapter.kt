package info.learncoding.dailyislam.ui.chapter

import info.learncoding.dailyislam.R
import info.learncoding.dailyislam.data.model.HadithBook
import info.learncoding.dailyislam.databinding.RowItemHadithChapterBinding
import info.learncoding.dailyislam.ui.base.BaseRecyclerAdapter

class HadithBookRecyclerAdapter : BaseRecyclerAdapter<HadithBook, RowItemHadithChapterBinding>() {
    override fun getLayout(): Int {
        return R.layout.row_item_hadith_chapter
    }

    override fun onBindViewHolder(
        holder: BaseViewHolder<RowItemHadithChapterBinding>,
        position: Int
    ) {
        try {
            val item = getItem(position)
            val name: StringBuilder = StringBuilder()
            item.book?.forEach {
                if ("en" == it.lang) {
                    name.append(it.name)
                } else if ("ar" == it.lang) {
                    if (name.isNotEmpty()) {
                        name.append(" - ")
                    }
                    name.append(it.name)
                }
            }
            holder.binding.chapterNameTextView.text = name
            holder.binding.hadithCountTextView.text = holder.itemView.context.getString(
                R.string.total_number_of_hadith,
                item.numberOfHadith
            )
            holder.binding.numberTextView.text = item.bookNumber
            holder.setIsRecyclable(false)
        } catch (e: Exception) {

        }
    }
}