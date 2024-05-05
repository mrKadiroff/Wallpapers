package com.example.wallpapers.fragments

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
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
    private lateinit var callback: OnBackPressedCallback
    private val TAG = "HomeFragment"
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(layoutInflater,container,false)




        setRv()
        BackPage()
        navigateBack()



        return binding.root
    }



    private fun displayStringFromSharedPreferences() {
        // Get SharedPreferences instance
        val sharedPref = binding.root.context.getSharedPreferences(
            "my_shared_pref", Context.MODE_PRIVATE
        )

        // Retrieve the string from SharedPreferences
        val savedString = sharedPref.getString("my_string_key", "")

        Toast.makeText(binding.root.context, savedString, Toast.LENGTH_SHORT).show()





    }


    private fun navigateBack() {
        val navController = findNavController()

        navController.addOnDestinationChangedListener { _, destination, _ ->
            // Check if the destination is your first fragment
            if (destination.id == R.id.hostFragment) {
                // Your logic when navigating back to the first fragment
                // This block will be executed whenever you navigate to the first fragment

                displayStringFromSharedPreferences()

            }
        }

    }



    private fun BackPage() {
        // Create a callback to handle back button press
        callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {

                val builder = AlertDialog.Builder(requireContext())
                builder.setTitle("Exit")
                    .setMessage("Are you sure you want to exit the app?")
                    .setPositiveButton("Yes") { _, _ ->
                        requireActivity().finish()
                    }
                    .setNegativeButton("No") { dialog, _ ->
                        dialog.dismiss()
                        val sharedPref = binding.root.context.getSharedPreferences(
                            "my_shared_pref", Context.MODE_PRIVATE
                        )

                        // Retrieve the string from SharedPreferences
                        val savedString = sharedPref.getString("my_string_key", "")

                        Toast.makeText(binding.root.context, savedString, Toast.LENGTH_SHORT).show()

                    }
                    .show()
            }
        }

        // Add the callback to the fragment
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
    }

    private fun setRv() {
        photoAdapter = PhotoAdapter(object:PhotoAdapter.OnItemClickListener{
            override fun onItemClick(hit: Hit?) {
                var bundle = Bundle()
                bundle.putSerializable("wall",hit!!.largeImageURL)
                findNavController().navigate(R.id.rawFragment,bundle)
            }

        })
        binding.rvAll.adapter = photoAdapter

        userViewModel = ViewModelProvider(this)[UserViewModel::class.java]
        userViewModel.setSearchQuery(name!!)
        userViewModel.liveData.observe(viewLifecycleOwner, Observer {

            GlobalScope.launch(Dispatchers.Main) {
                photoAdapter.submitData(it)
            }
        })


    }



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