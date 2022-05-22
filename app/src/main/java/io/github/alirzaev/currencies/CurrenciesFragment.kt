package io.github.alirzaev.currencies

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import io.github.alirzaev.currencies.utils.dto.Currency

private class CurrenciesListAdapter(private val currencies: List<Currency>) :
    RecyclerView.Adapter<CurrenciesListAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val currencyName: TextView
        val currencyValue: TextView
        val currencyCode: TextView

        init {
            currencyName = itemView.findViewById(R.id.currency_name)
            currencyValue = itemView.findViewById(R.id.currency_value)
            currencyCode = itemView.findViewById(R.id.currency_charcode)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.currency_row_item,
            parent,
            false
        )

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val c = currencies[position]
        holder.currencyName.text = c.name
        holder.currencyValue.text = c.value.toString()
        holder.currencyCode.text = c.charCode

        holder.itemView.setOnClickListener {
            val context = holder.itemView.context
            val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText(
                context.getString(R.string.currency_value_clip_label),
                c.value.toString()
            )
            clipboard.setPrimaryClip(clip)

            Toast.makeText(context, R.string.currency_value_copied_to_clipboard, Toast.LENGTH_SHORT)
                .show()
        }
    }

    override fun getItemCount(): Int {
        return currencies.size
    }
}

/**
 * Use the [CurrenciesFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CurrenciesFragment : Fragment() {
    private lateinit var model: CurrenciesViewModel

    private val _tag: String = CurrenciesFragment::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        model = ViewModelProvider(requireActivity()).get(CurrenciesViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_currencies, container, false)

        val list = view.findViewById<RecyclerView>(R.id.currencies_list_view)
        list.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val list = view.findViewById<RecyclerView>(R.id.currencies_list_view)
        val refreshLayout = view.findViewById<SwipeRefreshLayout>(R.id.refresh_layout)
        refreshLayout.setOnRefreshListener {
            model.fetchCurrencies()
        }

        list.adapter = CurrenciesListAdapter(emptyList())
        model.currencies.observe(viewLifecycleOwner) { currencies ->
            list.adapter = CurrenciesListAdapter(ArrayList<Currency>(currencies))
        }

        model.isLoading.observe(viewLifecycleOwner) { isLoading ->
            refreshLayout.isRefreshing = isLoading
        }

        model.toastMessage.observe(viewLifecycleOwner) { messageId ->
            Log.i(_tag, "Received message")
            if (messageId != null && context != null) {
                val string = requireContext().getString(messageId)
                Toast.makeText(context, string, Toast.LENGTH_SHORT).show()
                model.toastMessageShown()
            }
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment CurrenciesListFragment.
         */
        @JvmStatic
        fun newInstance() = CurrenciesFragment()
    }
}