package io.github.alirzaev.currencies.converter

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import io.github.alirzaev.currencies.R
import io.github.alirzaev.currencies.databinding.ActivityConverterBinding
import io.github.alirzaev.currencies.utils.dto.Currency
import io.github.alirzaev.currencies.utils.parseExpression

class ConverterActivity : AppCompatActivity() {
    lateinit var bindingClass: ActivityConverterBinding

    lateinit var currencies: Map<String, Currency>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingClass = ActivityConverterBinding.inflate(layoutInflater)
        setContentView(bindingClass.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setTitle(R.string.currency_converter)

        val data = (intent.getSerializableExtra("CURRENCIES") as ArrayList<*>).let {
            val currencies = it.filterIsInstance<Currency>()
            if (it.size != currencies.size) {
                null
            } else {
                currencies.associateBy { c -> c.charCode }
            }
        }

        if (data != null) {
            currencies = data
        } else {
            finish()
        }

        bindingClass.currencyConverterInput.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val parseResult = parseExpression(currencies, s.toString())
                if (parseResult != null) {
                    with(bindingClass) {
                        currencyInput.text =
                            getString(
                                R.string.currency_value_with_code,
                                parseResult.inputValue,
                                parseResult.inputCurrency.charCode
                            )
                        currencyOutput.text =
                            getString(
                                R.string.currency_value_with_code,
                                parseResult.outputValue,
                                parseResult.outputCurrency.charCode
                            )
                    }
                } else {
                    with(bindingClass) {
                        currencyInput.text = ""
                        currencyOutput.text = ""
                    }
                }
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
        }

        return true
    }
}