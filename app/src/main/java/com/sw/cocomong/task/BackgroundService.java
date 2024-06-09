package com.sw.cocomong.task;

import android.app.Notification;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.app.Service;
import android.util.Log;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationManagerCompat;


import com.sw.cocomong.Object.FoodResObj;
import com.sw.cocomong.R;
import com.sw.cocomong.task.foodtask.FoodListGetTask;

import org.json.JSONException;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Timer;

public class BackgroundService extends Service {
    public static final int NOTIFICATION_ID = 1;
    private Handler handler;
    private Runnable checkTimeRunnable;
    Timer timer;
    List<String> refnames = new ArrayList<>();
    String username;
    List<FoodResObj> foodResObjs = new ArrayList<>();
    // BackgroundService 인스턴스를 저장할 정적 변수
    private static BackgroundService instance;
    Integer count= 0;
    @Override
    public void onCreate() {
        super.onCreate();
        NotificationService.createNotificationChannel(this); // 채널 생성 코드
        instance= this;
    }
    // NotificationService에서 BackgroundService의 인스턴스를 얻는 메서드
    public static BackgroundService getInstance() {
        return instance;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null) {
            Bundle extras = intent.getExtras();
            if (extras != null) {
                refnames = extras.getStringArrayList("refNames");  // 냉장고 위치 받아옴
                username = extras.getString("userName");  // username 받아옴
                // refname이 null인지 확인
            }
        }

        // 포그라운드 서비스로 시작
        Notification initialNotification = new Notification.Builder(this, NotificationService.FORE_CHANNEL_ID)
                .setContentTitle("서비스 실행 중")
                .setContentText("백그라운드 서비스가 실행 중입니다.")
                .setSmallIcon(R.drawable.notification_icon)
                .build();
        startForeground(NOTIFICATION_ID, initialNotification);

        // 타이머를 초기화하고 1분마다 작업을 수행하도록 설정
        // Handler를 사용하여 일정한 간격으로 작업을 수행
        handler = new Handler();
        checkTimeRunnable = new Runnable() {
            @Override
            public void run() {
                // 1분마다 시간을 확인하고 알림을 보여줌
                Calendar calendar = Calendar.getInstance();
                int currentHour = calendar.get(Calendar.HOUR_OF_DAY);
                int currentMinute = calendar.get(Calendar.MINUTE);
                Log.d("BackgroundService", "Current time: " + currentHour + ":" + currentMinute);
                if (currentHour == 19 && currentMinute == 10) {
                    getFoodList();
                }
                // 다음 작업을 예약
                handler.postDelayed(this, 60 * 1000);
            }
        };
        // 서비스가 시작되면 시간 확인을 시작
        handler.post(checkTimeRunnable);
        return START_STICKY;
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        // 핸들러가 null이 아닌 경우 메시지를 지우고 핸들러를 제거하여 메모리 누수를 방지합니다.
        if (handler != null) {
            handler.removeCallbacks(checkTimeRunnable);
            handler = null;
        }
        // 타이머가 null이 아닌 경우 타이머를 취소하여 메모리 누수를 방지합니다.
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void getFoodList() {
        // 첫 번째 냉장고부터 처리 시작
        processNextRef(0);
    }

    private void processNextRef(final int index) {
        // 모든 냉장고를 처리했는지 확인
        if (index >= refnames.size()) {
            return;
        }

        final String refName = refnames.get(index);
        try {
            // FoodListGetTask를 통해 각 refName에 해당하는 음식 정보 가져오기
            new FoodListGetTask(this.username, refName, new FoodListGetTask.FoodListGetTaskListener() {
                @Override
                public void onFoodListReceived(List<FoodResObj> foodList) {
                    Notification updatedNotification;
                    // 현재 날짜 구하기
                    Calendar calendar = Calendar.getInstance();
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM");
                    String monthStr = dateFormat.format(new Date());
                    int currentDayInt = calendar.get(Calendar.DAY_OF_MONTH);

                    // 음식 정보를 해당 refName에 대한 FoodResObj 리스트에 저장
                    foodResObjs.clear();
                    foodResObjs.addAll(foodList); // 각 냉장고에 있는 모든 음식을 가져옴
                    List<List<String>> expMgmtList = new ArrayList<>(); // 임박한 음식 목록

                    for (int i = 0; i < foodResObjs.size(); i++) {
                        FoodResObj foodResObj = foodResObjs.get(i);
                        String foodName = foodResObj.getFoodname();
                        String foodExp = foodResObj.getExpiredate();
                        String[] parts = foodExp.split("/");
                        String foodMonth = parts[0] + "/" + parts[1]; //"YYYY/MM" 추출
                        Integer foodDay = Integer.parseInt(parts[2]); // DD 추출
                        if (foodMonth.equals(monthStr)) {
                            // 음식의 유통기한이 이번 달이라면
                            if (foodDay >= currentDayInt && foodDay <= currentDayInt + 3) {
                                // 유통기한이 오늘이거나 오늘 날짜 +3일 이내라면
                                List<String> item = new ArrayList<>();
                                item.add("임박");
                                item.add(foodName);
                                item.add(foodExp);

                                expMgmtList.add(item);
                            } else if (foodDay < currentDayInt) {
                                // 상함
                                List<String> item = new ArrayList<>();
                                item.add("만료");
                                item.add(foodName);
                                item.add(foodExp);

                                expMgmtList.add(item);
                            }
                        }
                    }

                    // 알림 업데이트
                    if (foodResObjs.size() == 0) {
                        // 음식이 없는 경우
                        updatedNotification = NotificationService.showNotification(
                                BackgroundService.getInstance(),
                                index,
                                username,
                                refName,
                                expMgmtList,
                                false
                        );
                    } else {
                        // 음식이 있는 경우
                        updatedNotification = NotificationService.showNotification(
                                BackgroundService.getInstance(),
                                index,
                                username,
                                refName,
                                expMgmtList,
                                true
                        );
                    }

                    NotificationManagerCompat notificationManager = NotificationManagerCompat.from(BackgroundService.getInstance());
                    if (ActivityCompat.checkSelfPermission(BackgroundService.this, android.Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {
                        notificationManager.notify(NOTIFICATION_ID + index, updatedNotification);
                    } else {
                        Log.e("BackgroundService", "POST_NOTIFICATIONS permission not granted.");
                    }

                    // 다음 냉장고를 처리
                    processNextRef(index + 1);
                }
            });
        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }
    }
}