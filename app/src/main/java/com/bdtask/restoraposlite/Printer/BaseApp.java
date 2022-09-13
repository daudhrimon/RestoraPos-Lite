package com.bdtask.restoraposlite.Printer;

import android.app.Application;
import com.bdtask.restoraposlite.Printer.PrinterUtil.SunmiPrintHelper;

public class BaseApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        init();
    }
    /**
     * Connect print service through interface library
     */
    private void init() {
        SunmiPrintHelper.getInstance().initSunmiPrinterService(this);
    }
}
