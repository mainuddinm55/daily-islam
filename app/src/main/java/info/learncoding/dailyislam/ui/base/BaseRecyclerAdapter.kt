package info.learncoding.dailyislam.ui.base

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView


abstract class BaseRecyclerAdapter<T, VH : ViewDataBinding> :
    RecyclerView.Adapter<BaseRecyclerAdapter.BaseViewHolder<VH>>() {
    protected val items = mutableListOf<T>()
    var itemClickListener: ((view: View, item: T, position: Int) -> Unit)? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<VH> {
        val binding = DataBindingUtil.inflate<VH>(
            LayoutInflater.from(parent.getContext()),
            getLayout(),
            parent,
            false
        )

        val viewHolder = BaseViewHolder(binding)
        viewHolder.itemView.setOnClickListener {
            itemClickListener?.invoke(
                it,
                items[viewHolder.adapterPosition],
                viewHolder.adapterPosition
            )
        }
        return viewHolder
    }

    fun addItems(items: List<T>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    fun addItem(item: T) {
        this.items.add(item)
        notifyDataSetChanged()
    }

    fun getItem(position: Int): T {
        return this.items[position]
    }

    override fun getItemCount(): Int {
        return items.size
    }

    abstract fun getLayout(): Int

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    class BaseViewHolder<VH : ViewDataBinding?>(val binding: VH) : RecyclerView.ViewHolder(
        binding!!.root
    )

}