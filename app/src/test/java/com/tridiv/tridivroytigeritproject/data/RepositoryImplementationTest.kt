package com.tridiv.tridivroytigeritproject.data

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.tridiv.tridivroytigerit.data.db.CharactersDataDao
import com.tridiv.tridivroytigeritproject.data.network.RestApiService
import org.junit.*
import org.mockito.Mockito.mock

internal class RepositoryImplementationTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var repoImpl: RepositoryImplementation

    // Mocked dependencies



    @Before
    fun setUp() {
      //  repoImpl= mock(RepositoryImplementation:class.java)
    }

    @After
    fun tearDown() {
    }



}