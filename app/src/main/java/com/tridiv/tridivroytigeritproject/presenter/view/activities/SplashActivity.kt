package com.tridiv.tridivroytigeritproject.presenter.view.activities


import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.animation.AnimationUtils
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.tridiv.tridivroytigerit.presenter.viewModel.CharactersListViewModel
import com.tridiv.tridivroytigeritproject.R
import com.tridiv.tridivroytigeritproject.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class SplashActivity : BaseActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val viewModel by viewModels<CharactersListViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        if(!isNetworkAvailable()){
            binding.logo.startAnimation(AnimationUtils.loadAnimation(this, R.anim.pulse))
            AlertDialog.Builder(this)
                .setTitle("No Internet!")
                .setMessage("Please check your internet connection and try again.")
                .setPositiveButton("OK") { _: DialogInterface?, i: Int ->
                    finishAffinity()
                }
                .create()
                .show()
        }else{
            binding.logo.startAnimation(AnimationUtils.loadAnimation(this, R.anim.pulse))
            lifecycleScope.launch(Dispatchers.IO) {
                viewModel.getCharactersList()
                delay(1000L)
                withContext(Dispatchers.Main) {
                    startActivity(Intent(this@SplashActivity, CharactersListActivity::class.java))
                }
            }
        }
    }


}