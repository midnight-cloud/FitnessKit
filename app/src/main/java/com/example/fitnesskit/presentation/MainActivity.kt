package com.example.fitnesskit.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import com.example.fitnesskit.R
import com.example.fitnesskit.databinding.ActivityMainBinding
import com.example.fitnesskit.presentation.fragments.MainFragment

class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        appComponent.inject(this)

        setTitle(resources.getString(R.string.raspisanie))

        init(savedInstanceState)

    }

    private fun init(savedInstanceState: Bundle?) {
        if (savedInstanceState != null) {
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                replace(R.id.main_fragment, MainFragment())
            }
        } else {
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                add(R.id.main_fragment, MainFragment())
            }
        }
    }
}