package com.example.wallpapers.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.wallpapers.MainActivity
import com.example.wallpapers.R
import com.example.wallpapers.adapters.PhotoAdapter
import com.example.wallpapers.databinding.FragmentHomeBinding
import com.example.wallpapers.databinding.FragmentSearchResultBinding
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
 * Use the [SearchResultFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SearchResultFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    lateinit var binding: FragmentSearchResultBinding
    lateinit var photoAdapter: PhotoAdapter
    lateinit var userViewModel: UserViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSearchResultBinding.inflate(layoutInflater,container,false)




        setRV()
        navigateBack()


        return binding.root
    }

    private fun navigateBack() {
        val navController = findNavController()

        navController.addOnDestinationChangedListener { _, destination, _ ->
            // Check if the destination is your first fragment
            if (destination.id == R.id.searchResultFragment) {
                // Your logic when navigating back to the first fragment
                // This block will be executed whenever you navigate to the first fragment
                Toast.makeText(binding.root.context, "Keldingmi?", Toast.LENGTH_SHORT).show()
                (activity as MainActivity).hideBottomNavigation()
            }
        }

    }



    private fun setRV() {
        val bundle = arguments
        val s = bundle!!.getString("wall")
        photoAdapter = PhotoAdapter(object:PhotoAdapter.OnItemClickListener{
            override fun onItemClick(hit: Hit?) {
                var bundle = Bundle()
                bundle.putSerializable("wall",hit!!.largeImageURL)
                findNavController().navigate(R.id.rawFragment,bundle)
            }

        })
        binding.rvAll.adapter = photoAdapter

        userViewModel = ViewModelProvider(this)[UserViewModel::class.java]
        userViewModel.word = s!!
        userViewModel.liveData.observe(viewLifecycleOwner, Observer {

            GlobalScope.launch(Dispatchers.Main) {
                photoAdapter.submitData(it)
            }
        })
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity as MainActivity).hideBottomNavigation()
    }

    override fun onDetach() {
        (activity as MainActivity).showBottomNavigation()
        super.onDetach()

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SearchResultFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SearchResultFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}