package io.github.alirzaev.currencies

import android.app.AlertDialog
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import io.github.alirzaev.currencies.currencies.CurrenciesViewModel
import io.github.alirzaev.currencies.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val model: CurrenciesViewModel by viewModels()

    private lateinit var bindingClass: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingClass = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bindingClass.root)

        val navController = findNavController(R.id.nav_host_fragment_content_main)
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        bindingClass.toolbar.setupWithNavController(navController, appBarConfiguration)
        bindingClass.toolbar.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.converter_menu_item ->
                    model.currencies.value?.let {
                        navController.navigate(R.id.nav_converter)
                    }
                R.id.about_menu_item ->
                    AlertDialog.Builder(this).setTitle(R.string.about_app)
                        .setMessage(R.string.about_app_description)
                        .setPositiveButton(android.R.string.ok) { _, _ -> ; }
                        .create()
                        .show()
            }

            true
        }
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.nav_converter -> bindingClass.toolbar.menu.clear()
                R.id.nav_currencies ->
                    bindingClass.toolbar.inflateMenu(R.menu.main_menu)
            }
        }
    }
}