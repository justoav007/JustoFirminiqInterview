package com.example.justofirminiqinterview

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.util.DisplayMetrics
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.example.justofirminiqinterview.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainActivityViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        viewModel = MainActivityViewModel()
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        if (isTablet()) {
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR
        }

        viewModel.showDialog.observe(this, Observer { email ->
            if (!email.isNullOrEmpty()) {
                AlertDialog.Builder(this)
                    .setTitle("Welcome")
                    .setMessage(" $email")
                    .setPositiveButton("OK", null)
                    .show()
            }
        })
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putString("email", viewModel.email.value)
        outState.putString("password", viewModel.password.value)
        outState.putString("confirmPassword", viewModel.confirmPassword.value)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        viewModel.email.value = savedInstanceState.getString("email")
        viewModel.password.value = savedInstanceState.getString("password")
        viewModel.confirmPassword.value = savedInstanceState.getString("confirmPassword")
    }

    private fun getScreenWidth(): Float {
        val metrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(metrics)
        return metrics.widthPixels.coerceAtMost(metrics.heightPixels) / metrics.density
    }

    private fun isTablet(): Boolean {
        return getScreenWidth() >= 600
    }
}