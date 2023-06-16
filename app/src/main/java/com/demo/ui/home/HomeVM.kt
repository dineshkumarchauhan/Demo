package com.demo.ui.home

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.demo.databinding.ItemPhotosBinding
import com.demo.genericAdapter.GenericAdapter
import com.demo.model.ItemPhoto
import com.demo.model.Items
import com.demo.networking.ApiInterface
import com.demo.networking.CallHandler
import com.demo.networking.Repository
import com.demo.utils.loadImage
import com.demo.utils.navigateDirection
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class HomeVM @Inject constructor(
    private val repository: Repository
) : ViewModel() {


    val photosAdapter = object : GenericAdapter<ItemPhotosBinding, ItemPhoto>() {
        override fun onCreateView(
            inflater: LayoutInflater,
            parent: ViewGroup,
            viewType: Int
        ) = ItemPhotosBinding.inflate(inflater, parent, false)

        override fun onBindHolder(binding: ItemPhotosBinding, dataClass: ItemPhoto, position: Int) {
            binding.txtTitle.text = dataClass.title
            binding!!.ivIcon.loadImage(url = { dataClass.image })
            binding.root.setOnClickListener {
                it.navigateDirection(HomeDirections.actionHomeToDetail(dataClass))
            }
        }
    }

    init {
        //photosAdapter.submitList(listOf(false, false, false, false, false, false, false))
    }

    fun getPhotos(callBack: Items.() -> Unit){
        viewModelScope.launch {
            repository.callApi(
                callHandler = object : CallHandler<Response<Items>> {
                    override suspend fun sendRequest(apiInterface: ApiInterface) =
                        apiInterface.getPhotos()

                    override fun success(response: Response<Items>) {
                        if (response.isSuccessful){
                            callBack(response.body()!!)
//                            Log.e("TAG", "successAAACC "+response.body()!!.toString())
                        }
                    }

                    override fun error(message: String) {
                        super.error(message)
                        Log.e("TAG", "errorAAA")
                    }

                    override fun loading() {
                        super.loading()
                        Log.e("TAG", "loadingAAA")
                    }

                }
            )
        }
    }


}