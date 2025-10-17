package com.example.dssmv.helper;

import android.content.Context;
import android.content.SharedPreferences;


public class Utils {
    public static final String ID = "INITIALS";
    public static final String NAME = "NAME";
    public static final int REQUEST_CODE_EDIT_ACTIVITY = 1;
    public static final int REQUEST_CODE_DELETE_ACTIVITY = 2;
    public static final int REQUEST_CODE_ADD_ACTIVITY = 3;
    public static final int ACTIVITY_MODE_EDITING = 2;
    public static final int ACTIVITY_MODE_NOT_EDITING = 3;

    public static final String MODE = "MODE";
    public static final String EDITED = "EDITED";
    public static final String OPERATION = "OPERATION";
    public static final String UNKNOWN_MODE = "MODO DESCONHECIDO";
    public static final String PICK = "PICK";

    private static final String WS_NAME = "v1/";

    public static final String MYSHPREFS="MySharedPreferences";

    public static final String IP1="IP1";
    public static final String IP2="IP2";
    public static final String IP3="IP3";
    public static final String IP4="IP4";


    public static String getWSAddress(Context context){
        SharedPreferences settings = context.getSharedPreferences(MYSHPREFS, Context.MODE_PRIVATE);
        int ip1 = settings.getInt(IP1, 193);
        int ip2 = settings.getInt(IP2, 136);
        int ip3 = settings.getInt(IP3, 62);
        int ip4 = settings.getInt(IP4, 24);

        return "http://"+ip1+"."+ip2+"."+ip3+"."+ip4+"/"+WS_NAME;
    } // :8585
    public static void setWSAddress(Context context, int ip1,int ip2,int ip3,int ip4 ){
        SharedPreferences settings = context.getSharedPreferences(MYSHPREFS,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt(IP1, ip1);
        editor.putInt(IP2, ip2);
        editor.putInt(IP3, ip3);
        editor.putInt(IP4, ip4);
        editor.commit();
    }
    public static int getIPNumber(Context context, String IP){
        SharedPreferences settings = context.getSharedPreferences(MYSHPREFS, Context.MODE_PRIVATE);
        return settings.getInt(IP, 0);
    }
    public static String getIPAddress(Context context){
        SharedPreferences settings = context.getSharedPreferences(MYSHPREFS, Context.MODE_PRIVATE);
        int ip1 = settings.getInt(IP1, 0);
        int ip2 = settings.getInt(IP2, 0);
        int ip3 = settings.getInt(IP3, 0);
        int ip4 = settings.getInt(IP4, 0);

        return ip1+"."+ip2+"."+ip3+"."+ip4;
    }
}
