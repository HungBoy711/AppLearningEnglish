package com.example.learningapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import androidx.appcompat.app.ActionBarDrawerToggle
import android.widget.Toast
import com.example.learningapp.R


class MainActivity : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var toggle: ActionBarDrawerToggle
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var navigationView: NavigationView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Toolbar setup
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        // Drawer setup
        drawerLayout = findViewById(R.id.my_drawer)
        navigationView = findViewById(R.id.navigation_view)

        toggle = ActionBarDrawerToggle(
            this,
            drawerLayout,
            toolbar,
            R.string.open,
            R.string.close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        val navController = findNavController(R.id.main_fragment)

        bottomNavigationView = findViewById(R.id.bottom_nav)

        // Link NavController with Bottom Nav and Navigation Drawer
        NavigationUI.setupWithNavController(bottomNavigationView, navController)
        NavigationUI.setupWithNavController(navigationView, navController)

        setupActionBarWithNavController(navController, drawerLayout)

        navigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.pdf -> {
                    Toast.makeText(this, "Ebook", Toast.LENGTH_SHORT).show()
                }
                R.id.share -> {
                    Toast.makeText(this, "Share", Toast.LENGTH_SHORT).show()
                }
                R.id.rate -> {
                    Toast.makeText(this, "Rate", Toast.LENGTH_SHORT).show()
                }
                R.id.about -> {
                    Toast.makeText(this, "About", Toast.LENGTH_SHORT).show()
                }
            }
            drawerLayout.closeDrawers() // Đóng Drawer sau khi chọn
            true
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.main_fragment)
        return NavigationUI.navigateUp(navController, drawerLayout) || super.onSupportNavigateUp()
    }
}
