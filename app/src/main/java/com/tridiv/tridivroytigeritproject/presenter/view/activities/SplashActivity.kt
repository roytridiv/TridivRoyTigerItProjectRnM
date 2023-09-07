package com.tridiv.tridivroytigeritproject.presenter.view.activities


import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.tridiv.tridivroytigerit.presenter.viewModel.CharactersViewModel
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
    private val viewModel by viewModels<CharactersViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.logo.startAnimation(AnimationUtils.loadAnimation(this, R.anim.pulse))
        lifecycleScope.launch(Dispatchers.IO) {
            delay(5000L)
            withContext(Dispatchers.Main) {
                if (!isNetworkAvailable()) {
                    binding.logo.startAnimation(
                        AnimationUtils.loadAnimation(
                            this@SplashActivity,
                            R.anim.pulse
                        )
                    )
                    viewModel.observeDataInputInDB()
                    viewModel.charactersDataListResponse.observe(this@SplashActivity) {
                        if (it.isNotEmpty()) {
                            startActivity(
                                Intent(
                                    this@SplashActivity,
                                    CharactersListActivity::class.java
                                )
                            )
                        } else {
                            AlertDialog.Builder(this@SplashActivity)
                                .setTitle("No Internet!")
                                .setMessage("Please check your internet connection and try again.")
                                .setPositiveButton("OK") { _: DialogInterface?, i: Int ->
                                    finishAffinity()
                                }
                                .create()
                                .show()
                        }
                    }
                } else {
                    viewModel.getCharactersList()
                    viewModel.charactersListFromServerResponse.observe(this@SplashActivity) {
                        if (it.results?.isNotEmpty() == true) {
                            viewModel.observeDataInputInDB()
                            viewModel.charactersDataListResponse.observe(this@SplashActivity) {
                                if (it.isNotEmpty()) {
                                    startActivity(
                                        Intent(
                                            this@SplashActivity,
                                            CharactersListActivity::class.java
                                        )
                                    )
                                } else {
                                    AlertDialog.Builder(this@SplashActivity)
                                        .setTitle("Alert!")
                                        .setMessage("Character list unavailable, Please try again later.")
                                        .setPositiveButton("OK") { _: DialogInterface?, i: Int ->
                                            finishAffinity()
                                        }
                                        .create()
                                        .show()
                                }
                            }
                        } else {
                            startActivity(
                                Intent(
                                    this@SplashActivity,
                                    CharactersListActivity::class.java
                                )
                            )
                        }
                    }
                    viewModel.charactersListFromServerErrorResponse.observe(this@SplashActivity) {
                        Toast.makeText(this@SplashActivity, it, Toast.LENGTH_SHORT).show()
                        viewModel.observeDataInputInDB()
                        viewModel.charactersDataListResponse.observe(this@SplashActivity) {
                            if (it.isNotEmpty()) {
                                startActivity(
                                    Intent(
                                        this@SplashActivity,
                                        CharactersListActivity::class.java
                                    )
                                )
                            } else {
                                AlertDialog.Builder(this@SplashActivity)
                                    .setTitle("Alert!")
                                    .setMessage("Character list unavailable, Please try again later.")
                                    .setPositiveButton("OK") { _: DialogInterface?, i: Int ->
                                        finishAffinity()
                                    }
                                    .create()
                                    .show()
                            }
                        }
                    }
                }
            }
        }
    }
}