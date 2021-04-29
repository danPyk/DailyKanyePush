package com.beta.kanyenotifications.ui.about

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.beta.kanyenotifications.R
import com.beta.kanyenotifications.databinding.FragmentAboutBinding


class AboutFragment : Fragment() {

    private lateinit var aboutViewModel: AboutViewModel
    private var _binding: FragmentAboutBinding? = null
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        aboutViewModel =
            ViewModelProvider(this).get(AboutViewModel::class.java)
        //val imageView: ImageView = activity?.findViewById(R.id.imegeViewAbout) ?:

        _binding = FragmentAboutBinding.inflate(inflater, container, false)

        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding?.imegeViewAbout?.setOnClickListener { composeEmail() }


    }

    @SuppressLint("QueryPermissionsNeeded")
    fun composeEmail() {
        val eMailIds: Array<String> = arrayOf(getString(R.string.email))
        val intent = Intent(Intent.ACTION_SENDTO)
        intent.data = Uri.parse("mailto:") // only email apps should handle this

        intent.putExtra(Intent.EXTRA_EMAIL, eMailIds)
        intent.putExtra(Intent.EXTRA_SUBJECT, "Kanye notifications contact")

        if (activity?.let { intent.resolveActivity(it.packageManager) } != null) {
            startActivity(intent)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null

    }
}

