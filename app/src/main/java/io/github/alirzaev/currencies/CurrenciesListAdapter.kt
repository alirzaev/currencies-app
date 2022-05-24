package io.github.alirzaev.currencies

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
import io.github.alirzaev.currencies.utils.dto.Currency

class CurrenciesListAdapter(private var currencies: List<Currency>) :
    RecyclerView.Adapter<CurrenciesListAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val currencyName: TextView
        val currencyValue: TextView
        val currencyCode: TextView
        val currencyNominal: TextView
        val currencyDelta: TextView

        init {
            currencyName = itemView.findViewById(R.id.currency_name)
            currencyValue = itemView.findViewById(R.id.currency_value)
            currencyCode = itemView.findViewById(R.id.currency_charcode)
            currencyNominal = itemView.findViewById(R.id.currency_nominal)
            currencyDelta = itemView.findViewById(R.id.currency_delta)
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
        val context = holder.itemView.context
        holder.currencyName.text = c.name
        holder.currencyValue.text = c.value.toString()
        holder.currencyCode.text = c.charCode
        holder.currencyNominal.text =
            holder.itemView.context.getString(R.string.currency_nominal, c.nominal)
        val delta = c.value - c.previous

        holder.currencyDelta.text = String.format("%1$+.4f", delta)
        holder.currencyDelta.setTextColor(
            if (delta > 0) context.resources.getColor(R.color.success) else context.resources.getColor(
                R.color.danger
            )
        )

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

    @SuppressLint("NotifyDataSetChanged")
    fun setItems(items: List<Currency>) {
        currencies = items
        notifyDataSetChanged()
    }
}