package info.learncoding.dailyislam.ui.chapter

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import info.learncoding.dailyislam.R
import info.learncoding.dailyislam.data.model.ApiError
import info.learncoding.dailyislam.data.model.HadithBook
import info.learncoding.dailyislam.data.model.HadithCollection
import info.learncoding.dailyislam.data.network.DataState
import info.learncoding.dailyislam.databinding.HadithBookFragmentBinding
import info.learncoding.dailyislam.utils.RecyclerViewOuterPaddingDecoration
import info.learncoding.dailyislam.utils.showError

@AndroidEntryPoint
class HadithBookFragment : Fragment() {
    companion object {
        private const val TAG = "HadithBookFragment"
    }

    private lateinit var hadithBookRecyclerAdapter: HadithBookRecyclerAdapter
    private lateinit var viewModel: HadithBookViewModel
    private lateinit var chapterFragmentBinding: HadithBookFragmentBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        chapterFragmentBinding =
            DataBindingUtil.inflate(inflater, R.layout.hadith_book_fragment, container, false)
        viewModel = ViewModelProvider(this).get(HadithBookViewModel::class.java)
        return chapterFragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        arguments?.let {
            val args = HadithBookFragmentArgs.fromBundle(it)

            hadithBookRecyclerAdapter = HadithBookRecyclerAdapter()
            hadithBookRecyclerAdapter.itemClickListener = { _, item, _ ->
                val action = HadithBookFragmentDirections.actionChapterFragmentToHadithFragment(
                    args.hadithCollection,
                    item
                )
                findNavController().navigate(action)
            }
            chapterFragmentBinding.chapterRecyclerView.apply {
                setHasFixedSize(true)
                adapter = hadithBookRecyclerAdapter
                addItemDecoration(RecyclerViewOuterPaddingDecoration(10, 10, 10, 10))
            }


            args.hadithCollection?.let { collection ->
                subscribeHadithBooksObserver(collection)
                viewModel.fetchHadithBooks(collection.name)
            } ?: run {
                Toast.makeText(context, getText(R.string.please_select_a_book), Toast.LENGTH_SHORT)
                    .show()
                findNavController().popBackStack()
            }
        }

    }

    private fun subscribeHadithBooksObserver(collection: HadithCollection) {
        viewModel.books.observe(viewLifecycleOwner, {
            when (it) {
                is DataState.Loading -> {
                    showProgress(true)
                }
                is DataState.Loaded -> {
                    showProgress(false)
                    showData(it.data)
                }
                is DataState.Failed -> {
                    showProgress(false)
                    showError(it.error, collection)
                }
            }
        })

    }


    private fun showProgress(isShow: Boolean) {
        if (isShow) {
            chapterFragmentBinding.progressBar.visibility = View.VISIBLE
            chapterFragmentBinding.chapterRecyclerView.visibility = View.GONE
            chapterFragmentBinding.errorLayout.rootLayout.visibility = View.GONE
        } else {
            chapterFragmentBinding.progressBar.visibility = View.GONE
        }
    }

    private fun showData(data: List<HadithBook>) {
        chapterFragmentBinding.chapterRecyclerView.visibility = View.VISIBLE
        hadithBookRecyclerAdapter.addItems(data)
    }

    private fun showError(error: ApiError, collection: HadithCollection) {
        showError(chapterFragmentBinding.errorLayout, error) {
            viewModel.fetchHadithBooks(collection.name)
        }
    }

}