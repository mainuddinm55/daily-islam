package info.learncoding.dailyislam.utils

import android.graphics.Rect
import androidx.recyclerview.widget.RecyclerView


class RecyclerViewOuterPaddingDecoration(
    private val top: Int,
    private val right: Int,
    private val bottom: Int,
    private val left: Int
) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(outRect: Rect, itemPosition: Int, parent: RecyclerView) {
        outRect.right = right
        outRect.left = left
        outRect.top = top
        outRect.bottom = bottom
    }

}