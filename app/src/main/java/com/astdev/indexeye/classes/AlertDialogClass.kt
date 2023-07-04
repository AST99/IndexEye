package com.astdev.indexeye.classes

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.astdev.indexeye.R

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

        fun editUserInfoDialog(context: Context?): Dialog{
            val dialog = Dialog(context!!)
            dialog.setContentView(R.layout.edit_dialog)
            dialog.window!!.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
            dialog.window!!.setGravity(Gravity.BOTTOM)
            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.setCanceledOnTouchOutside(false)
            return dialog
        }

        fun logOutDialog(context: Context?):Dialog{
            val dialog = Dialog(context!!)
            dialog.setContentView(R.layout.log_out_dialog)
            dialog.window!!.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
            dialog.window!!.setGravity(Gravity.CENTER)
            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.setCanceledOnTouchOutside(false)
            return dialog
        }
    }
}