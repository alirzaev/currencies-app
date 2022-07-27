package io.github.alirzaev.currencies.features.currencychooser

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import io.github.alirzaev.currencies.R
import io.github.alirzaev.currencies.data.model.Currency

class CurrenciesListAdapter(
    private val listener: (currency: Currency) -> Unit
) : RecyclerView.Adapter<CurrenciesListAdapter.ViewHolder>() {

    class ViewHolder(itemView: View, private val listener: (currency: Currency) -> Unit) :
        RecyclerView.ViewHolder(itemView) {
        private val currencyName: TextView = itemView.findViewById(R.id.currency_name)
        private val currencyCode: TextView = itemView.findViewById(R.id.currency_charcode)

        fun bind(currency: Currency) {
            currencyName.text = currency.name
            currencyCode.text = currency.charCode

            itemView.setOnClickListener {
                this@ViewHolder.listener(currency)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.view_holder_currency,
                parent,
                false
            ),
            listener
        )
    }

    private var currencies: List<Currency> = emptyList()

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(currencies[position])
    }

    override fun getItemCount(): Int = currencies.size

    @SuppressLint("NotifyDataSetChanged")
    fun setItems(items: List<Currency>) {
        currencies = items
        notifyDataSetChanged()
    }
}