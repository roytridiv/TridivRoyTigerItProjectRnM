package com.tridiv.tridivroytigerit.presenter.viewModel

import android.util.Log
import androidx.lifecycle.*
import com.example.tridivtigritproject.data.model.CharacterDaoItem
import com.tridiv.tridivroytigeritproject.data.domain.ResultData
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

    private fun insertDataInDao(charactersList: List<Result?>?) {
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
    fun observeDataInputInDB(context: LifecycleOwner) {
        repository.getAllCharactersDataFromDB().observe(context) {
            charactersDataListResponse.postValue(it)
        }
    }

    var characterDetailsDataResponse = MutableLiveData<CharacterDaoItem>()
    fun observeCharacterDataFromDb(characterId: Int, context: LifecycleOwner) {
        repository.getCharacterDetailsFromDB(characterId).observe(context) {
            characterDetailsDataResponse.postValue(it)
        }
    }


    fun getCharactersList() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                when (val response =
                    repository.getCharListTemp()) {
                    is ResultData.Success -> {
                        insertDataInDao(response.data.body()?.results)
                    }
                    is ResultData.Error -> {
                        Log.d("ERROR", response.exception.message?:"")
                    }
                    else -> {

                    }
                }
            } catch (e: Exception) {
                Log.d("ERROR", e.message?:"")
            }
        }
    }

}