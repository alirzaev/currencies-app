package io.github.alirzaev.currencies.features.currencies

import android.annotation.SuppressLint
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import io.github.alirzaev.currencies.R
import io.github.alirzaev.currencies.data.source.remote.dto.Currency

class CurrenciesListAdapter : RecyclerView.Adapter<CurrenciesListAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val currencyName: TextView = itemView.findViewById(R.id.currency_name)
        private val currencyValue: TextView = itemView.findViewById(R.id.currency_value)
        private val currencyCode: TextView = itemView.findViewById(R.id.currency_charcode)
        private val currencyNominal: TextView = itemView.findViewById(R.id.currency_nominal)
        private val currencyDelta: TextView = itemView.findViewById(R.id.currency_delta)

        fun bind(currency: Currency) {
            val context = itemView.context

            currencyName.text = currency.name
            currencyValue.text = currency.value.toString()
            currencyCode.text = currency.charCode
            currencyNominal.text =
                context.getString(R.string.currency_nominal, currency.nominal)
            val delta = currency.value - currency.previous

            currencyDelta.text = String.format("%1$+.4f", delta)
            currencyDelta.setTextColor(
                if (delta > 0) context.resources.getColor(R.color.success) else context.resources.getColor(
                    R.color.danger
                )
            )

            itemView.setOnClickListener {
                val clipboard =
                    context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                val clip = ClipData.newPlainText(
                    context.getString(R.string.currency_value_clip_label),
                    currency.value.toString()
                )
                clipboard.setPrimaryClip(clip)

                Toast.makeText(
                    context,
                    R.string.currency_value_copied_to_clipboard,
                    Toast.LENGTH_SHORT
                )
                    .show()
            }
        }
    }

    private var currencies: List<Currency> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.currency_row_item,
            parent,
            false
        )

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(currencies[position])
    }

    override fun getItemCount() = currencies.size

    @SuppressLint("NotifyDataSetChanged")
    fun setItems(items: List<Currency>) {
        currencies = items
        notifyDataSetChanged()
    }
}