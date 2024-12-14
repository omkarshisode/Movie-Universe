package com.example.movieuniverse.utiles

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.widget.ImageView
import androidx.core.app.NotificationCompat.MessagingStyle.Message
import com.bumptech.glide.Glide

fun ImageView.loadImage(imageUrl: String?) {
    if (imageUrl == null) {
        return
    }
    Glide.with(this)
        .load(imageUrl)
        .into(this)
}

fun Activity.showAlert(title: String, message: String) {
    AlertDialog.Builder(this)
        .setTitle(title)
        .setMessage(message)
        .setPositiveButton("Ok") { dialog, _ ->
            dialog.dismiss()
        }
        .show()

}