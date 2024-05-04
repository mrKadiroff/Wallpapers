package com.example.wallpapers

import android.app.AlertDialog
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.wallpapers.adapters.ViewPagerAdapter
import com.example.wallpapers.databinding.ActivityMainBinding
import com.example.wallpapers.databinding.DialogueLayoutBinding
import com.example.wallpapers.databinding.ItemTabBinding
import com.example.wallpapers.ombor.setData
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {
    val tabArray = arrayOf(
        "All",
        "Car",
        "Animals",
        "Girls",
        "Weather"
    )
    lateinit var binding: ActivityMainBinding
    private var lastClickedButton: ConstraintLayout? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        setviewPager()
        setDialuge()


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
            }

        }





        dialogView.cancel.setOnClickListener {
            dialog.cancel()
        }






        dialog.setView(dialogView.root)
        dialog.show()
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
        val adapter = ViewPagerAdapter(tabbi,supportFragmentManager, lifecycle)
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


        val adapter = ViewPagerAdapter(setData.tabArray1,supportFragmentManager, lifecycle)
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
}