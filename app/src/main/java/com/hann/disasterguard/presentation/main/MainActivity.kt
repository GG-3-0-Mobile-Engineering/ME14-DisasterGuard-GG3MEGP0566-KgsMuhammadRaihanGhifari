package com.hann.disasterguard.presentation.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.view.WindowCompat
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.hann.disasterguard.R
import com.hann.disasterguard.coreapp.utils.NOTIFICATION_CHANNEL_ID
import com.hann.disasterguard.coreapp.utils.TypeConverterEntity
import com.hann.disasterguard.databinding.ActivityMainBinding
import com.hann.disasterguard.notification.NotificationWorker
import com.hann.disasterguard.presentation.archive.ArchiveFragment
import com.hann.disasterguard.presentation.map.MapFragment
import com.hann.disasterguard.presentation.report.ReportFragment
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.TimeUnit


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private val mainViewModel : MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        WindowCompat.setDecorFitsSystemWindows(
            window,
            false
        )

        val mapFragment = MapFragment()
        supportFragmentManager.beginTransaction()
            .replace(R.id.map_container, mapFragment)
            .commit()


        mainViewModel.state.observe(this){
            if (it.flood.isNotEmpty()){
                val workManager = WorkManager.getInstance(this)
                val flood = TypeConverterEntity().fromGeometryFlood(it.flood[0])
                val data = workDataOf(NOTIFICATION_CHANNEL_ID to flood)
                val workRequest = PeriodicWorkRequest.Builder(NotificationWorker::class.java, 2, TimeUnit.HOURS)
                    .setInputData(data)
                    .build()
                workManager.enqueue(workRequest)
            }
        }


        val reportFragment = ReportFragment()
        reportFragment.show(supportFragmentManager, reportFragment.tag)

        binding.apply {
            icDisasterLive.setOnClickListener {

            }
            icDisasterArchive.setOnClickListener {
                val archiveFragment = ArchiveFragment()
                archiveFragment.show(supportFragmentManager, archiveFragment.tag)
            }
        }
    }


}