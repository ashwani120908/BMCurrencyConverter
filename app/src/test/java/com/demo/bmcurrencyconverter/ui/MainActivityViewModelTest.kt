package com.demo.bmcurrencyconverter.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import com.demo.bmcurrencyconverter.models.LatestRates
import com.demo.bmcurrencyconverter.network.ApiHelper
import com.demo.bmcurrencyconverter.network.MainRepository
import com.demo.bmcurrencyconverter.utils.NetworkHelper
import com.demo.bmcurrencyconverter.utils.Response
import io.mockk.mockk
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock


class MainActivityViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    var apiHelper: ApiHelper? = mockk()

    @Mock
    var networkHelper: NetworkHelper = mockk()

    @Mock
    lateinit var mainRepository: MainRepository

    lateinit var viewModel: MainActivityViewModel


    @Before
    fun before() {
        mainRepository = apiHelper?.let { MainRepository(it) }!!
        viewModel = MainActivityViewModel(mainRepository, networkHelper)
    }

    @Test
    fun getLatestRates() {
        var liveData: LiveData<Response<LatestRates>> = viewModel.getLatestRates()
        Assert.assertNotNull(liveData)
    }
}