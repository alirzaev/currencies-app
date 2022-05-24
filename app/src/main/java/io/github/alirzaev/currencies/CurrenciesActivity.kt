package io.github.alirzaev.currencies

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import io.github.alirzaev.currencies.converter.ConverterActivity
import io.github.alirzaev.currencies.databinding.ActivityCurrenciesBinding

class CurrenciesActivity : AppCompatActivity() {
    private lateinit var model: CurrenciesViewModel

    private lateinit var bindingClass: ActivityCurrenciesBinding

    private val openConverterActivity =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingClass = ActivityCurrenciesBinding.inflate(layoutInflater)
        setContentView(bindingClass.root)
        model = ViewModelProvider(this).get(CurrenciesViewModel::class.java)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.converter_menu_item ->
                model.currencies.value?.let {
                    openConverterActivity.launch(
                        Intent(
                            this,
                            ConverterActivity::class.java
                        ).apply {
                            putExtra("CURRENCIES", ArrayList(it))
                        }
                    )
                }
        }

        return true
    }
}