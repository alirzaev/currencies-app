package io.github.alirzaev.currencies.features.currencychooser

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import io.github.alirzaev.currencies.R
import io.github.alirzaev.currencies.databinding.FragmentCurrencyChooserBinding
import io.github.alirzaev.currencies.features.converter.ConverterViewModel

class CurrencyChooserFragment : Fragment() {
    private val converterViewModel: ConverterViewModel by activityViewModels()

    private val currencyChooserViewModel: CurrencyChooserViewModel by activityViewModels()

    private var _bindingClass: FragmentCurrencyChooserBinding? = null

    private val bindingClass get() = _bindingClass!!

    private lateinit var target: String

    private val adapter = CurrenciesListAdapter {
        when (target) {
            TARGET_INPUT -> converterViewModel.setInputCurrency(it)
            TARGET_OUTPUT -> converterViewModel.setOutputCurrency(it)
            else -> {}
        }

        activity
            ?.findNavController(R.id.nav_host_fragment_content_main)
            ?.popBackStack()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            target = it.getString(TARGET_KEY) ?: TARGET_INPUT
        }

        if (savedInstanceState == null) {
            currencyChooserViewModel.fetchCurrencies()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _bindingClass = FragmentCurrencyChooserBinding.inflate(inflater, container, false)
        return _bindingClass?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(bindingClass) {
            currenciesList.adapter = adapter
            currenciesList.addItemDecoration(
                DividerItemDecoration(
                    context,
                    DividerItemDecoration.VERTICAL
                )
            )
        }

        currencyChooserViewModel.currencies.observe(viewLifecycleOwner) { currencies ->
            if (currencies.isNotEmpty()) {
                adapter.setItems(currencies)
            } else {
                activity
                    ?.findNavController(R.id.nav_host_fragment_content_main)
                    ?.popBackStack()
            }
        }
    }

    override fun onDetach() {
        super.onDetach()
        _bindingClass = null
    }

    companion object {
        private const val TARGET_KEY = "TARGET_KEY"

        const val TARGET_INPUT = "TARGET_INPUT"

        const val TARGET_OUTPUT = "TARGET_OUTPUT"

        fun createArgsBundle(target: String): Bundle {
            if (target != TARGET_INPUT && target != TARGET_OUTPUT) {
                throw IllegalArgumentException("Invalid target value: $target")
            }

            return Bundle().apply {
                putString(TARGET_KEY, target)
            }
        }
    }
}