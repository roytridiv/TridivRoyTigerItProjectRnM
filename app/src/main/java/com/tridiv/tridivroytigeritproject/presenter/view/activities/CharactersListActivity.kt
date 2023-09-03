package com.tridiv.tridivroytigeritproject.presenter.view.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tridivtigritproject.data.model.CharacterDaoItem
import com.tridiv.tridivroytigeritproject.presenter.view.adapters.CharacterListAdapter
import com.tridiv.tridivroytigerit.presenter.viewModel.CharactersListViewModel
import com.tridiv.tridivroytigeritproject.databinding.ActivityCharactersListBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@AndroidEntryPoint
class CharactersListActivity : AppCompatActivity(), CharacterListAdapter.OnItemClickListener  {
    private val binding by lazy { ActivityCharactersListBinding.inflate(layoutInflater) }
    private val viewModel by viewModels<CharactersListViewModel>()

    private var charactersListResp: MutableList<CharacterDaoItem> = mutableListOf()
    private val characterListAdapter by lazy {
        CharacterListAdapter(
            charactersListResp,
            this
        )
    }
    private val linearLayoutManager by lazy { LinearLayoutManager(this) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initUi()
        viewModel.observeDataInputInDB(this)
        GlobalScope.launch(Dispatchers.IO) {
            delay(1000L)
        }
        registerObservers()


    }

    private fun initUi() {
        binding.characterListRv.layoutManager = linearLayoutManager
        binding.characterListRv.adapter = characterListAdapter
//        binding.characterListRv.apply {
//            layoutManager = linearLayoutManager
//            adapter = characterListAdapter
//        }
    }

    private fun registerObservers() {
        viewModel.charactersDataListResponse.observe(this) {
            if (charactersListResp.isNotEmpty()) charactersListResp.clear()
            charactersListResp.addAll(it.toMutableList())
            for(item in charactersListResp) println(" -------item name-------"+ item.name)
            println("---------------------------length"+charactersListResp.size)
            characterListAdapter.notifyDataSetChanged()
        }
    }



    override fun onItemClick(position: Int, tvData: CharacterDaoItem) {
        TODO("Not yet implemented")
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finishAffinity()
    }
}