package io.github.alirzaev.currencies.features.exchangerates

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
import io.github.alirzaev.currencies.data.model.Currency

class ExchangeRatesListAdapter : RecyclerView.Adapter<ExchangeRatesListAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val currencyName: TextView = itemView.findViewById(R.id.currency_name)
        private val exchangeRateValue: TextView = itemView.findViewById(R.id.exchange_rate_value)
        private val currencyCode: TextView = itemView.findViewById(R.id.currency_charcode)
        private val currencyNominal: TextView = itemView.findViewById(R.id.currency_nominal)
        private val exchangeRateDelta: TextView = itemView.findViewById(R.id.exchange_rate_delta)

        fun bind(currency: Currency) {
            val context = itemView.context

            currencyName.text = currency.name
            exchangeRateValue.text = currency.exchangeRate.toString()
            currencyCode.text = currency.charCode
            currencyNominal.text =
                context.getString(R.string.currency_nominal, currency.nominal)
            val delta = currency.exchangeRate - currency.previousExchangeRate

            exchangeRateDelta.text = String.format("%1$+.4f", delta)
            exchangeRateDelta.setTextColor(
                if (delta > 0) context.resources.getColor(
                    R.color.success,
                    context.theme
                ) else context.resources.getColor(
                    R.color.danger,
                    context.theme
                )
            )

            itemView.setOnClickListener {
                val clipboard =
                    context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                val clip = ClipData.newPlainText(
                    context.getString(R.string.exchange_rate_clip_label),
                    currency.exchangeRate.toString()
                )
                clipboard.setPrimaryClip(clip)

                Toast.makeText(
                    context,
                    R.string.exchange_rate_copied_to_clipboard,
                    Toast.LENGTH_SHORT
                )
                    .show()
            }
        }
    }

    private var currencies: List<Currency> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.view_holder_exchange_rate,
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