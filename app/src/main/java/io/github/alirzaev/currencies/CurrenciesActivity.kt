package io.github.alirzaev.currencies

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import io.github.alirzaev.currencies.converter.ConverterActivity
import io.github.alirzaev.currencies.databinding.ActivityCurrenciesBinding

class CurrenciesActivity : AppCompatActivity() {
    private val model: CurrenciesViewModel by viewModels()

    private lateinit var bindingClass: ActivityCurrenciesBinding

    private val openConverterActivity =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingClass = ActivityCurrenciesBinding.inflate(layoutInflater)
        setContentView(bindingClass.root)
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
            R.id.about_menu_item ->
                AlertDialog.Builder(this).setTitle(R.string.about_app)
                    .setMessage(R.string.about_app_description)
                    .setPositiveButton(android.R.string.ok) { _, _ -> ; }
                    .create()
                    .show()
        }

        return true
    }
}