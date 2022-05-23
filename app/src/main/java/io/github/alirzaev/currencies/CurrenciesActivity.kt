package io.github.alirzaev.currencies

import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import io.github.alirzaev.currencies.databinding.ActivityCurrenciesBinding

class CurrenciesActivity : AppCompatActivity() {
    private lateinit var bindingClass: ActivityCurrenciesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingClass = ActivityCurrenciesBinding.inflate(layoutInflater)
        setContentView(bindingClass.root)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }
}