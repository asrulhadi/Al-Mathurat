package com.apprikot.mathurat.controller.components;

import android.content.Context;
import android.os.Build;
import android.util.Log;


import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;

public class CustomExceptionHandler implements Thread.UncaughtExceptionHandler {
    private Thread.UncaughtExceptionHandler defaultUEH;
    private Context context;

    public CustomExceptionHandler(Context context) {
        this.context = context;
        this.defaultUEH = Thread.getDefaultUncaughtExceptionHandler();
    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        final Writer result = new StringWriter();
        final PrintWriter printWriter = new PrintWriter(result);
        ex.printStackTrace(printWriter);
        String stacktrace = result.toString();
        sendReport(stacktrace);
        printWriter.close();
        defaultUEH.uncaughtException(thread, ex);
    }

    private void sendReport(final String report) {
        Log.i("Report", "Sending...");
        new Thread(new Runnable() {
            @Override
            public void run() {
                // TODO: report crashes on different API
                // report(report);
            }
        }).start();
    }

    private void report(String exception) {
//        HashMap<String, Object> params = new HashMap<>();
//        params.put("app", "Qanati");
//        params.put("udid", UDID.getDeviceId(context));
//        params.put("device", Build.DEVICE);
//        params.put("model", Build.MODEL + " - " + Build.PRODUCT);
//        params.put("manufacturer", Build.MANUFACTURER);
//        params.put("android_version_int", String.valueOf(Build.VERSION.SDK_INT));
//        params.put("android_version", Build.VERSION.RELEASE);
//        params.put("app_version", AppUtils.getVersionCode(context));
//        params.put("app_version_code", String.valueOf(AppUtils.getVersionCode(context)));
//        params.put("free_memory", "-");
//        params.put("connection_type", "-");
//        params.put("language", "-");
//        params.put("time_zone", "-");
//        params.put("exception", exception);
//        String data = StringUtils.pojoToJson(params);
//        RestWebService restWebService = new RestWebService(context, WebserviceType.CRASH_REPORTING);
//        SimpleResponse simpleResponse = restWebService.call(new String[]{data});
//        Log.i("Report", "response: " + simpleResponse.response);
    }
}