package com.sw.cocomong.task;

import static com.sw.cocomong.task.BackgroundService.NOTIFICATION_ID;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.sw.cocomong.R;
import com.sw.cocomong.view.activity.UserActivity;

public class NotificationService {
    static String CHANNEL_NAME = "Food Expiry Notifications";
    //채널 생성
    static String CHANNEL_ID = "foreground_service_channel"; // Foreground 서비스용 채널 ID

    // Foreground 서비스용 채널 생성
    public static void createNotificationChannel(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    "Service Notifications", // 동일한 채널 이름 사용
                    NotificationManager.IMPORTANCE_HIGH // IMPORTANCE_HIGH 설정
            );
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        }
        Log.d("NotificationService", "Foreground 채널 생성 완료");
    }

    public static void showNotification(Context context, String foodName, String expireDate) {
        Intent notificationIntent = new Intent(context, UserActivity.class);

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, notificationIntent, PendingIntent.FLAG_MUTABLE);

        // 팝업 알림 스타일 생성
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setContentTitle("유통기한 알림")
                .setContentText(foodName + "의 유통기한이 " + expireDate + "까지입니다.")
                .setSmallIcon(R.drawable.notification_icon)
                .setPriority(NotificationCompat.PRIORITY_MAX) // 팝업 알림의 우선순위를 높임
                .setDefaults(NotificationCompat.DEFAULT_SOUND | NotificationCompat.DEFAULT_VIBRATE)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE) // 메시지 카테고리로 설정하여 팝업 알림 스타일을 적용
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        // 알림 표시
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            Log.d("NotificationService", "권한 없음");
            // 권한이 없는 경우 알림을 표시하지 않음
            return;
        }
        notificationManager.notify(NOTIFICATION_ID, builder.build());
    }

}