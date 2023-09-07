package com.tridiv.tridivroytigeritproject.presenter.view.activities


import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.NetworkInfo
import android.os.Build
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import com.tridiv.tridivroytigeritproject.data.domain.common.ConnectionStateMonitor
import com.tridiv.tridivroytigeritproject.data.domain.common.CustomSnackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

abstract class BaseActivity: AppCompatActivity(), ConnectionStateMonitor.OnNetworkAvailableCallbacks {

    var snackbar: CustomSnackbar? = null
    private var connectionStateMonitor: ConnectionStateMonitor? = null
    private var viewGroup: ViewGroup? = null



    fun isNetworkAvailable(): Boolean {
        try {
            val connectivityManager: ConnectivityManager =
                this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                val capabilities =
                    connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork) as NetworkCapabilities
                when {
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                        return true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                        return true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                        return true
                    }
                }
            } else {
                try {
                    val activeNetworkInfo: NetworkInfo? = connectivityManager.activeNetworkInfo
                    if (activeNetworkInfo != null && activeNetworkInfo.isConnected) {
                        return true
                    }
                } catch (exception: Exception) {
                }
            }
            return false
        } catch (e: Exception) {
            return false
        }
    }

    override fun onResume() {
        super.onResume()

        if (viewGroup == null) viewGroup = findViewById(android.R.id.content)
        if (snackbar == null) snackbar =
            CustomSnackbar.make(viewGroup ?: return, Snackbar.LENGTH_INDEFINITE)
                .setText("No internet connection.")
        if (connectionStateMonitor == null) connectionStateMonitor =
            ConnectionStateMonitor(applicationContext, this)
        connectionStateMonitor?.enable()

        // Recheck network status manually whenever activity resumes
        if (connectionStateMonitor?.hasNetworkConnection() == false) onNegative()
        else onPositive()
    }

    override fun onDestroy() {
        super.onDestroy()
        connectionStateMonitor = null
    }

    override fun onPause() {
        snackbar?.dismiss()
        snackbar = null
        connectionStateMonitor?.disable()
        connectionStateMonitor = null
        super.onPause()
    }

    override fun onPositive() {
        lifecycleScope.launch(Dispatchers.Main){
            snackbar?.dismiss()
        }
    }

    override fun onNegative() {
        lifecycleScope.launch(Dispatchers.Main){
            snackbar?.setText("No internet connection.")?.show()
        }
    }
}