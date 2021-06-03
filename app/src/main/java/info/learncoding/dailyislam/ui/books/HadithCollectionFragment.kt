package info.learncoding.dailyislam.ui.books

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import info.learncoding.dailyislam.R
import info.learncoding.dailyislam.data.model.ApiError
import info.learncoding.dailyislam.data.model.ErrorType
import info.learncoding.dailyislam.data.model.HadithCollection
import info.learncoding.dailyislam.data.network.DataState
import info.learncoding.dailyislam.databinding.HadithCollectionFragmentBinding
import info.learncoding.dailyislam.utils.RecyclerViewOuterPaddingDecoration
import info.learncoding.dailyislam.utils.showError

@AndroidEntryPoint
open class HadithCollectionFragment : Fragment() {
    companion object {
        private const val TAG = "BookFragment"
    }

    private lateinit var viewModel: HadithCollectionViewModel
    private lateinit var fragmentBinding: HadithCollectionFragmentBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragmentBinding =
            DataBindingUtil.inflate(inflater, R.layout.hadith_collection_fragment, container, false)
        viewModel = ViewModelProvider(this).get(HadithCollectionViewModel::class.java)
        return fragmentBinding.root
    }

    private lateinit var hadithCollectionRecyclerAdapter: HadithCollectionRecyclerAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        hadithCollectionRecyclerAdapter = HadithCollectionRecyclerAdapter()
        hadithCollectionRecyclerAdapter.setHasStableIds(true)
        hadithCollectionRecyclerAdapter.itemClickListener = { _, item, _ ->
            val action =
                HadithCollectionFragmentDirections.actionBookFragmentToChapterFragment(item)
            findNavController().navigate(action)
        }
        fragmentBinding.hadithCollectionRecyclerView.apply {
            setHasFixedSize(true)
            adapter = hadithCollectionRecyclerAdapter
            addItemDecoration(RecyclerViewOuterPaddingDecoration(10, 10, 10, 10))
        }
        subscribeCollectionObserver()
        viewModel.fetchCollections()

    }

    private fun subscribeCollectionObserver() {
        viewModel.collections.observe(viewLifecycleOwner, {
            Log.d(TAG, "onViewCreated: $it")
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
                    showError(it.error)
                }
            }
        })
    }

    private fun showProgress(isShow: Boolean) {
        if (isShow) {
            fragmentBinding.progressBar.visibility = View.VISIBLE
            fragmentBinding.hadithCollectionRecyclerView.visibility = View.GONE
            fragmentBinding.errorLayout.rootLayout.visibility = View.GONE
        } else {
            fragmentBinding.progressBar.visibility = View.GONE
        }
    }

    private fun showData(data: List<HadithCollection>) {
        fragmentBinding.hadithCollectionRecyclerView.visibility = View.VISIBLE
        hadithCollectionRecyclerAdapter.addItems(data)
    }

    private fun showError(error: ApiError) {
        showError(fragmentBinding.errorLayout, error) {
            viewModel.fetchCollections()
        }
    }

}

