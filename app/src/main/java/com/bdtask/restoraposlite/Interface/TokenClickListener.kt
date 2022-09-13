package com.bdtask.restoraposlite.Interface

import com.bdtask.restoraposlite.Dialog.TokenPrintDialog

interface TokenClickListener {
    fun onTokenButtonsClick(tokenPrintDialog: TokenPrintDialog)
}