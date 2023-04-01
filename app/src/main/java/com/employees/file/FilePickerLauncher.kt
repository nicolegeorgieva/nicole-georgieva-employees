package com.employees.file

import android.content.Context
import android.content.Intent
import android.net.Uri
import javax.inject.Inject

class FilePickerLauncher @Inject constructor(
) : ActivityLauncher<Unit, Uri?>() {
    override fun intent(context: Context, input: Unit): Intent = Intent(
        Intent.ACTION_OPEN_DOCUMENT
    ).apply {
        addCategory(Intent.CATEGORY_OPENABLE)
        type = "*/*"
        putExtra(Intent.EXTRA_MIME_TYPES, arrayOf("text/csv", "text/comma-separated-values"))
    }

    override fun onActivityResult(resultCode: Int, intent: Intent?): Uri? = intent?.data
}
