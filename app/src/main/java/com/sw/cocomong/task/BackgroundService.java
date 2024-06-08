package com.sw.cocomong.task;

import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.app.Service;
import android.util.Log;
import androidx.annotation.Nullable;


import com.sw.cocomong.Object.FoodExpObj;
import com.sw.cocomong.Object.FoodResObj;
import com.sw.cocomong.task.foodtask.FoodListGetTask;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class BackgroundService extends Service {
    public static final int NOTIFICATION_ID = 1;
    private static final int ALARM_HOUR = 9; // 매일 오전 8시
    private static final String CHANNEL_ID = "foreground_service_channel"; // 동일한 채널 ID 사용
    private static final CharSequence CHANNEL_NAME = "Foreground Service Channel";
    private Handler handler;
    private Runnable checkTimeRunnable;

    String username,refname,refnum;

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

    /*

    private void getExpFoodList(){
        for (int i=0; i<this.refname.size(); i++){
            try {
                new FoodListGetTask((this.username, this.refname.get(i).toString(), new FoodListGetTask.FoodListGetTaskListener(){
                    @Override
                    public void onFoodListReceived(List<FoodResObj> foodResObjs) {

                    }

                    Calendar calendar = Calendar.getInstance();
                    @Override
                    public void onFoodReceived(List<FoodExpObj> foodExpList){
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM");
                        String monthStr = dateFormat.format(new Date());
                        int currentDayInt = calendar.get(Calendar.DAY_OF_MONTH);
                        foodExpList.clear();
                        foodExpList.addAll(foodList);
                        List<List<String>> expMgmtList = new ArrayList<>();
                        for (int i = 0; i < foodExpList.size(); i++) {
                            FoodExpObj foodExpObj = foodExpList.get(i);
                            String foodName= foodExpObj.getFoodname();
                            String foodName= foodExpObj.getFoodname();
                            String foodExp= foodExpObj.getExpiredate();
                            String[] parts= foodExp.split("/");
                            String foodMonth= parts[0]+"/"+parts[1]; //"YYYY/MM"추출
                            Integer foodDay= Integer.parseInt(parts[2]); //DD 추출
                            if (foodMonth.equals(monthStr)){
                                //음식이 exp가 이번 달이라면
                                if(foodDay >= currentDayInt && foodDay <= currentDayInt+3){
                                    //유통기한이 오늘이거나 오늘 날짜 +3일 이내라면
                                    List<String> item = new ArrayList<>();
                                    item.add("임박");
                                    item.add(refName);
                                    item.add(foodName);
                                    item.add(foodExp);

                                    expMgmtList.add(item);
                                } else if (foodDay < currentDayInt) {
                                    // 상함
                                    List<String> item = new ArrayList<>();
                                    item.add("만료");
                                    item.add(refName);
                                    item.add(foodName);
                                    item.add(foodExp);

                                    expMgmtList.add(item);
                                }
                                else//이번 달이기만 하면~
                                    break;
                            }
                        }
                    }
                }))
            }

        }
    }

     */


}

