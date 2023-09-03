package com.tridiv.tridivroytigeritproject.data

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import com.example.tridivtigritproject.data.model.CharacterDaoItem
import com.example.tridivtigritproject.data.model.CharacterDetailsReqBody
import com.tridiv.tridivroytigeritproject.data.model.networkPojo.CharactersListResp.CharactersRespBody
import com.tridiv.tridivroytigeritproject.data.model.networkPojo.CharaterDetailsResp.CharacterDetailsRespBody
import com.tridiv.tridivroytigerit.data.db.CharactersDataDao
import com.tridiv.tridivroytigeritproject.data.network.RestApiService
import com.tridiv.tridivroytigeritproject.data.repository.Repository

class RepositoryImplementation(
    private val apiService: RestApiService,
    private val characterDao: CharactersDataDao,
    val context: Context
) : Repository {

    override suspend fun getCharactersList(): CharactersRespBody? {
        return try {
            apiService.getCharactersList().body()
        } catch (e: Exception) {
            Log.e("REPO_ERROR", e.message ?: "")
            null
        }
    }

    override suspend fun getCharacterDetails(id: Int): CharacterDetailsRespBody? {
        return try {
            apiService.getCharacterDetails(CharacterDetailsReqBody(id)).body()
        } catch (e: Exception) {
            Log.e("REPO_ERROR", e.message ?: "")
            null
        }
    }

    override fun getAllCharactersData(): LiveData<List<CharacterDaoItem>> {
        return characterDao.getAllTelevisionData()
    }

    override fun insertCharacterDataInDb(item: CharacterDaoItem) {
        characterDao.saveCharactersInDB(item)
    }

    override fun clearDbNew() {
        characterDao.clearTable()
    }
}