package com.hann.disasterguard.presentation.map

import com.hann.disasterguard.coreapp.utils.Utils
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MapFragmentTest {

    @Test
    fun testGetRegionCode() {
        val regionCodeConverter = Utils.getRegionCode("Aceh")
        assertEquals("ID-AC", regionCodeConverter)
    }


}