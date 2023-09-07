package com.tridiv.tridivroytigeritproject.data

import com.example.tridivtigritproject.data.model.CharacterDaoItem
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.tridiv.tridivroytigerit.data.db.CharactersDataDao
import com.tridiv.tridivroytigeritproject.data.domain.common.ResultData
import com.tridiv.tridivroytigeritproject.data.network.RestApiService
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockWebServer
import org.junit.*
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.mockito.Mockito.*
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

internal class RepositoryImplementationTest {

    private lateinit var mockWebServer: MockWebServer

    private lateinit var repoImpl: RepositoryImplementation
    val charDao = mock(CharactersDataDao::class.java)

    @Before
    fun setUp() {
        mockWebServer = MockWebServer()

        val apiService = Retrofit.Builder()
            .baseUrl(mockWebServer.url("https://rickandmortyapi.com/api/"))
            .addConverterFactory(
                MoshiConverterFactory.create(
                    Moshi
                        .Builder()
                        .addLast(KotlinJsonAdapterFactory())
                        .build()
                )
            )
            .build()
            .create(RestApiService::class.java)
        repoImpl = RepositoryImplementation(apiService, charDao)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun testCharacterListApi() = runBlocking {
        val result = repoImpl.getCharactersList()
        assert(result is ResultData.Success)

        val resp = (result as ResultData.Success)
        assertTrue(resp.data.isSuccessful)
    }

    @Test
    fun `test data insertion in dao`() {
        val characterDaoItem = CharacterDaoItem(
            1,
            "Rick",
            "Alive",
            "Human",
            "none",
            "Male",
            "Earth",
            "Unknown",
            "Image"
        )

        repoImpl.insertCharacterDataInDb(characterDaoItem)
        verify(charDao).saveCharactersInDB(characterDaoItem)

    }


    @Test
    fun `test character list data retrieval from dao`() {
        val characterList = listOf(
            CharacterDaoItem(
                1,
                "Rick",
                "Alive",
                "Human",
                "none",
                "Male",
                "Earth",
                "Unknown",
                "Image"
            ),
            CharacterDaoItem(
                2,
                "Rick",
                "Alive",
                "Human",
                "none",
                "Male",
                "Earth",
                "Unknown",
                "Image"
            )
        )

        `when`(repoImpl.getAllCharactersDataFromDB()).thenReturn(characterList)

        val result  = repoImpl.getAllCharactersDataFromDB()
        assertEquals(characterList,result)

    }


    @Test
    fun `test character details data retrieval from dao`() {

       val  characterDaoItem = CharacterDaoItem(
            1,
            "Rick",
            "Alive",
            "Human",
            "none",
            "Male",
            "Earth",
            "Unknown",
            "Image"
        )

        `when`(repoImpl.getCharacterDetailsFromDB(1)).thenReturn(characterDaoItem)

        val result  = repoImpl.getCharacterDetailsFromDB(1)
        assertEquals(characterDaoItem,result)

    }

    @Test
    fun `test clear DB`() {
        repoImpl.clearDbNew()
        verify(charDao).clearTable()
    }


}