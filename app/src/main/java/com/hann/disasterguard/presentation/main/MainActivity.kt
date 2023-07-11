package com.hann.disasterguard.presentation.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.hann.disasterguard.R
import com.hann.disasterguard.databinding.ActivityMainBinding
import com.hann.disasterguard.presentation.map.MapFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        supportFragmentManager.findFragmentByTag(MapFragment::class.java.simpleName)
            ?: MapFragment().let { fragment ->
                supportFragmentManager.beginTransaction()
                    .add(R.id.map_container, fragment, MapFragment::class.java.simpleName)
                    .commit()
            }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.option_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.action_settings -> {
                startActivity(Intent(this, Class.forName("com.hann.disasterguard.settings.SettingActivity")))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }





}