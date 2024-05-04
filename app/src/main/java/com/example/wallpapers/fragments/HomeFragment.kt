package com.example.wallpapers.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.wallpapers.R
import com.example.wallpapers.adapters.PhotoAdapter
import com.example.wallpapers.databinding.FragmentHomeBinding
import com.example.wallpapers.models.Hit
import com.example.wallpapers.viewmodels.UserViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var categoryID: Int? = null
    private var name: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            categoryID = it.getInt(ARG_PARAM1)
            name = it.getString(ARG_PARAM2)

        }
    }

    lateinit var binding: FragmentHomeBinding
    lateinit var photoAdapter: PhotoAdapter
    lateinit var userViewModel: UserViewModel
    private val TAG = "HomeFragment"
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(layoutInflater,container,false)




        setRv()
        Check()




        return binding.root
    }

    private fun setRv() {
        photoAdapter = PhotoAdapter(object:PhotoAdapter.OnItemClickListener{
            override fun onItemClick(hit: Hit?) {
                var bundle = Bundle()
//                bundle.putSerializable("wall",hit!!.largeImageURL)
//                navController.navigate(R.id.rawFragment,bundle)
            }

        })
        binding.rvAll.adapter = photoAdapter

        userViewModel = ViewModelProvider(this)[UserViewModel::class.java]
        userViewModel.word = name.toString()
        userViewModel.liveData.observe(viewLifecycleOwner, Observer {

            GlobalScope.launch(Dispatchers.Main) {
                photoAdapter.submitData(it)
            }
        })


    }

    private fun Check() {
//                RetrofitClient.apiService.getListPhotos("27240519-6e85e045b4edde1049de33f01","spring","photo",true,1,15).enqueue(object:
//        Callback<Rasmlar>{
//            override fun onResponse(call: Call<Rasmlar>, response: Response<Rasmlar>) {
//                val body = response.body()
//                Log.d(TAG, "onResponse: ${body!!.hits}")
//
//            }
//
//            override fun onFailure(call: Call<Rasmlar>, t: Throwable) {
//                Log.d(TAG, "onFailure: ${t.message}")
//            }
//
//
//        })


    }


//    private fun setRv() {
//        photoAdapter = PhotoAdapter(object:PhotoAdapter.OnItemClickListener{
//            override fun onItemClick(hit: Hit?) {
//                var bundle = Bundle()
//                bundle.putSerializable("wall",hit!!.largeImageURL)
//
//            }
//
//        })
//        binding.rvAll.adapter = photoAdapter
//
//
//        userViewModel = ViewModelProvider(this)[UserViewModel::class.java]
//        userViewModel.word = name.toString()
//        userViewModel.liveData.observe(viewLifecycleOwner, Observer {
//
//
//            Log.d(TAG, "setRv:$name ")
//
//            GlobalScope.launch(Dispatchers.Main) {
//                photoAdapter.submitData(it)
//            }
//        })
//    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment HomeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(categoryID: Int, name: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_PARAM1, categoryID)
                    putString(ARG_PARAM2,name)
                }
            }
    }
}