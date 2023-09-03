package com.tridiv.tridivroytigerit.presenter.viewModel

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tridivtigritproject.data.model.CharacterDaoItem
import com.tridiv.tridivroytigeritproject.data.model.networkPojo.CharactersListResp.Result
import com.tridiv.tridivroytigeritproject.data.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharactersListViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {


    fun getCharactersList() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = repository.getCharactersList()
                insertDataInDao(response?.results)
                println(response?.results)
            } catch (e: Exception) {

            }
        }
    }


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

}