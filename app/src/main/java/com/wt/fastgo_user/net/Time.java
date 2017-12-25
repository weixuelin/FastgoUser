package com.wt.fastgo_user.net;


import android.os.Handler;
import android.os.Message;


public class Time extends Thread {
    private int time;
    private int code;
    private Handler handler;

    public Time(int time, int code, Handler handler) {
        this.time = time;
        this.code = code;
        this.handler = handler;
    }

    @Override
    public void run() {
        super.run();
        for (int i = time; i >= 0; i--) {
            Message message = handler.obtainMessage();
            message.what = code;
            message.arg1 = i;
            handler.sendMessage(message);
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
