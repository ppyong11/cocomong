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
import androidx.core.app.TaskStackBuilder;

import com.sw.cocomong.R;
import com.sw.cocomong.view.activity.UserActivity;

import java.util.List;

public class NotificationService {
    static String CHANNEL_NAME = "Food Expiry Notifications";
    //채널 생성
    static String CHANNEL_ID = "Background_service_channel"; // Background 서비스용 채널 ID
    static String FORE_CHANNEL_ID = "Foreground_service_channel"; // Foreground 서비스용 채널 ID

    // Foreground 서비스용 채널 생성
    public static void createNotificationChannel(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel foregroundChannel = new NotificationChannel(
                    FORE_CHANNEL_ID,
                    "service foreground",
                    NotificationManager.IMPORTANCE_MIN);

            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    "Service Notifications", // 동일한 채널 이름 사용
                    NotificationManager.IMPORTANCE_HIGH); // IMPORTANCE_HIGH 설정

            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
                notificationManager.createNotificationChannel(foregroundChannel);
            }
        }

        Log.d("NotificationService", "Foreground 채널 생성 완료");
    }
    //백그라운드 서비스 알림
    public static Notification getForegroundServiceNotification(Context context) {
        return new NotificationCompat.Builder(context, FORE_CHANNEL_ID)
                .setContentTitle("서비스 실행 중")
                .setContentText("백그라운드 서비스가 실행 중입니다.")
                .setSmallIcon(R.drawable.main_logo)
                .setPriority(NotificationCompat.PRIORITY_LOW) // 중요도 낮춤
                .build();
    }

    public static Notification showNotification(BackgroundService backgroundService, Integer count, String userName, String refname, List<List<String>> foodInfoList, Boolean inFood) {
        // showNotification 메서드의 매개변수에 BackgroundService 인스턴스를 추가
        Context context= backgroundService;
        Log.d("noti", userName+inFood);
        if(context == null){
            Log.d("ccc", "null임");
        }else
            Log.d("ccc", "null아님");
        // 각 알림에 대한 Intent 및 PendingIntent 설정

        Intent notificationIntent;
        PendingIntent pendingIntent;
        int notificationId = NOTIFICATION_ID + count;
        String refName= refname;
        String state= "";

        int requestCode = count; // 각 알림에 고유한 requestCode 사용, intent 이동
        notificationIntent = new Intent(context, UserActivity.class);
        notificationIntent.putExtra("refname", refname);
        notificationIntent.putExtra("username", userName);

        // TaskStackBuilder를 사용하여 Back Stack을 생성
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addNextIntentWithParentStack(notificationIntent);
        pendingIntent = stackBuilder.getPendingIntent(count, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_MUTABLE);


        // 여러 음식의 정보를 문자열로 만들기
        StringBuilder contentTextBuilder = new StringBuilder();
        if(foodInfoList.size() > 0 && inFood){
            for (List<String> foodInfo : foodInfoList) {
                state= foodInfo.get(0);
                String foodName = foodInfo.get(1);
                String expireDate = foodInfo.get(2);
                Log.d("Noti", state+refName+foodName+expireDate);
                if(state.equals("만료"))
                    contentTextBuilder.append(foodName).append("의 유통기한이 ").append(expireDate).append("로 상했어요 (˘•̥ㅁ•̥˘)\n");
                else
                    contentTextBuilder.append(foodName).append("의 유통기한이 ").append(expireDate).append("까지예요!\n");
            }
        }else if(foodInfoList.size() == 0 && inFood) {
            contentTextBuilder.append("유통기한이 임박하거나 상한 식재료가 없어요!\n");
        } else if (!inFood) {
            contentTextBuilder.append("식재료를 추가하시면 유통기한 관리를 도와드릴게요 (｡•̀ᴗ-)✧");
        }

        String contentText = contentTextBuilder.toString().trim();

        // 팝업 알림 스타일 생성
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setContentText(contentText)
                .setSmallIcon(R.drawable.ahyun_logo)
                .setPriority(NotificationCompat.PRIORITY_MAX) // 팝업 알림의 우선순위를 높임
                .setDefaults(NotificationCompat.DEFAULT_SOUND | NotificationCompat.DEFAULT_VIBRATE)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE) // 메시지 카테고리로 설정하여 팝업 알림 스타일을 적용
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);
        if (foodInfoList.size() == 0 && inFood) {
            //내부에 음식은 있지만 상한 건 없을 때
            builder.setContentTitle(userName+" 님! "+ "<"+refName+"> 한 번 들여다 보시지 않을래요?"+" \uD83D\uDC40");

        } else if (foodInfoList.size() != 0 && inFood){ //내부에 음식 o, 임박 o
            builder.setContentTitle(userName+" 님! "+ "<"+refName+">의 음식이 상해 가고 있어요!"+" \uD83E\uDD7A"); //이모지: \uD83D\uDE22
        } else if (!inFood) {
            //냉장고 내부에 음식이 없을 때
            builder.setContentTitle(userName+" 님! "+ "<"+refName+">에 추가된 음식이 없어요"+" \uD83D\uDE4A");
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