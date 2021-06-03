package info.learncoding.dailyislam.ui.books

import info.learncoding.dailyislam.R
import info.learncoding.dailyislam.data.model.HadithCollection
import info.learncoding.dailyislam.databinding.RowHadithCollectionBinding
import info.learncoding.dailyislam.ui.base.BaseRecyclerAdapter
import java.lang.Exception

class HadithCollectionRecyclerAdapter :
    BaseRecyclerAdapter<HadithCollection, RowHadithCollectionBinding>() {
    override fun getLayout(): Int {
        return R.layout.row_hadith_collection
    }

    val booksBg = arrayOf(
        R.drawable.light_red_bg,
        R.drawable.orange_bg,
        R.drawable.red_sky_blue,
        R.drawable.yewello_bg,
        R.drawable.sky_blue_bg
    )

    override fun onBindViewHolder(
        holder: BaseViewHolder<RowHadithCollectionBinding>,
        position: Int
    ) {
        try {
            val item = getItem(position)
            item.collection?.forEach {
                if ("en" == it.lang) {
                    holder.binding.nameTextView.text = it.title
                } else if ("ar" == it.lang) {
                    holder.binding.titleTextView.text = it.title
                }
                val booksPosition = position % 5
                holder.binding.bgImageView.setImageResource(booksBg[booksPosition])
            }
        } catch (e: Exception) {

        }
        holder.setIsRecyclable(false)
    }
}