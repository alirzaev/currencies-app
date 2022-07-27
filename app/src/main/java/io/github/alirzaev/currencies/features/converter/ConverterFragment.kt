package io.github.alirzaev.currencies.features.converter

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import dagger.hilt.android.AndroidEntryPoint
import io.github.alirzaev.currencies.R
import io.github.alirzaev.currencies.databinding.FragmentConverterBinding
import io.github.alirzaev.currencies.features.currencychooser.CurrencyChooserFragment

@AndroidEntryPoint
class ConverterFragment : Fragment() {
    private val model: ConverterViewModel by activityViewModels()

    private var _bindingClass: FragmentConverterBinding? = null

    private val bindingClass get() = _bindingClass!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState == null) {
            model.resetUiState()
        }
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

        model.uiState.observe(viewLifecycleOwner) {
            with(bindingClass) {
                currencyInputButton.text =
                    it.inputCurrency?.charCode ?: getString(R.string.currency_button_placeholder)
                currencyOutputButton.text =
                    it.outputCurrency?.charCode ?: getString(R.string.currency_button_placeholder)
                currencyOutput.setText(
                    if (it.output != null) getString(
                        R.string.currency_value,
                        it.output
                    ) else ""
                )
            }
        }

        with(bindingClass) {
            currencyInputButton.setOnClickListener {
                activity
                    ?.findNavController(R.id.nav_host_fragment_content_main)
                    ?.navigate(
                        R.id.nav_currency_chooser,
                        CurrencyChooserFragment.createArgsBundle(CurrencyChooserFragment.TARGET_INPUT)
                    )
            }

            currencyOutputButton.setOnClickListener {
                activity
                    ?.findNavController(R.id.nav_host_fragment_content_main)
                    ?.navigate(
                        R.id.nav_currency_chooser,
                        CurrencyChooserFragment.createArgsBundle(CurrencyChooserFragment.TARGET_OUTPUT)
                    )
            }

            currencyInput.doAfterTextChanged {
                val value = it.toString().toDoubleOrNull()
                model.performConversion(value)
            }

            copyValueToClipboard.setOnClickListener {
                val clipboard =
                    requireContext().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                val clip = ClipData.newPlainText(
                    requireContext().getString(R.string.converted_value_clip_label),
                    bindingClass.currencyOutput.text.toString()
                )
                clipboard.setPrimaryClip(clip)

                Toast.makeText(
                    context,
                    R.string.converted_value_copied_to_clipboard,
                    Toast.LENGTH_SHORT
                )
                    .show()
            }

            exchangeCurrenciesButton.setOnClickListener {
                model.exchangeCurrencies()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _bindingClass = null
    }
}