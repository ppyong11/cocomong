package com.sw.cocomong.view.activity;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.sw.cocomong.Object.RefObj;
import com.sw.cocomong.R;
import com.sw.cocomong.task.BackgroundService;
import com.sw.cocomong.task.foodtask.RecipeListGetTask;
import com.sw.cocomong.task.reftask.RefListGetTask;
import com.sw.cocomong.view.adapter.RefAdapter;

import org.json.JSONException;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

public class RefridgeActivity extends AppCompatActivity implements RefListGetTask.RefListGetTaskListener{

    ListView list;
    ImageButton refAdd, setting;
    private static final int REQUEST_SYSTEM_ALERT_WINDOW = 123;
    RefAdapter refAdapter;
    List<RefObj> refObjs=new ArrayList<>();
    String username;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.refridge_list);
        Intent nameIntent=getIntent();
        Bundle nameExtras=nameIntent.getExtras();
        SharedPreferences sh = getSharedPreferences("UserSharedPref", MODE_PRIVATE);
        username=sh.getString("username", "");  // login name

        try {
            new RefListGetTask(username, this);
            //refList=refListGetTask.getList();
        } catch (JSONException e) {
            Log.d("RefListGetTask", "안됨");
            throw new RuntimeException(e);
        }

        list = findViewById(R.id.list_ref);
        refAdd = findViewById(R.id.btn_refplus);
        setting=findViewById(R.id.btn_setting);


        refAdapter = new RefAdapter(RefridgeActivity.this, refObjs );
        list.setAdapter(refAdapter);

        list.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent = new Intent(RefridgeActivity.this, UserActivity.class);
            intent.putExtra("refname", refObjs.get(position).getRefName());
            //intent.putExtra("refnum",refObjs.get(position).getRefnum());
            intent.putExtra("username",username);
            startActivity(intent);

        });
        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view,
                                           int position, long id) {
                Intent intent = new Intent(RefridgeActivity.this, RefDeleteActivity.class);
                intent.putExtra("refname", refObjs.get(position).getRefName());

                intent.putExtra("refnum",refObjs.get(position).getRefid());
                intent.putExtra("username",username);
                startActivity(intent);
                refAdapter.notifyDataSetChanged();

                // 이벤트 처리 종료 , 여기만 리스너 적용시키고 싶으면 true , 아니면 false
                return true;
            }
        });

        refAdd.setOnClickListener(v -> {
            Intent intent = new Intent(RefridgeActivity.this, RefAddActivity.class);
            intent.putExtra("username",username);
            startActivity(intent);
        });

        setting.setOnClickListener(v->{
            Intent intent = new Intent(RefridgeActivity.this, SettingActivity.class);
            intent.putExtra("username",username);
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            new RefListGetTask(username,this);
            Log.d("Ref", "서비스 개수(삭제/생성 전):"+String.valueOf(getRunningServicesCount(this)));
            if (getRunningServicesCount(this) == 1) {
                stopService(new Intent(this, BackgroundService.class));
                Log.d("Ref", "서비스 삭제:"+String.valueOf(getRunningServicesCount(this)));
            }
            if(getRunningServicesCount(this) == 0){
                //background 인텐트 실행 및 refObjs 데이터 보내기
                Intent serviceIntent = new Intent(this, BackgroundService.class);
                //RefObj 객체의 모든 refName 리스트에 추가
                ArrayList<String> refNames = new ArrayList<>();
                for (RefObj refObj : refObjs) {
                    refNames.add(refObj.getRefName());
                    Log.d("refActivity", "refNames: "+String.valueOf(refNames.size())+username);
                }
                serviceIntent.putStringArrayListExtra("refNames", refNames);
                serviceIntent.putExtra("userName",username);
                startService(serviceIntent);
                Log.d("refActivity", "refNames: "+String.valueOf(refNames.size())+username);
                Log.d("refActivity", "BackgroundService 시작됨");
                Log.d("Ref", "서비스 생성:"+String.valueOf(getRunningServicesCount(this)));
            }
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        refAdapter.notifyDataSetChanged();
    }

    //데이터를 background로 보내기 위해 해당 함수 내에 코드 첨부
    @Override
    public void onRefListReceived(List<RefObj> refObjs) {
        this.refObjs.clear();
        this.refObjs.addAll(refObjs);
        updateRefUI();
    }

    private void updateRefUI() {
        refAdapter.notifyDataSetChanged();
    }

    //백그라운드 알림 권한
    // 시스템 오버레이 권한을 확인하고 요청합니다.
    private void checkSystemAlertWindowPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.canDrawOverlays(this)) {
                requestAlertWindowPermission();
            } else {
                // 이미 권한이 있는 경우 알림을 표시합니다.
                Log.d("ref: 알림 권한","권한 O");
            }
        }
    }

    // 권한 요청 후 처리를 위한 메서드입니다.
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_SYSTEM_ALERT_WINDOW) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // 권한이 부여되면 알림을 표시합니다.
                Log.d("ref: 알림 권한","권한 O");
            }
        }
    }

    // 시스템 오버레이 권한을 요청합니다.
    private void requestAlertWindowPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.canDrawOverlays(this)) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                        Uri.parse("package:" + getPackageName()));
                startActivityForResult(intent, REQUEST_SYSTEM_ALERT_WINDOW);
            } else {
                // 이미 권한이 있는 경우 알림을 표시합니다.
                Log.d("ref: 알림 권한","권한 O");
            }
        }
    }
    // 현재 실행 중인 서비스 개수 확인
    public int getRunningServicesCount(Context context) {
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        if (manager != null) {
            return manager.getRunningServices(Integer.MAX_VALUE).size();
        } else {
            return 0;
        }
    }
}

