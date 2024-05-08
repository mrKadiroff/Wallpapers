package com.shoxruxbek.wallpapers

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.shoxruxbek.wallpapers.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    private lateinit var appBarConfiguration: AppBarConfiguration
    lateinit var navController: NavController
    private lateinit var drawerToggle: ActionBarDrawerToggle
    private lateinit var callback: OnBackPressedCallback
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val toolbar: Toolbar = findViewById(R.id.toolbar_main)
        setSupportActionBar(toolbar)
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.purple_200))
        navController = findNavController(R.id.hostFragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.






        setDrawer()
        setSearch()





    }



    private fun setSearch() {
        binding.searchView.setOnClickListener {
            navController.navigate(R.id.searchResultFragment)
        }
                // Handle search query submission





    }

    private fun setDrawer() {
        val toolbar: Toolbar = findViewById(R.id.toolbar_main)
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.navigation_view)
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.homeFragment
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)


        drawerToggle = ActionBarDrawerToggle(
            this, binding.drawerLayout, binding.toolbarMain,
            R.string.nav_open, R.string.nav_close
        )

        drawerToggle.isDrawerIndicatorEnabled = true
        binding.drawerLayout.addDrawerListener(drawerToggle)
        drawerToggle.syncState()
        drawerToggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.white));



        getSupportActionBar()!!.setDisplayShowTitleEnabled(false)
        toolbar.subtitle = "Search..."

        // Traverse through the Toolbar's child views to find the subtitle TextView
        for (i in 0 until toolbar.childCount) {
            val view = toolbar.getChildAt(i)
            if (view is TextView && view.text == toolbar.subtitle) {
                // Set the text color of the subtitle
                view.setTextColor(Color.WHITE)
                break
            }
        }




        // Set the navigation item selected listener
        binding.navigationView.setNavigationItemSelectedListener { menuItem ->
            // Handle navigation item clicks here
            when (menuItem.itemId) {
                R.id.nav_account -> {
                    // Handle click on Item 1
                    showToast("Item 1 clicked")
                }
                // Add cases for other items if needed
            }
            // Close the drawer when item is clicked
            true
        }
    }

    private fun showToast(s: String) {
        navController.navigate(R.id.infoFragment)
        binding.drawerLayout.closeDrawer(GravityCompat.START)
    }



    fun showBottomNavigation()
    {

        binding.toolbarMain.visibility = View.VISIBLE



    }

    fun hideBottomNavigation()
    {

        binding.toolbarMain.visibility = View.GONE
        val fragmenttt = findViewById<View>(R.id.hostFragment)
    }

    override fun onResume() {
        binding.toolbarMain.title = ""
        super.onResume()
    }



}