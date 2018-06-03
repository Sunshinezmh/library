package com.dmsdbj.library.app.util;


//import org.json.JSONObject;

import net.sf.json.JSONObject;
import net.sf.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;


public class HttpRequestUtil {
//    public static JSONObject doGet(String url)
//    {
////        JSONObject demoJson =new JSONObject();
////        JSONObject demoJson = null;
//        try {
//            URL urlGet = new URL(url);
//            HttpURLConnection http = (HttpURLConnection) urlGet.openConnection();
//            http.setRequestMethod("GET"); // 必须是get方式请求
//            http.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
//            http.setDoOutput(true);
//            http.setDoInput(true);
//            System.setProperty("sun.net.client.defaultConnectTimeout", "30000");// 连接超时30秒
//            System.setProperty("sun.net.client.defaultReadTimeout", "30000"); // 读取超时30秒
//            http.connect();
//            InputStream is = http.getInputStream();
//            int size = is.available();
//            byte[] jsonBytes = new byte[size];
//            is.read(jsonBytes);
//            String message = new String(jsonBytes, "UTF-8");
////            JSONObject demoJson = new JSONObject(message);
//            JSONObject demoJson = JSONObject.fromObject(message);
//            is.close();
//            return  demoJson;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }

    public static String sendGetRequest(String getUrl)
    {
        StringBuffer sb = new StringBuffer();
        InputStreamReader isr = null;
        BufferedReader br = null;
        try
        {
            URL url = new URL(getUrl);
            URLConnection urlConnection = url.openConnection();
            urlConnection.setAllowUserInteraction(false);
            isr = new InputStreamReader(url.openStream());
            br = new BufferedReader(isr);
            String line;
            while ((line = br.readLine()) != null)
            {
                sb.append(line);
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return sb.toString();
    }
}