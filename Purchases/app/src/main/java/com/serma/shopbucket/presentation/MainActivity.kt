package com.serma.shopbucket.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.serma.shopbucket.R
import com.serma.shopbucket.di.AppComponent
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        AppComponent.init(application).inject(this)
        val navController = findNavController(R.id.navHostFragment)
        if (auth.currentUser == null) {
            navController.navigate(R.id.loginFragment)
        } else {
            navController.navigate(R.id.purchaseListFragment)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == R.id.logout) {
            auth.signOut()
            val navOptions = NavOptions.Builder().setPopUpTo(R.id.loginFragment, true).build()
            findNavController(R.id.navHostFragment).navigate(R.id.loginFragment, null, navOptions)
            true
        } else {
            super.onOptionsItemSelected(item)
        }
    }
}
