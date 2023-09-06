package com.tridiv.tridivroytigeritproject.presenter.viewModel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.tridivtigritproject.data.model.CharacterDaoItem
import com.tridiv.tridivroytigerit.presenter.viewModel.CharactersViewModel
import com.tridiv.tridivroytigeritproject.data.domain.ResultData
import com.tridiv.tridivroytigeritproject.data.model.networkPojo.CharactersListResp.CharactersRespBody
import com.tridiv.tridivroytigeritproject.data.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.*
import org.junit.Assert.*

import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import retrofit2.Response

@OptIn(ExperimentalCoroutinesApi::class)
class CharactersViewModelTest {


   // private val testDispatcher = StandardTestDispatcher()

    @Mock
    lateinit var repository: Repository

    @Mock
    lateinit var lifecycleOwner: LifecycleOwner

    @Mock
    lateinit var charactersObserver: Observer<List<CharacterDaoItem>>

    @Mock
    lateinit var characterDetailsObserver: Observer<CharacterDaoItem>

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var viewModel: CharactersViewModel


    @Before
    fun setUp() {
        System.setProperty("org.mockito.mockmaker", "android")
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(testDispatcher)

        viewModel = CharactersViewModel(repository)
    }


    @Test
    fun `test fetching character list from api success`() = runTest {
        val mockResponse = ResultData.Success(Response.success(CharactersRespBody(null,null)))
        Mockito.`when`(repository.getCharListTemp()).thenReturn(mockResponse)

        viewModel.getCharactersList()

        assert(viewModel.charactersDataListResponse.value != null)
        assert(viewModel.charactersDataListResponse.value?.isEmpty() == false)
    }



    @Test
    fun `test data observance from with id Room DB`() = runTest {
        val characterId = 1

        val mockCharacterData = CharacterDaoItem(characterId, "", "","","","","","","")
        val characterLiveData = MutableLiveData<CharacterDaoItem>()
        characterLiveData.value = mockCharacterData

        Mockito.`when`(repository.getCharacterDetailsFromDB(characterId)).thenReturn(characterLiveData)

        viewModel.observeCharacterDataFromDb(characterId, lifecycleOwner)
        viewModel.characterDetailsDataResponse.observe(lifecycleOwner, characterDetailsObserver)


        assert(viewModel.characterDetailsDataResponse.value == mockCharacterData)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}