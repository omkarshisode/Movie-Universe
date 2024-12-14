package com.example.movieuniverse.utiles

import android.app.ProgressDialog
import android.content.Context

class Loader {
    companion object {
        private var progressDialog: ProgressDialog? = null

        fun showLoader(context: Context, title: String, message: String) {
            progressDialog = ProgressDialog(context)
            progressDialog?.setTitle(title)
            progressDialog?.setMessage(message)
            progressDialog?.setCancelable(false)
            progressDialog?.show()
        }

        fun hideLoader() {
            progressDialog?.hide()
        }
    }
}