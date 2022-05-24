package io.github.alirzaev.currencies.converter

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import io.github.alirzaev.currencies.R
import io.github.alirzaev.currencies.databinding.ActivityConverterBinding
import io.github.alirzaev.currencies.utils.dto.Currency

class ConverterActivity : AppCompatActivity() {
    private lateinit var bindingClass: ActivityConverterBinding

    private lateinit var model: ConverterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingClass = ActivityConverterBinding.inflate(layoutInflater)
        setContentView(bindingClass.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setTitle(R.string.currency_converter)

        model = ViewModelProvider(this).get(ConverterViewModel::class.java)

        val data = (intent.getSerializableExtra("CURRENCIES") as ArrayList<*>).let {
            val currencies = it.filterIsInstance<Currency>()
            if (it.size != currencies.size) {
                null
            } else {
                (currencies + arrayListOf(
                    Currency(
                        "",
                        "643",
                        "RUB",
                        1,
                        "Российский рубль",
                        1.0,
                        1.0
                    )
                )).associateBy { c -> c.charCode }
            }
        }

        if (data != null) {
            model.setCurrencies(data)

            supportFragmentManager.beginTransaction().replace(
                R.id.fragment_container_view,
                ConverterFragment()
            ).commit()
        } else {
            finish()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
        }

        return true
    }
}