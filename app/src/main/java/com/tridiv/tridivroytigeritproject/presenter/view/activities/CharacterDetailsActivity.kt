package com.tridiv.tridivroytigeritproject.presenter.view.activities

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.tridiv.tridivroytigerit.presenter.viewModel.CharactersListViewModel
import com.tridiv.tridivroytigeritproject.R
import com.tridiv.tridivroytigeritproject.databinding.ActivityCharacterDetailsBinding
import com.tridiv.tridivroytigeritproject.databinding.ActivityCharactersListBinding

class CharacterDetailsActivity : AppCompatActivity() {

    private val binding by lazy { ActivityCharacterDetailsBinding.inflate(layoutInflater) }
    private val viewModel by viewModels<CharactersListViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val characterId = intent.extras?.getInt("character_id") ?: -1

        viewModel.observeCharacterDataFromDb(characterId,this)
        viewModel.characterDetailsDataResponse.observe(this) {
            binding.nameTv.text = it.name
            binding.statusValTv.text = it.status
            binding.originValTv.text = it.origin
            binding.genderValTv.text = it.gender
            binding.locationValTv.text = it.location
            when (it.status) {
                "Alive" -> binding.statusValTv.setTextColor(Color.parseColor("#38AF6D"))
                "Dead" -> binding.statusValTv.setTextColor(Color.parseColor("#E9533A"))
                else -> binding.statusValTv.setTextColor(Color.parseColor("#FEBF42"))
            }
        }

    }
}