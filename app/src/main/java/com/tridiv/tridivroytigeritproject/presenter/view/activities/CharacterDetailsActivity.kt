package com.tridiv.tridivroytigeritproject.presenter.view.activities

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View.GONE
import androidx.activity.viewModels
import com.squareup.picasso.Picasso
import com.tridiv.tridivroytigerit.presenter.viewModel.CharactersViewModel
import com.tridiv.tridivroytigeritproject.databinding.ActivityCharacterDetailsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CharacterDetailsActivity : BaseActivity(){

    private val binding by lazy { ActivityCharacterDetailsBinding.inflate(layoutInflater) }
    private val viewModel by viewModels<CharactersViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val characterId = intent.extras?.getInt("character_id") ?: -1

        viewModel.observeCharacterDataFromDb(characterId)
        viewModel.characterDetailsDataResponse.observe(this) {
            if(it.image.isNotEmpty()) Picasso.get().load(it.image)
                .into(binding.characterImage)
            binding.nameTv.text = it.name
            binding.statusValTv.text = it.status
            binding.originValTv.text = it.origin
            binding.genderValTv.text = it.gender
            binding.locationValTv.text = it.location
            binding.speciesValTv.text = it.species
            when (it.status) {
                "Alive" -> binding.statusValTv.setTextColor(Color.parseColor("#38AF6D"))
                "Dead" -> binding.statusValTv.setTextColor(Color.parseColor("#E9533A"))
                else -> binding.statusValTv.setTextColor(Color.parseColor("#FF8C00"))
            }
            binding.progressBar.visibility = GONE
        }

    }
}