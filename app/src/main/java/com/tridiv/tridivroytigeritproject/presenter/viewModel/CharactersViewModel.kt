package com.tridiv.tridivroytigerit.presenter.viewModel

import android.util.Log
import androidx.lifecycle.*
import com.example.tridivtigritproject.data.model.CharacterDaoItem
import com.tridiv.tridivroytigeritproject.data.domain.common.ResultData
import com.tridiv.tridivroytigeritproject.data.model.networkPojo.CharactersListResp.CharactersRespBody
import com.tridiv.tridivroytigeritproject.data.model.networkPojo.CharactersListResp.Result
import com.tridiv.tridivroytigeritproject.data.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharactersViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

     fun insertDataInDao(charactersList: List<Result?>?) {
        repository.clearDbNew()
        if (charactersList != null) {
            for (item in charactersList) {
                repository.insertCharacterDataInDb(
                    CharacterDaoItem(
                        item?.id ?: -1,
                        item?.name ?: "",
                        item?.status ?: "",
                        item?.species ?: "",
                        item?.type ?: "-",
                        item?.gender ?: "",
                        item?.origin?.name ?: "",
                        item?.location?.name ?: "",
                        item?.image ?: ""
                    )
                )
            }
        }
    }

    var charactersDataListResponse = MutableLiveData<List<CharacterDaoItem>>()
    fun observeDataInputInDB() {
            charactersDataListResponse.postValue(repository.getAllCharactersDataFromDB())
    }

    var characterDetailsDataResponse = MutableLiveData<CharacterDaoItem>()
    fun observeCharacterDataFromDb(characterId: Int) {
            characterDetailsDataResponse.postValue(repository.getCharacterDetailsFromDB(characterId))
    }


    var charactersListFromServerResponse = MutableLiveData<CharactersRespBody>()
    var charactersListFromServerErrorResponse = MutableLiveData<String>()
    fun getCharactersList() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                when (val response =
                    repository.getCharactersList()) {
                    is ResultData.Success -> {
                        insertDataInDao(response.data.body()?.results)
                        charactersListFromServerResponse.postValue(response.data.body())
                    }
                    is ResultData.Error -> {
                        Log.d("ERROR", response.exception.message?:"")
                        charactersListFromServerErrorResponse.postValue(response.exception.message?:"")
                    }
                    else -> {

                    }
                }
            } catch (e: Exception) {
                Log.d("ERROR", e.message?:"")
                charactersListFromServerErrorResponse.postValue(e.message?:"")
            }
        }
    }

}