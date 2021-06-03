package info.learncoding.dailyislam.ui.hadith

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import dagger.hilt.android.AndroidEntryPoint
import info.learncoding.dailyislam.R
import info.learncoding.dailyislam.data.model.ApiError
import info.learncoding.dailyislam.data.model.Hadith
import info.learncoding.dailyislam.data.model.HadithBook
import info.learncoding.dailyislam.data.model.HadithCollection
import info.learncoding.dailyislam.data.network.DataState
import info.learncoding.dailyislam.databinding.HadithFragmentBinding
import info.learncoding.dailyislam.ui.chapter.HadithBookFragmentArgs
import info.learncoding.dailyislam.ui.chapter.HadithBookRecyclerAdapter
import info.learncoding.dailyislam.utils.RecyclerViewOuterPaddingDecoration
import info.learncoding.dailyislam.utils.showError

@AndroidEntryPoint
class HadithFragment : Fragment() {
    private lateinit var fragmentHadithBinding: HadithFragmentBinding
    private lateinit var hadithRecyclerAdapter: HadithRecyclerAdapter
    private lateinit var viewModel: HadithViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragmentHadithBinding =
            DataBindingUtil.inflate(inflater, R.layout.hadith_fragment, container, false)
        viewModel = ViewModelProvider(this).get(HadithViewModel::class.java)
        return fragmentHadithBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        arguments?.let {
            hadithRecyclerAdapter = HadithRecyclerAdapter()
            hadithRecyclerAdapter.itemClickListener = { view, item, position ->
                val action =
                    HadithFragmentDirections.actionHadithFragmentToHadithDetailsFragment(item)
                findNavController().navigate(action)
            }
            fragmentHadithBinding.hadithRecyclerView.apply {
                setHasFixedSize(true)
                adapter = hadithRecyclerAdapter
                addItemDecoration(RecyclerViewOuterPaddingDecoration(10, 10, 10, 10))
            }

            val args = HadithFragmentArgs.fromBundle(it)
            if (args.hadithBook != null && args.hadithCollection != null) {
                subscribeHadithBooksObserver(args.hadithCollection!!, args.hadithBook!!)
                viewModel.fetchHadith(args.hadithCollection!!.name, args.hadithBook!!.bookNumber)
            } else {
                Toast.makeText(context, getText(R.string.please_select_a_book), Toast.LENGTH_SHORT)
                    .show()
                findNavController().popBackStack()
            }
        }

    }

    private fun subscribeHadithBooksObserver(collection: HadithCollection, book: HadithBook) {
        viewModel.hadith.observe(viewLifecycleOwner, {
            when (it) {
                is DataState.Loading -> {
                    showProgress(true)
                }
                is DataState.Loaded -> {
                    showProgress(false)
                    showData(it.data)
                    Log.d("TAG", "subscribeHadithBooksObserver: ${it.data.size}")
                }
                is DataState.Failed -> {
                    showProgress(false)
                    showError(it.error, collection, book)
                }
            }
        })

    }


    private fun showProgress(isShow: Boolean) {
        if (isShow) {
            fragmentHadithBinding.progressBar.visibility = View.VISIBLE
            fragmentHadithBinding.hadithRecyclerView.visibility = View.GONE
            fragmentHadithBinding.errorLayout.rootLayout.visibility = View.GONE
        } else {
            fragmentHadithBinding.progressBar.visibility = View.GONE
        }
    }

    private fun showData(data: List<Hadith>) {
        fragmentHadithBinding.hadithRecyclerView.visibility = View.VISIBLE
        hadithRecyclerAdapter.addItems(data)
    }

    private fun showError(error: ApiError, collection: HadithCollection, book: HadithBook) {
        showError(fragmentHadithBinding.errorLayout, error) {
            viewModel.fetchHadith(collection.name, book.bookNumber)
        }
    }

}