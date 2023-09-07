package com.tridiv.tridivroytigeritproject.presenter.viewModel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.tridivtigritproject.data.model.CharacterDaoItem
//import com.nhaarman.mockitokotlin2.mock
import com.tridiv.tridivroytigerit.presenter.viewModel.CharactersViewModel
import com.tridiv.tridivroytigeritproject.data.domain.common.ResultData
import com.tridiv.tridivroytigeritproject.data.model.networkPojo.CharactersListResp.CharactersRespBody
import com.tridiv.tridivroytigeritproject.data.model.networkPojo.CharactersListResp.Result
import com.tridiv.tridivroytigeritproject.data.repository.Repository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking

import org.junit.*
import org.mockito.Mockito.*
import org.mockito.kotlin.mock

import retrofit2.Response

@OptIn(ExperimentalCoroutinesApi::class)
class CharactersViewModelTest {

    lateinit var repository: Repository
    lateinit var observer: Observer<CharactersRespBody>
    lateinit var daoListObserver: Observer<List<CharacterDaoItem>>
    lateinit var daoItemObserver: Observer<CharacterDaoItem>
    private lateinit var viewModel: CharactersViewModel

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        repository = mock(Repository::class.java)
        observer = mock<Observer<CharactersRespBody>>()
        daoListObserver = mock<Observer<List<CharacterDaoItem>>>()
        daoItemObserver = mock<Observer<CharacterDaoItem>>()
        viewModel = CharactersViewModel(repository)
    }


    @Test
    fun `test fetching character list from api success`() = runBlocking {
        val mockResponse = sharedDataInformationSetup()
        `when`(repository.getCharactersList()).thenReturn(mockResponse)

        viewModel.getCharactersList()
        viewModel.charactersListFromServerResponse.observeForever(observer)
        verify(observer).onChanged(any())
    }



    @Test
    fun `test observeDataInputInDB`() = runBlocking {

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
        `when`(repository.getAllCharactersDataFromDB()).thenReturn(characterList)

        viewModel.observeDataInputInDB()

        viewModel.charactersDataListResponse.observeForever(daoListObserver)
        verify(daoListObserver).onChanged(characterList)
    }

    @Test
    fun `test observeCharacterDataFromDb`() = runBlocking {

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
        `when`(repository.getCharacterDetailsFromDB(1)).thenReturn(characterDaoItem)
        viewModel.observeCharacterDataFromDb(1)

        viewModel.characterDetailsDataResponse.observeForever(daoItemObserver)
        verify(daoItemObserver).onChanged(characterDaoItem)
    }


    @Test
    fun `test insertDataInDao`() = runBlocking {

        val mockResponse = sharedDataInformationSetup()
        `when`(repository.getCharactersList()).thenReturn(mockResponse)


        val charactersList = listOf(
            Result("", null, "Status1", 1, "Type1", null, null, null, null, null, "", ""),
            Result("", null, "Status1", 1, "Type1", null, null, null, null, null, "", ""),
        )


        doNothing().`when`(repository).clearDbNew()


        viewModel.insertDataInDao(charactersList)


        verify(repository).clearDbNew()


        val item = charactersList[0]
        verify(repository, times(2)).insertCharacterDataInDb(
            CharacterDaoItem(
                item.id ?: -1,
                item.name ?: "",
                item.status ?: "",
                item.species ?: "",
                item.type ?: "-",
                item.gender ?: "",
                item.origin?.name ?: "",
                item.location?.name ?: "",
                item.image ?: ""
            )
        )

    }


    private fun sharedDataInformationSetup(): ResultData.Success<Response<CharactersRespBody>> {
        return ResultData.Success(Response.success(CharactersRespBody(null, null)))
    }

    @After
    fun tearDown() {
        //  Dispatchers.resetMain()
    }

}
