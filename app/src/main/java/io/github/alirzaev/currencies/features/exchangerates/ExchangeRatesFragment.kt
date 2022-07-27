package io.github.alirzaev.currencies.features.exchangerates

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import io.github.alirzaev.currencies.MainViewModel
import io.github.alirzaev.currencies.R
import io.github.alirzaev.currencies.databinding.FragmentExchangeRatesBinding

@AndroidEntryPoint
class ExchangeRatesFragment : Fragment() {
    private val model: MainViewModel by activityViewModels()

    private var _bindingClass: FragmentExchangeRatesBinding? = null

    private val bindingClass get() = _bindingClass!!

    private val adapter = ExchangeRatesListAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _bindingClass = FragmentExchangeRatesBinding.inflate(inflater, container, false)
        return _bindingClass?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(bindingClass) {
            refreshLayout.setOnRefreshListener {
                model.fetchExchangeRates(true)
            }

            exchangeRatesListView.addItemDecoration(
                DividerItemDecoration(
                    context,
                    DividerItemDecoration.VERTICAL
                )
            )
            exchangeRatesListView.adapter = adapter
            exchangeRatesListView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    if (dy > 0) {
                        currenciesConverterFab.hide()
                    } else if (dy < 0) {
                        currenciesConverterFab.show()
                    }
                }
            })

            currenciesConverterFab.setOnClickListener {
                val state = model.uiState.value ?: return@setOnClickListener

                if (state.isLoading) {
                    Toast.makeText(
                        context,
                        R.string.please_wait_data_is_being_loaded,
                        Toast.LENGTH_SHORT
                    ).show()
                } else if (state.currencies.isEmpty()) {
                    Toast.makeText(
                        context,
                        R.string.exchange_rates_are_not_loaded,
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    activity
                        ?.findNavController(R.id.nav_host_fragment_content_main)
                        ?.navigate(R.id.nav_converter)
                }
            }

            model.uiState.observe(viewLifecycleOwner) { uiState ->
                adapter.setItems(uiState.currencies)
            }

            model.uiState.observe(viewLifecycleOwner) { uiState ->
                refreshLayout.isRefreshing = uiState.isLoading
            }

            model.uiState.observe(viewLifecycleOwner) { uiState ->
                if (uiState.toastMessage != null && context != null) {
                    val string = requireContext().getString(uiState.toastMessage)
                    Toast.makeText(context, string, Toast.LENGTH_SHORT).show()
                    model.toastMessageShown()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _bindingClass = null
    }
}