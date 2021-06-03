package info.learncoding.dailyislam.ui.hadith_details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.databinding.DataBindingUtil
import info.learncoding.dailyislam.R
import info.learncoding.dailyislam.databinding.FragmentHadithDetailsBinding

class HadithDetailsFragment : Fragment() {
    private lateinit var fragmentHadithDetailsBinding: FragmentHadithDetailsBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        fragmentHadithDetailsBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_hadith_details, container, false)
        return fragmentHadithDetailsBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        arguments?.let { bundle ->
            val args = HadithDetailsFragmentArgs.fromBundle(bundle)
            args.hadithBook?.let {
                var hadith = ""
                var translatedHadith = ""
                val grades = StringBuilder()
                it.hadith?.forEach { info ->
                    if ("ar" == info.lang) {
                        hadith = info.body ?: ""
                        try {
                            grades.append(info.grades?.get(0)?.graded_by)
                        } catch (e: Exception) {
                        }

                    } else if ("en" == info.lang) {
                        translatedHadith = info.body ?: ""
                        try {
                            val enGrades = info.grades?.get(0)?.graded_by
                            if (!enGrades.isNullOrBlank() && grades.isNotBlank()) {
                                grades.append(" - ")
                                grades.append(enGrades)
                            }
                        } catch (e: Exception) {
                        }

                    }
                }
                fragmentHadithDetailsBinding.hadithTextView.text =
                    HtmlCompat.fromHtml(hadith, HtmlCompat.FROM_HTML_MODE_COMPACT)
                fragmentHadithDetailsBinding.translatedHadithTextView.text =
                    HtmlCompat.fromHtml(translatedHadith, HtmlCompat.FROM_HTML_MODE_COMPACT)
                fragmentHadithDetailsBinding.titleTranslateTextView.text =
                    getString(R.string.translate)
                fragmentHadithDetailsBinding.gradeByTextView.text = grades

            }
        }
    }

}