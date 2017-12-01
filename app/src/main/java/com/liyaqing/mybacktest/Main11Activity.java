package com.liyaqing.mybacktest;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Main11Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        TextView textView = findViewById(R.id.text);
        textView.setText("b11");
        Button btn = findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                startActivity(new Intent(Main11Activity.this,Main11Activity.class));

                ActivityManager manager = (ActivityManager) getApplicationContext()
                        .getSystemService(Context.ACTIVITY_SERVICE);


//                List<RunningTaskInfo> taskList = manager.getRunningTasks(100);
//                for (RunningTaskInfo rti : taskList) {
//                    Log.i("num", rti.numActivities + "");
//                    for (int i=0;i<rti.numActivities;i++) {
//                        try {
//                            String clsname=rti.topActivity.getClassName();
//                            Class clazz = Class.forName(clsname);
//                            Constructor c = clazz.getConstructor(null);
//                            Activity o =(Activity) c.newInstance(null);
//                            o.finish();
//                        }catch (Exception e){
//                            e.printStackTrace();
//                        }
//
//                        Log.i("name", rti.topActivity.getClassName() + "");
//
//                    }
//                    Log.i("showRunningTasks", rti.baseActivity.getClassName() + "-----" + rti.topActivity.getClassName());
//                }
//                if (Build.VERSION.SDK_INT >= 21) {
//                    List<ActivityManager.AppTask> appTasks = manager.getAppTasks();
//                    for (int i = 0; i < appTasks.size(); i++) {
//                        ActivityManager.AppTask appTask = appTasks.get(i);
//                        Log.i("name",  appTask.getTaskInfo()+ "");
//
////                        if (i != 0) {
////                            appTask.finishAndRemoveTask();
////                        }
//                    }
//                }
                List<Activity> activityList = getActivitiesByApplication(getApplication());
                Log.i("Activitysize", activityList.size() + "");
                Activity bottomActivity = null;
                for (int i = 0; i < activityList.size(); i++) {
                    Activity activity = activityList.get(i);
                    Log.i("Activityname", activity.getLocalClassName() + "," + i);
                    if (!activity.getLocalClassName().equals("MainActivity")) {
                        activity.finish();

                    } else {
                        Log.i("Activityname====", activity.getLocalClassName() + "," + i);
                        bottomActivity = activity;
                    }
                }
                if (Build.VERSION.SDK_INT >= 21) {
                    Log.i("addapptask", "dddddd");
                    Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
                    Activity activity1 = getParent();
                    MainActivity mainActivity;
                    try {
                        String pk = bottomActivity.getPackageName() + "." + bottomActivity.getLocalClassName();
                        Log.i("m====", pk);
//                        Class clazz = Class.forName(pk);
//                        Constructor c = clazz.getConstructor(new Class[0]);
//                        mainActivity = (MainActivity) c.newInstance(new Object[]{});
                        Context c = createPackageContext(bottomActivity.getPackageName(), Context.CONTEXT_INCLUDE_CODE | Context.CONTEXT_IGNORE_SECURITY);
                        //载入这个类
                        Class clazz = c.getClassLoader().loadClass(pk);
                        //新建一个实例
                        mainActivity  =(MainActivity) clazz.newInstance();
                        MainActivity.viewPager.setCurrentItem(2);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    startActivity(new Intent(getApplicationContext(), Main3Activity.class));
                    startActivity(new Intent(getApplicationContext(), Main31Activity.class));
                }

            }

        });
    }

    private static List<Activity> getActivitiesByApplication(Application application) {
        List<Activity> list = new ArrayList<>();
        try {
            Class<Application> applicationClass = Application.class;
            Field mLoadedApkField = applicationClass.getDeclaredField("mLoadedApk");
            mLoadedApkField.setAccessible(true);
            Object mLoadedApk = mLoadedApkField.get(application);
            Class<?> mLoadedApkClass = mLoadedApk.getClass();
            Field mActivityThreadField = mLoadedApkClass.getDeclaredField("mActivityThread");
            mActivityThreadField.setAccessible(true);
            Object mActivityThread = mActivityThreadField.get(mLoadedApk);
            Class<?> mActivityThreadClass = mActivityThread.getClass();
            Field mActivitiesField = mActivityThreadClass.getDeclaredField("mActivities");
            mActivitiesField.setAccessible(true);
            Object mActivities = mActivitiesField.get(mActivityThread);
            // 注意这里一定写成Map，低版本这里用的是HashMap，高版本用的是ArrayMap
            if (mActivities instanceof Map) {
                @SuppressWarnings("unchecked")
                Map<Object, Object> arrayMap = (Map<Object, Object>) mActivities;
                for (Map.Entry<Object, Object> entry : arrayMap.entrySet()) {
                    Object value = entry.getValue();
                    Class<?> activityClientRecordClass = value.getClass();
                    Field activityField = activityClientRecordClass.getDeclaredField("activity");
                    activityField.setAccessible(true);
                    Object o = activityField.get(value);
                    list.add((Activity) o);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            list = null;
        }
        return list;
    }

}
