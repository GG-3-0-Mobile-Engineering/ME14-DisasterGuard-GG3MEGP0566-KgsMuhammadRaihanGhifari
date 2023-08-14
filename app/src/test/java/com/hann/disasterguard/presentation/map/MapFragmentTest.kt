package com.hann.disasterguard.presentation.map

import android.content.Context
import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.view.View
import androidx.core.content.ContextCompat
import com.hann.disasterguard.coreapp.R
import com.hann.disasterguard.coreapp.utils.Utils
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MapFragmentTest {

    @Mock
    private lateinit var view: View

    @Mock
    private lateinit var context: Context

    @Before
    fun setup() {
        `when`(view.context).thenReturn(context)
        `when`(context.resources).thenReturn(mock(Resources::class.java))
    }
    @Test
    fun testGetRegionCode() {
        val regionCodeConverter = Utils.getRegionCode("Aceh")
        assertEquals("ID-AC", regionCodeConverter)
    }

    @Test
    fun testGetTypeDisaster(){
        val expectedDrawable = mock(Drawable::class.java)
        `when`(ContextCompat.getDrawable(context, R.color.azure)).thenReturn(expectedDrawable)

        val typeDisaster = Utils.getTypeDisaster("flood", view)

        assertEquals(expectedDrawable, typeDisaster)
    }


}