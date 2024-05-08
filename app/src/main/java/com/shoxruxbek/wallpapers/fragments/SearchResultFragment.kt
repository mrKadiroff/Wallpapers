package com.shoxruxbek.wallpapers.fragments

import android.content.Context
import android.os.Bundle
import android.os.Parcelable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.shoxruxbek.wallpapers.MainActivity
import com.shoxruxbek.wallpapers.R
import com.shoxruxbek.wallpapers.adapters.PhotoAdapter2
import com.shoxruxbek.wallpapers.databinding.FragmentSearchResultBinding
import com.shoxruxbek.wallpapers.models.Hit
import com.shoxruxbek.wallpapers.viewmodels.UserViewModel


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

    lateinit var binding: FragmentSearchResultBinding
    lateinit var photoAdapter: PhotoAdapter2
    lateinit var userViewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSearchResultBinding.inflate(layoutInflater, container, false)

        searchView()

        val sharedPref = binding.root.context.getSharedPreferences(
            "my_shared_pref", Context.MODE_PRIVATE
        )

        // Retrieve the string from SharedPreferences
        val savedString = sharedPref.getString("my_string_key1", "")

        if (savedString!!.isNotEmpty()){
            setRV(savedString!!)
        }

        
        binding.toolbarMain2.setOnClickListener {
            Toast.makeText(binding.root.context, "Clicked", Toast.LENGTH_SHORT).show()
            clearData()
        }

        return binding.root
    }

    private fun clearData(){
        photoAdapter = PhotoAdapter2(viewLifecycleOwner, object : PhotoAdapter2.OnItemClickListener {
            override fun onItemClick(hit: Hit?) {

            }
        })
        binding.rvAll.adapter = photoAdapter

        photoAdapter.clearList()
    }

    private fun searchView() {
        binding.searchView2.requestFocus()

        binding.searchView2.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                // Handle search query submission
                if (!query.isNullOrEmpty()) {
                    setRV(query)
                    saveOrUpdateStringToSharedPreferences(query)
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                // Filter your data based on newText
                // Example: filterRecyclerView(newText)
                return true
            }
        })
    }

    private fun saveOrUpdateStringToSharedPreferences(query: String) {
        // Get SharedPreferences instance
        val sharedPref = binding.root.context.getSharedPreferences(
            "my_shared_pref", Context.MODE_PRIVATE
        )

        // Retrieve the existing string from SharedPreferences
        val existingString = sharedPref.getString("my_string_key1", "")

        // If no existing string, or if it's different from the new string, update it
        if (existingString.isNullOrEmpty() || existingString != query) {
            val editor = sharedPref.edit()
            editor.putString("my_string_key1", query)
            editor.apply()
        }
    }

    private fun setRV(word: String) {
        val s = word
        photoAdapter = PhotoAdapter2(viewLifecycleOwner, object : PhotoAdapter2.OnItemClickListener {
            override fun onItemClick(hit: Hit?) {
                var bundle = Bundle()
                bundle.putSerializable("wall", hit!!.largeImageURL)
                findNavController().navigate(R.id.searchRawFragment, bundle)
            }
        })
        binding.rvAll.adapter = photoAdapter

        photoAdapter.clearList()
        userViewModel = ViewModelProvider(this)[UserViewModel::class.java]
        userViewModel.setSearchQuery(s)
        userViewModel.liveData.observe(viewLifecycleOwner, Observer { pagingData ->
            photoAdapter.updateList(pagingData) // Update with new data
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


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable("RECYCLER_VIEW_STATE", binding.rvAll.layoutManager?.onSaveInstanceState())
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        savedInstanceState?.let {
            val state = it.getParcelable<Parcelable>("RECYCLER_VIEW_STATE")
            binding.rvAll.layoutManager?.onRestoreInstanceState(state)

        }
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