package com.sw.cocomong.task;

import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.app.Service;
import android.util.Log;
import androidx.annotation.Nullable;


import java.util.Calendar;

public class BackgroundService extends Service {
    public static final int NOTIFICATION_ID = 1;
    private static final int ALARM_HOUR = 9; // 매일 오전 8시
    private static final String CHANNEL_ID = "foreground_service_channel"; // 동일한 채널 ID 사용
    private static final CharSequence CHANNEL_NAME = "Foreground Service Channel";
    private Handler handler;
    private Runnable checkTimeRunnable;

    @Override
    public void onCreate() {
        super.onCreate();
        handler = new Handler();
        checkTimeRunnable = new Runnable() {
            @Override
            public void run() {
                checkTimeAndShowNotification();
                // 1분마다 시간을 확인하고 알림을 보여줌
                handler.postDelayed(this, 60 * 1000);
                printCurrentTime();
            }
        };
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        NotificationService.createNotificationChannel(this); // 채널 생성 코드

        // 서비스가 시작되면 시간 확인을 시작
        handler.post(checkTimeRunnable);
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        // 서비스가 종료될 때 handler를 제거하여 메모리 누수를 방지
        handler.removeCallbacks(checkTimeRunnable);
        // Foreground 서비스 중지
        stopForeground(true);
        super.onDestroy();
    }

    private void checkTimeAndShowNotification() {
        Calendar calendar = Calendar.getInstance();
        int currentHour = calendar.get(Calendar.HOUR_OF_DAY);
        int currentMinute = calendar.get(Calendar.MINUTE);

        if (currentHour == 8 && currentMinute == 54) {
            NotificationService.showNotification(this, "8:54", "테스트");
        }
    }
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void printCurrentTime() {
        // 현재 시간 출력
        Calendar calendar = Calendar.getInstance();
        int hourOfDay = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        Log.d("BackgroundService", "Current time: " + hourOfDay + ":" + minute);
    }
}

