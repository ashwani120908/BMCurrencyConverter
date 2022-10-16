package com.demo.bmcurrencyconverter.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.demo.bmcurrencyconverter.network.ApiHelper
import com.demo.bmcurrencyconverter.network.MainRepository
import com.demo.bmcurrencyconverter.utils.NetworkHelper
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*


internal class DetailsActivityViewModelTest {
    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    var apiHelper: ApiHelper? = mockk()

    @Mock
    var networkHelper: NetworkHelper = mockk()

    @Mock
    lateinit var mainRepository: MainRepository

    lateinit var viewModel: DetailsActivityViewModel


    @Before
    fun before() {
        mainRepository = apiHelper?.let { MainRepository(it) }!!
        viewModel = DetailsActivityViewModel(mainRepository, networkHelper)
    }

    @Test
    fun getHistoryData() {
        every { networkHelper.isNetworkConnected() } returns true
        viewModel.getHistoryData()

        Assert.assertNotNull(viewModel.rates)
    }

    @Test
    fun setLastThreeDates() {
        mockkStatic(Calendar::class)
        val firstApiFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        val date = LocalDate.parse("2018-08-07 00:00:00", firstApiFormat)

        val cal = Calendar.getInstance()
        cal.time = Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant())
        every { Calendar.getInstance() } returns cal

        var list = viewModel.setLastThreeDates()
        Assert.assertEquals(3, list.size)
        Assert.assertEquals("2018-08-07", list[0])
        Assert.assertEquals("2018-08-06", list[1])
        Assert.assertEquals("2018-08-04", list[2])
    }
}