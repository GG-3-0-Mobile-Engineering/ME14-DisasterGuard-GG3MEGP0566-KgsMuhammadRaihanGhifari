package com.hann.disasterguard.presentation.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.hann.disasterguard.R
import com.hann.disasterguard.databinding.ActivityMainBinding
import com.hann.disasterguard.presentation.archive.ArchiveFragment
import com.hann.disasterguard.presentation.map.MapFragment
import com.hann.disasterguard.presentation.report.ReportFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        val mapFragment = MapFragment()
        supportFragmentManager.beginTransaction()
            .replace(R.id.map_container, mapFragment)
            .commit()

        binding.apply {
            icDisasterLive.setOnClickListener {
                val reportFragment = ReportFragment()
                reportFragment.show(supportFragmentManager, reportFragment.tag)
            }
            icSetting.setOnClickListener {
                startActivity(Intent(this@MainActivity,
                    Class.forName("com.hann.disasterguard.settings.SettingActivity")))
            }
            icDisasterArchive.setOnClickListener {
                val archiveFragment = ArchiveFragment()
                archiveFragment.show(supportFragmentManager, archiveFragment.tag)
            }
        }

    }


}