package com.bdtask.restoraposroomdbtab.Dialog

import android.content.Context
import android.os.Bundle
import cn.pedant.SweetAlert.SweetAlertDialog

class InvoicePrintDialog(context: Context) : SweetAlertDialog(context, SUCCESS_TYPE) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        titleText = "Do You Want To Print Invoice ?"
        cancelText = "No"
        confirmText = "Yes"


    }
}