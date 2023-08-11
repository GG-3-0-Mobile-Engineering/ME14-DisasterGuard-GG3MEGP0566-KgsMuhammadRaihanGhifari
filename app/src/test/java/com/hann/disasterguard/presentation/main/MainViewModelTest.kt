package com.hann.disasterguard.presentation.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.hann.disasterguard.coreapp.domain.usecase.DisasterUseCase
import com.hann.disasterguard.coreapp.resource.Resource
import com.hann.disasterguard.utils.CoroutinesTestRule
import com.hann.disasterguard.utils.DataDummy
import com.hann.disasterguard.utils.MainDispatcherRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MainViewModelTest {

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    var coroutinesTestRule = CoroutinesTestRule()

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()


    private lateinit var mainViewModel: MainViewModel

    @Mock
    private lateinit var disasterUseCase: DisasterUseCase

    private val dummyFloodSuccessfully = DataDummy.generateDummyListGeometryFlood()

    @Before
    fun setUp(){
        mainViewModel = MainViewModel(disasterUseCase)
    }

    @Test
    fun `get flood level notification disaster successfully`() = runTest {
        val mockReportList = dummyFloodSuccessfully
        val expected = flowOf(Resource.Success(mockReportList))

        Mockito.`when`(disasterUseCase.getFloodLevel()).thenReturn(expected)

        mainViewModel.getFloodLevel()

        val resultData = mainViewModel.state.value

        expected.collect {
            Assert.assertNotNull(resultData)
            Assert.assertEquals(mockReportList, resultData?.flood)
            resultData?.error?.isBlank()?.let { it1 -> Assert.assertTrue(it1) }
        }
    }

}