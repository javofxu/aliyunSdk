package com.aliyun.iot.aep.sdk;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.location.LocationManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.view.Display;
import android.view.WindowManager;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;


/**
 * Created by jishu0001 on 2016/8/30.
 */
public class UnitTools {
    private Context context;
    private static MediaPlayer mediaPlayer = null;
    private static SoundPool soundPool = null;
    private static int streamID ;
    public UnitTools(Context context){
        this.context = context;
    }

    /**
     * get usr Login infor
     * @param name
     * @param key
     */
    public void writeUserInro(String name, String key){
        SharedPreferences user = context.getSharedPreferences("user_info",0);
        SharedPreferences.Editor mydata = user.edit();
        mydata.putString("uName" ,name);
        mydata.putString("uKey",key);
        mydata.commit();
    }
    public Map readUserInfo() {
        Map<String, Object> chosed = new HashMap<>();
        SharedPreferences wode = context.getSharedPreferences("user_info",0);
        String name = wode.getString("uName",null);
        String key = wode.getString("uKey",null);
        chosed.put("name",name);
        chosed.put("key",key);
        return chosed;
    }

    /**
     * methodname:
     * 作者：Henry on 2017/3/6 8:53
     * 邮箱：xuejunju_4595@qq.com
     * 参数:context
     * 返回:当前运行的activity的名称
     */
    @RequiresApi(api = Build.VERSION_CODES.Q)
    public static String getRunningActivityName(Context context){
        ActivityManager activityManager=(ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE);
        String runningActivity=activityManager.getRunningTasks(1).get(0).topActivity.getClassName();
        return runningActivity;
    }

    /**
     * methodname:
     * 作者：Henry on 2017/3/6 8:54
     * 邮箱：xuejunju_4595@qq.com
     * 参数:设置容器第一次打开标志
     * 返回:
     */
    public static void writeFirstOpen(Context context, String activityName, boolean falg){
        SharedPreferences user = context.getSharedPreferences("user_info",0);
        SharedPreferences.Editor mydata = user.edit();
        mydata.putBoolean(activityName ,falg);
        mydata.commit();
    }


    /**
     * methodname:
     * 作者：Henry on 2017/3/6 8:54
     * 邮箱：xuejunju_4595@qq.com
     * 参数:设置对应账号的密码
     * 返回:
     */
    public static void writeUserPwd(Context context, String userName, String pwd){
        SharedPreferences user = context.getSharedPreferences("user_info",0);
        SharedPreferences.Editor mydata = user.edit();
        mydata.putString(userName ,pwd);
        mydata.commit();
    }

    /**
     * methodname:
     * 作者：Henry on 2017/3/6 8:56
     * 邮箱：xuejunju_4595@qq.com
     * 参数:读取是否第一次打开标志
     * 返回:
     */
    public static boolean readFirstOpen(Context context, String activityName) {
        SharedPreferences wode = context.getSharedPreferences("user_info",0);
        boolean flag = wode.getBoolean(activityName,false);

        return flag;
    }


    /**
     * methodname:
     * 作者：Henry on 2017/3/6 8:56
     * 邮箱：xuejunju_4595@qq.com
     * 参数:读取对应账号的密码
     * 返回:
     */
    public static String readUserPwd(Context context, String userName) {
        SharedPreferences wode = context.getSharedPreferences("user_info",0);
        String flag = wode.getString(userName,"");

        return flag;
    }

    public static List<String> decode1(List<String> mlist) {
        String temp="";
        for(int i = 0;i<mlist.size();i++){
            for(int j=i+1;j<mlist.size();j++){
                if( Integer.parseInt(mlist.get(i))> Integer.parseInt( mlist.get(j))){
                    temp = mlist.get(i);
                    mlist.set(i,mlist.get(j));
                    mlist.set(j,temp);
                }
            }
        }
        return mlist;
    }

    public static String timeDecode(String timeIn, int tag) {
        String time  ="";
        switch (tag){
            case 6:
                if(timeIn != null && timeIn.length() == tag && tag==6){
                    String d = timeIn.substring(0,2);
                    String h = timeIn.substring(2,4);
                    String m = timeIn.substring(4,6);
                    String d1 =d;
//                    if(Integer.toHexString(Integer.parseInt(h)).length()<2){
//                        h1 = "0"+Integer.toHexString(Integer.parseInt(h));
//                    }else {
//                        h1 = Integer.toHexString(Integer.parseInt(h));
//                    }
                    String h1 ="";
                    if(Integer.toHexString(Integer.parseInt(h)).length()<2){
                        h1 = "0"+ Integer.toHexString(Integer.parseInt(h));
                    }else {
                        h1 = Integer.toHexString(Integer.parseInt(h));
                    }
                    String m1 ="";
                    if(Integer.toHexString(Integer.parseInt(m)).length()<2){
                        m1 = "0"+ Integer.toHexString(Integer.parseInt(m));
                    }else {
                        m1 = Integer.toHexString(Integer.parseInt(m));
                    }
                    time = d1 +h1+m1;
                }else {
                    time ="000000";
                }
                break;
            case 4:
                if(timeIn!= null && timeIn.length() == 4 && tag ==4){
                    String m = timeIn.substring(0,2);
                    String s = timeIn.substring(2,4);
                    String m1 ="";
                    if(Integer.toHexString(Integer.parseInt(m)).length()<2){
                        m1 = "0"+ Integer.toHexString(Integer.parseInt(m));
                    }else {
                        m1 = Integer.toHexString(Integer.parseInt(m));
                    }
                    String s1 ="";
                    if(Integer.toHexString(Integer.parseInt(s)).length()<2){
                        s1 = "0"+ Integer.toHexString(Integer.parseInt(s));
                    }else {
                        s1 = Integer.toHexString(Integer.parseInt(s));
                    }
                    time= m1+s1;
                }else{
                    time = "0000";
                }
                break;
            default:
                break;
        }
        return time;
    }


    /**
     *判断当前应用程序处于前台还是后台
     */
    @RequiresApi(api = Build.VERSION_CODES.Q)
    public static boolean isApplicationBroughtToBackground(final Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> tasks = am.getRunningTasks(1);
        if (!tasks.isEmpty()) {
            ComponentName topActivity = tasks.get(0).topActivity;
            return !topActivity.getPackageName().equals(context.getPackageName());
        }
        return false;
    }

    public static void playNotifycationSound(Context context){

        try {
            if(streamID!=0){
                soundPool.stop(streamID);
                soundPool.release();
                soundPool = null;
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        soundPool = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
        final int soundID = soundPool.load(context, R.raw.phonering, 1);
        soundPool.setOnLoadCompleteListener((soundPool, sampleId, status) -> streamID = soundPool.play(soundID, 0.6f, 0.6f, 1, 0, 1));

    }

    public static void stopMusic(Context context)
            throws IOException {
        if(soundPool!=null){
            soundPool.stop(streamID);
            soundPool.release();
            soundPool = null;
        }
    }

    //获取屏幕的宽度
    public static int getScreenWidth(Context context) {
        WindowManager manager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();
        return display.getWidth();
    }


    /**
     * write ssid code
     * @param name
     * @param key
     */
    public void writeSSidcode(String name, String key){
        SharedPreferences user = context.getSharedPreferences("user_info",0);
        SharedPreferences.Editor mydata = user.edit();
        mydata.putString(name ,key);
        mydata.commit();
    }

    public String readSSidcode(String key) {
        SharedPreferences wode = context.getSharedPreferences("user_info",0);
        String psd = wode.getString(key,null);

        return psd;
    }

    /**
     * 设置语言
     * @param Language
     */
    public void writeLanguage(String Language){
        SharedPreferences user = context.getSharedPreferences("user_info",0);
        SharedPreferences.Editor mydata = user.edit();
        mydata.putString("Language" ,Language);
        mydata.commit();
    }


    public String readShareLanguage() {
        SharedPreferences wode = context.getSharedPreferences("user_info",0);
        String name = wode.getString("Language","");

        return name;
    }

    public String readLanguage() {
        Resources resource = context.getResources();
        Configuration config = resource.getConfiguration();
        Locale locale = context.getResources().getConfiguration().locale;//获得local对象

        return locale.getLanguage();
    }

    public String shiftLanguage(Context context, String sta){

        Resources resource = context.getResources();
        Configuration config = resource.getConfiguration();
        Locale locale = context.getResources().getConfiguration().locale;//获得local对象
        String lan = locale.getLanguage();

        if(TextUtils.isEmpty(sta)){
            if("zh".equals(lan)){
                config.locale = Locale.CHINA;
                writeLanguage("zh");
            }else if("fr".equals(lan)){
                config.locale = Locale.FRENCH;
                writeLanguage("fr");
            }else if("de".equals(lan)){
                config.locale = Locale.GERMANY;
                writeLanguage("de");
            }else{
                config.locale = Locale.ENGLISH;
                writeLanguage("en");
            }

            context.getResources().updateConfiguration(config, null);
            return lan;
        }else{
            if("zh".equals(sta)){
                config.locale = Locale.CHINA;
                writeLanguage("zh");
            }else if("fr".equals(sta)){
                config.locale = Locale.FRENCH;
                writeLanguage("fr");
            }else if("de".equals(sta)){
                config.locale = Locale.GERMANY;
                writeLanguage("de");
            }else{
                config.locale = Locale.ENGLISH;
                writeLanguage("en");
            }

            context.getResources().updateConfiguration(config, null);
            return sta;
        }



    }

    /**
     * get usr Login infor
     * @param name
     * @param name
     */
    public void writeUserLog(String name){
        SharedPreferences user = context.getSharedPreferences("user_info",0);
        SharedPreferences.Editor mydata = user.edit();
        mydata.putString("userlist" ,name);
        mydata.commit();
    }
    public String readUserLog() {
        SharedPreferences wode = context.getSharedPreferences("user_info",0);
        String list = wode.getString("userlist","[]");
        return list;
    }


    public static boolean isLocServiceEnable(Context context) {
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        boolean gps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        boolean network = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        return gps || network;
    }

}
