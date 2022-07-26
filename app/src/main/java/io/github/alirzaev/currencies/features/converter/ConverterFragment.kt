package io.github.alirzaev.currencies.features.converter

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import dagger.hilt.android.AndroidEntryPoint
import io.github.alirzaev.currencies.MainViewModel
import io.github.alirzaev.currencies.R
import io.github.alirzaev.currencies.databinding.FragmentConverterBinding
import io.github.alirzaev.currencies.data.source.remote.dto.ExchangeRate
import io.github.alirzaev.currencies.utils.parseExpression

@AndroidEntryPoint
class ConverterFragment : Fragment() {
    private val model: MainViewModel by activityViewModels()

    private var _bindingClass: FragmentConverterBinding? = null

    private val bindingClass get() = _bindingClass!!

    private lateinit var exchangeRates: Map<String, ExchangeRate>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        exchangeRates = (model.uiState.value!!.exchangeRates + arrayListOf(
            ExchangeRate(
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _bindingClass = FragmentConverterBinding.inflate(inflater, container, false)
        return _bindingClass?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bindingClass.currencyConverterInput.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val parseResult = parseExpression(exchangeRates, s.toString())
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

    override fun onDestroy() {
        super.onDestroy()
        _bindingClass = null
    }
}