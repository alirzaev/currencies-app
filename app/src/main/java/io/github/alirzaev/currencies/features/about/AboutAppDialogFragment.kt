package io.github.alirzaev.currencies.features.about

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import io.github.alirzaev.currencies.R

class AboutAppDialogFragment : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val context = requireActivity()
        val builder = AlertDialog.Builder(context)
            .setTitle(R.string.about_app)
            .setMessage(R.string.about_app_description)
            .setPositiveButton(android.R.string.ok) { _, _ -> ; }

        return builder.create()
    }
}