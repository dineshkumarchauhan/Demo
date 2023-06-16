package com.demo.ui.home

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.demo.databinding.HomeBinding
import com.demo.model.ItemPhoto
import com.demo.model.Items
import dagger.hilt.android.AndroidEntryPoint
import com.demo.utils.checkIsTablet

@AndroidEntryPoint
class Home : Fragment() {

    var items : ArrayList<ItemPhoto> = ArrayList()

    private var _binding: HomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<HomeVM>()

//    var photoItems: Items? = null
    var isWatcher  = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = HomeBinding.inflate(inflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        viewModel.getPhotos() {
            var photoItems = this
            if (this != null) {
                if (requireActivity().checkIsTablet()) {
                    binding.rcPhotosList.setLayoutManager(GridLayoutManager(requireContext(), 7))
                } else {
                    binding.rcPhotosList.setLayoutManager(GridLayoutManager(requireContext(), 3))
                }
                binding.rcPhotosList.adapter = viewModel.photosAdapter


                if(isWatcher == false){
                    viewModel.photosAdapter.submitList(photoItems)
                    viewModel.photosAdapter.notifyDataSetChanged()
                }

                binding.editTextSearch.addTextChangedListener(object : TextWatcher {
                    override fun afterTextChanged(s: Editable?) {
                        Log.e("TAG", "afterTextChanged")
                    }

                    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                        Log.e("TAG", "beforeTextChanged")
                    }

                    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                        Log.e("TAG", "onTextChanged")
                        isWatcher = true
                        if(s.toString().isEmpty()){
                            viewModel.photosAdapter.submitList(photoItems)
                            items.clear()
                        }else{
                            items.clear()
                            photoItems?.map {
                                if(it.title.lowercase().contains(s.toString().lowercase())){
                                    items.add(it)
                                }
                            }
                            viewModel.photosAdapter.submitList(items)
                        }
                        viewModel.photosAdapter.notifyDataSetChanged()
                    }
                })

            }
        }


    }




    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

}