package com.hann.disasterguard.presentation.map

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.hann.disasterguard.coreapp.domain.usecase.DisasterUseCase
import com.hann.disasterguard.coreapp.resource.Resource
import com.hann.disasterguard.utils.CoroutinesTestRule
import com.hann.disasterguard.utils.DataDummy
import com.hann.disasterguard.utils.MainDispatcherRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class MapViewModelTest {

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    var coroutinesTestRule = CoroutinesTestRule()

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var mapViewModel: MapViewModel

    @Mock
    private lateinit var disasterUseCase: DisasterUseCase

    private val dummyMapSuccessfully = DataDummy.generateDummyListGeometryReport()
    private val dummyArchiveSuccessfully = DataDummy.generateDummyListArchiveReport()

    @Before
    fun setUp(){
        mapViewModel = MapViewModel(disasterUseCase)
    }

    @Test
    fun `get map disaster successfully`() = runTest {
        val mockReportList = dummyMapSuccessfully
        val expected = flowOf(Resource.Success(mockReportList))

        `when`(disasterUseCase.getLiveReport(null, null)).thenReturn(expected)

        mapViewModel.getReportLive(null, null)

        val resultData = mapViewModel.state.value

        expected.collect {
            assertEquals(mockReportList, resultData?.map )
            assertEquals(false, resultData?.isLoading )
            assertEquals("", resultData?.error )
        }
    }

    @Test
    fun `get archive disaster successfully`() = runTest {
        val mockArchiveList = dummyArchiveSuccessfully
        val expected = flowOf(Resource.Success(mockArchiveList))

        `when`(disasterUseCase.getArchiveReport("2023-08-09T07:00:00:0700",
            "2023-08-11T07:00:00:0700", geoformat = "geojson")).thenReturn(expected)

        mapViewModel.getArchiveReport("2023-08-09T07:00:00:0700",
            "2023-08-11T07:00:00:0700", geoformat = "geojson")

        val resultData = mapViewModel.stateArchive.value

        expected.collect {
            assertEquals(mockArchiveList, resultData?.archive )
            assertEquals(false, resultData?.isLoading )
            assertEquals("", resultData?.error )
        }
    }

}