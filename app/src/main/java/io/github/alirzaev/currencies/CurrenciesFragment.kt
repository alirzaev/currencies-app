package io.github.alirzaev.currencies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import io.github.alirzaev.currencies.databinding.FragmentCurrenciesBinding

class CurrenciesFragment : Fragment() {
    private lateinit var model: CurrenciesViewModel

    private var _bindingClass: FragmentCurrenciesBinding? = null

    private val bindingClass get() = _bindingClass!!

    private val adapter = CurrenciesListAdapter(emptyList())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        model = ViewModelProvider(requireActivity()).get(CurrenciesViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _bindingClass = FragmentCurrenciesBinding.inflate(inflater, container, false)
        return bindingClass.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(bindingClass) {
            refreshLayout.setOnRefreshListener {
                model.fetchCurrencies()
            }

            currenciesListView.addItemDecoration(
                DividerItemDecoration(
                    context,
                    DividerItemDecoration.VERTICAL
                )
            )
            currenciesListView.adapter = adapter

            model.currencies.observe(viewLifecycleOwner) { currencies ->
                adapter.setItems(currencies)
            }

            model.isLoading.observe(viewLifecycleOwner) { isLoading ->
                refreshLayout.isRefreshing = isLoading
            }

            model.toastMessage.observe(viewLifecycleOwner) { messageId ->
                if (messageId != null && context != null) {
                    val string = requireContext().getString(messageId)
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