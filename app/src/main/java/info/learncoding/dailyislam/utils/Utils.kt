package info.learncoding.dailyislam.utils

import android.view.View
import androidx.fragment.app.Fragment
import info.learncoding.dailyislam.data.model.ApiError
import info.learncoding.dailyislam.data.model.ErrorType
import info.learncoding.dailyislam.databinding.ErrorStateLayoutBinding

fun Fragment.showError(binding: ErrorStateLayoutBinding, error: ApiError, tryAgain: () -> Unit) {
    binding.rootLayout.visibility = View.VISIBLE
    if (error.type == ErrorType.NO_DATA) {
        binding.tryAgain.visibility = View.GONE
    }
    binding.messageTextView.text = error.message
    binding.tryAgain.setOnClickListener {
        tryAgain.invoke()
    }
}

