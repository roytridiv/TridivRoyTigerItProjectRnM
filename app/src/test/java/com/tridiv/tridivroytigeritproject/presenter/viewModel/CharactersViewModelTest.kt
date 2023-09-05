package com.tridiv.tridivroytigeritproject.presenter.viewModel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.tridiv.tridivroytigerit.presenter.viewModel.CharactersViewModel
import com.tridiv.tridivroytigeritproject.data.domain.ResultData
import com.tridiv.tridivroytigeritproject.data.model.networkPojo.CharactersListResp.CharactersRespBody
import com.tridiv.tridivroytigeritproject.data.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.*
import org.junit.Assert.*

import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations


class CharactersViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
@Mock
    lateinit var repository: Repository

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(testDispatcher)
    }
    @Test
    fun getCharacters()= runTest{
//       Mockito.`when`(repository.getCharactersList()).thenReturn(CharactersRespBody(null,null))
//        val systemUnderTest = CharactersViewModel(repository)
//        systemUnderTest.getCharactersList()
//        testDispatcher.scheduler.advanceUntilIdle()
//        val result = systemUnderTest.liveDataFromTesting
//        Assert.assertEquals(0,result)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}