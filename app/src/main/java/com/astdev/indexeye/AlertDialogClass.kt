package com.astdev.indexeye

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater

class AlertDialogClass {

    companion object {
        @SuppressLint("InflateParams")
        fun progressDialog(context: Context?): Dialog {
            val dialog = Dialog(context!!)
            val inflate = LayoutInflater.from(context).inflate(R.layout.loading_dialog, null)
            dialog.setContentView(inflate)
            dialog.setCancelable(false)
            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.WHITE))
            return dialog
        }
    }
}