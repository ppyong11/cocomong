package com.sw.cocomong.task;

import static com.sw.cocomong.task.BackgroundService.NOTIFICATION_ID;

import android.Manifest;
import android.app.Notification;
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

import java.util.List;

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

    public static Notification showNotification(BackgroundService backgroundService, Integer count, String userName, String refname, List<List<String>> foodInfoList) {
        // showNotification 메서드의 매개변수에 BackgroundService 인스턴스를 추가
        Context context= backgroundService;
        if(context == null){
            Log.d("ccc", "null임");
        }else
            Log.d("ccc", "null아님");
        Intent notificationIntent = new Intent(context, UserActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, notificationIntent, PendingIntent.FLAG_MUTABLE);
        int notificationId = NOTIFICATION_ID + count;
        String refName= refname;
        String state= "";
        // 여러 음식의 정보를 문자열로 만들기
        StringBuilder contentTextBuilder = new StringBuilder();
        if(foodInfoList.size() > 0){
            for (List<String> foodInfo : foodInfoList) {
                state= foodInfo.get(0);
                String foodName = foodInfo.get(1);
                String expireDate = foodInfo.get(2);
                Log.d("Noti", state+refName+foodName+expireDate);
                if(state.equals("만료"))
                    contentTextBuilder.append(foodName).append("의 유통기한이 ").append(expireDate).append("로 상했어요 ㅠ.ㅠ\n");
                else
                    contentTextBuilder.append(foodName).append("의 유통기한이 ").append(expireDate).append("까지예요!\n");
            }
        }else
            contentTextBuilder.append("유통기한이 임박하거나 상한 식재료가 없어요!\n");

        String contentText = contentTextBuilder.toString().trim();

        // 팝업 알림 스타일 생성
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setContentText(contentText)
                .setSmallIcon(R.drawable.notification_icon)
                .setPriority(NotificationCompat.PRIORITY_MAX) // 팝업 알림의 우선순위를 높임
                .setDefaults(NotificationCompat.DEFAULT_SOUND | NotificationCompat.DEFAULT_VIBRATE)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE) // 메시지 카테고리로 설정하여 팝업 알림 스타일을 적용
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);
        if (foodInfoList.size() == 0) {
            // 음식 정보가 없을 때
            builder.setContentTitle(userName+" 님! "+ "<"+refName+"> 한 번 들여다 보시지 않을래요?"+" \uD83D\uDC40");

        } else{
            builder.setContentTitle(userName+" 님! "+ "<"+refName+">의 음식이 상해 가고 있어요!"+" \uD83E\uDD7A"); //이모지: \uD83D\uDE22
        }

        // 접기/펼치기 기능 추가
        NotificationCompat.Style style = new NotificationCompat.BigTextStyle()
                .bigText(contentText); // 긴 텍스트 설정
        builder.setStyle(style);

        Notification notification = builder.build();

        // 알림 표시
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            Log.d("NotificationService", "권한 없음");
        }
        notificationManager.notify(notificationId, notification);
        return notification;
    }
}