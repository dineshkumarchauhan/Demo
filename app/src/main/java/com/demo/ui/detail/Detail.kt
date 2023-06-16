package com.demo.ui.detail

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.demo.R
import com.demo.databinding.DetailBinding
import com.demo.model.ItemPhoto
import com.demo.utils.loadImage
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class Detail : Fragment() {

    private var _binding: DetailBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<DetailVM>()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DetailBinding.inflate(inflater)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var data  = arguments?.getParcelable<ItemPhoto>("data") as ItemPhoto

        data.let {
            binding!!.ivImage.loadImage(url = { data.image })
            var title = getString(R.string.title, data.title)
            binding.txtTitle.text = title
        }
    }
}