package io.github.alirzaev.currencies

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import dagger.hilt.android.AndroidEntryPoint
import io.github.alirzaev.currencies.databinding.ActivityMainBinding
import io.github.alirzaev.currencies.features.about.AboutAppDialogFragment

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var bindingClass: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        bindingClass = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bindingClass.root)

        val navController = findNavController(R.id.nav_host_fragment_content_main)
        val appBarConfiguration = AppBarConfiguration(navController.graph)

        with(bindingClass) {
            toolbar.setupWithNavController(navController, appBarConfiguration)
            toolbar.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.about_menu_item ->
                        AboutAppDialogFragment().show(supportFragmentManager, null)
                }

                true
            }
            toolbar.inflateMenu(R.menu.main_menu)
        }
    }
}