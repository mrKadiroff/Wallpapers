package com.example.wallpapers.fragments

import android.app.AlertDialog
import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.fragment.findNavController
import com.example.wallpapers.R
import com.example.wallpapers.adapters.ViewPagerAdapter
import com.example.wallpapers.databinding.DialogueLayoutBinding
import com.example.wallpapers.databinding.FragmentHomeBinding
import com.example.wallpapers.databinding.FragmentHostBinding
import com.example.wallpapers.databinding.ItemTabBinding
import com.example.wallpapers.ombor.setData
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HostFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HostFragment : Fragment() {
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

    lateinit var binding: FragmentHostBinding
    private var lastClickedButton: ConstraintLayout? = null
    private var isDialogShown = false
     var valuebek = "Happy"
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHostBinding.inflate(layoutInflater,container,false)


        navigateBack()




        if (!isDialogShown) {
            setDialuge()
            setviewPager()
            isDialogShown = true
        }



        return binding.root
    }


    private fun displayStringFromSharedPreferences() {
        // Get SharedPreferences instance
        val sharedPref = binding.root.context.getSharedPreferences(
            "my_shared_pref", Context.MODE_PRIVATE
        )

        // Retrieve the string from SharedPreferences
        val savedString = sharedPref.getString("my_string_key", "")

        // Display the string in TextView
        valuebek = savedString!!
        Toast.makeText(binding.root.context, savedString, Toast.LENGTH_SHORT).show()

        if (savedString == ""){
            updateViewPager("Happy")
        }else{
            updateViewPager(savedString)
        }

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

    private fun setDialuge() {
        val alertDialog = AlertDialog.Builder(binding.root.context,R.style.dialog)
        val dialog = alertDialog.create()
        val dialogView = DialogueLayoutBinding.inflate(
            LayoutInflater.from(binding.root.context),null,false
        )


        val birinchi = dialogView.birinchi


        val button1 = dialogView.backg1
        val button2 = dialogView.backg2
        val button3 = dialogView.backg3
        val button4 = dialogView.backg4
        val button5 = dialogView.backg5
        val button6 = dialogView.backg6
        val button7 = dialogView.backg7
        val button8 = dialogView.backg8
        val button9 = dialogView.backg9








        dialogView.birinchi.setOnClickListener {
            val toString = dialogView.first.text.toString()
            dialogView.experim.text = toString
            onButtonClick(button1)
        }

        dialogView.ikkinchi.setOnClickListener {
            val toString = dialogView.second.text.toString()
            dialogView.experim.text = toString
            onButtonClick(button2)
        }

        dialogView.uchinchi.setOnClickListener {
            val toString = dialogView.third.text.toString()
            dialogView.experim.text = toString
            onButtonClick(button3)
        }

        dialogView.toriticnhi.setOnClickListener {
            val toString = dialogView.fourth.text.toString()
            dialogView.experim.text = toString
            onButtonClick(button4)
        }

        dialogView.beshinchi.setOnClickListener {
            val toString = dialogView.fifth.text.toString()
            dialogView.experim.text = toString
            onButtonClick(button5)
        }

        dialogView.oltinchi.setOnClickListener {
            val toString = dialogView.sixth.text.toString()
            dialogView.experim.text = toString
            onButtonClick(button6)
        }

        dialogView.yettinchi.setOnClickListener {
            val toString = dialogView.seveneth.text.toString()
            dialogView.experim.text = toString
            onButtonClick(button7)
        }


        dialogView.sakkizinchi.setOnClickListener {
            val toString = dialogView.eighth.text.toString()
            dialogView.experim.text = toString
            onButtonClick(button8)
        }


        dialogView.toqqizinchi.setOnClickListener {
            val toString = dialogView.ninth.text.toString()
            dialogView.experim.text = toString
            onButtonClick(button9)
        }





        dialogView.addd.setOnClickListener {
            val toString = dialogView.experim.text.toString()

            if (toString.isEmpty()){
                Toast.makeText(binding.root.context, "Please select your current mood", Toast.LENGTH_SHORT).show()
            }else{
                dialog.cancel()
                updateViewPager(toString)
                saveOrUpdateStringToSharedPreferences(toString)
            }

        }





        dialogView.cancel.setOnClickListener {
            dialog.cancel()
        }






        dialog.setView(dialogView.root)
        dialog.show()
    }

    private fun saveOrUpdateStringToSharedPreferences(newString: String) {


        // Get SharedPreferences instance
        val sharedPref = binding.root.context.getSharedPreferences(
            "my_shared_pref", Context.MODE_PRIVATE
        )

        // Retrieve the existing string from SharedPreferences
        val existingString = sharedPref.getString("my_string_key", "")

        // If no existing string, or if it's different from the new string, update it
        if (existingString.isNullOrEmpty() || existingString != newString) {
            val editor = sharedPref.edit()
            editor.putString("my_string_key", newString)
            editor.apply()
        }
    }


    private fun onButtonClick(clickedButton: ConstraintLayout) {

        // Reset background color of the previously clicked button
        lastClickedButton?.setBackgroundColor(Color.WHITE)

        // Change background color of the current button
        clickedButton.setBackgroundColor(Color.BLUE)

        // Update last clicked button
        lastClickedButton = clickedButton
    }


    private fun updateViewPager(toString: String) {
        val viewPager = binding.viewPager
        val tabLayout = binding.tabLayout


//        if (toString == "Happy"){
//          UpdateTabs(setData.tabArray1)
//
//
//
//        } else if (toString == "Sad"){
//            Toast.makeText(this, toString, Toast.LENGTH_SHORT).show()
//            UpdateTabs(setData.tabArray2)
//        }




        when (toString) {
            "Happy" -> UpdateTabs(setData.tabArray1)
            "Sad" -> UpdateTabs(setData.tabArray2)
            "Energetic" -> UpdateTabs(setData.tabArray3)
            "Calm" -> UpdateTabs(setData.tabArray4)
            "Angry" -> UpdateTabs(setData.tabArray5)
            "Stressed" -> UpdateTabs(setData.tabArray6)
            "Inspired" -> UpdateTabs(setData.tabArray7)
            "Playful" -> UpdateTabs(setData.tabArray8)
            "Romantic" -> UpdateTabs(setData.tabArray9)
            else -> println("No matching function for the input")
        }



    }


    private fun UpdateTabs(tabbi: Array<String>) {
        val viewPager = binding.viewPager
        val tabLayout = binding.tabLayout
        val adapter = ViewPagerAdapter(tabbi,childFragmentManager, lifecycle)
        viewPager.adapter = adapter

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            val itemTabBinding: ItemTabBinding = ItemTabBinding.inflate(layoutInflater)
            tab.customView = itemTabBinding.root
            itemTabBinding.text.text = tabbi[position]
            if (position == 0) {
                itemTabBinding.circle.visibility = View.VISIBLE
                itemTabBinding.text.setTextColor(Color.WHITE)
            } else {
                itemTabBinding.circle.visibility = View.INVISIBLE
                itemTabBinding.text.setTextColor(Color.parseColor("#808a93"))
            }

        }.attach()
    }

    private fun setviewPager() {
        val viewPager = binding.viewPager
        val tabLayout = binding.tabLayout


        val adapter = ViewPagerAdapter(setData.tabArray1,childFragmentManager, lifecycle)
        viewPager.adapter = adapter


        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            val itemTabBinding: ItemTabBinding = ItemTabBinding.inflate(layoutInflater)
            tab.customView = itemTabBinding.root
            itemTabBinding.text.text = setData.tabArray1[position]
            if (position == 0) {
                itemTabBinding.circle.visibility = View.VISIBLE
                itemTabBinding.text.setTextColor(Color.WHITE)
            } else {
                itemTabBinding.circle.visibility = View.INVISIBLE
                itemTabBinding.text.setTextColor(Color.parseColor("#808a93"))
            }

        }.attach()



        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                val itemTabBinding = ItemTabBinding.bind(tab?.customView!!)
                itemTabBinding.circle.visibility = View.VISIBLE
                itemTabBinding.text.setTextColor(Color.WHITE)
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                val itemTabBinding = ItemTabBinding.bind(tab?.customView!!)
                itemTabBinding.circle.visibility = View.INVISIBLE
                itemTabBinding.text.setTextColor(Color.parseColor("#808a93"))
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

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
         * @return A new instance of fragment HostFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HostFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}