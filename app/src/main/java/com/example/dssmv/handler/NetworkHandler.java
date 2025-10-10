package com.example.dssmv.handler;

import android.content.Context;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;



public class NetworkHandler {
    public static String getDataInStringFromUrl(String url) throws IOException {
        InputStream is = NetworkHandler.openGetHttpConnection(url);
        String data ="";
        if(is != null) {
            data = NetworkHandler.readString(is);
        }
        return data;
    }

    public static String addDataInStringFromUrl(String url, String body) throws IOException {
        InputStream is = NetworkHandler.openPostHttpConnection(url, body);
        String data ="";
        if(is != null) {
            data = NetworkHandler.readString(is);
        }
        return data;
    }

    public static String updateDataInStringFromUrl(String url, String body) throws IOException {
        InputStream is = NetworkHandler.openPutHttpConnection(url, body);
        String data ="";
        if(is != null) {
            data = NetworkHandler.readString(is);
        }
        return data;
    }

    public static boolean deleteDataInStringFromUrl(String url){
        NetworkHandler.openDeleteHttpConnection(url);
        return true;
    }

    public static InputStream openGetHttpConnection(String urlStr) {
        InputStream in = null;
        HttpURLConnection httpConn = null;
        int resCode = -1;
        try {
            URL url = new URL(urlStr);
            URLConnection urlConn = url.openConnection();

            if (!(urlConn instanceof HttpURLConnection)) {
                throw new IOException("URL is not an Http URL");
            }

            httpConn = (HttpURLConnection) urlConn;
            httpConn.setConnectTimeout(3000);
            httpConn.setRequestMethod("GET");
            httpConn.setDoInput(true);
            httpConn.connect();
            resCode = httpConn.getResponseCode();
            if (resCode == HttpURLConnection.HTTP_OK) {
                in = httpConn.getInputStream();
            }
        }catch (MalformedURLException e) {
            e.printStackTrace();
        }catch (IOException e) {
            e.printStackTrace();
        }
        return in;
    }

    public static InputStream openPostHttpConnection(String urlStr, String body) {
        InputStream in = null;
        HttpURLConnection httpConn = null;
        int resCode = -1;
        try {
            URL url = new URL(urlStr);
            URLConnection urlConn = url.openConnection();

            if (!(urlConn instanceof HttpURLConnection)) {
                throw new IOException("URL is not an Http URL");
            }

            httpConn = (HttpURLConnection) urlConn;
            httpConn.setConnectTimeout(3000);
            httpConn.setRequestMethod("POST");
            httpConn.setRequestProperty("Content-Type", "application/json");
            httpConn.setDoOutput(true);
            writeBody(httpConn.getOutputStream(), body);
            httpConn.connect();
            resCode = httpConn.getResponseCode();
            if (resCode == HttpURLConnection.HTTP_OK) {
                in = httpConn.getInputStream();
            }else{
                throw new RuntimeException("rescode not 200. VALUE:"+resCode + ". URL:" + urlStr +". With body:"+body);
            }
        }catch (MalformedURLException e) {
            e.printStackTrace();
        }catch (IOException e) {
            e.printStackTrace();
        }
        return in;
    }

    public static InputStream openPutHttpConnection(String urlStr, String body) {
        InputStream in = null;
        HttpURLConnection httpConn = null;
        int resCode = -1;
        try {
            URL url = new URL(urlStr);
            URLConnection urlConn = url.openConnection();

            if (!(urlConn instanceof HttpURLConnection)) {
                throw new IOException("URL is not an Http URL");
            }

            httpConn = (HttpURLConnection) urlConn;
            httpConn.setConnectTimeout(3000);
            httpConn.setRequestMethod("PUT");
            httpConn.setRequestProperty("Content-Type", "application/json");
            httpConn.setDoOutput(true);
            writeBody(httpConn.getOutputStream(), body);
            httpConn.connect();
            resCode = httpConn.getResponseCode();
            if (resCode == HttpURLConnection.HTTP_OK) {
                in = httpConn.getInputStream();
            }else{
                throw new RuntimeException("rescode not 200. VALUE:"+resCode + ". URL:" + urlStr +". With body:"+body);
            }
        }catch (MalformedURLException e) {
            e.printStackTrace();
        }catch (IOException e) {
            e.printStackTrace();
        }
        return in;
    }

    private static void writeBody(OutputStream writer, String body){
        try {
            byte[] dataBytes = body.getBytes("UTF-8");
            writer.write(dataBytes);
            writer.flush();
            writer.close();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static InputStream openDeleteHttpConnection(String urlStr) {
        InputStream in = null;
        HttpURLConnection httpConn = null;
        int resCode = -1;
        try {
            URL url = new URL(urlStr);
            URLConnection urlConn = url.openConnection();

            if (!(urlConn instanceof HttpURLConnection)) {
                throw new IOException("URL is not an Http URL");
            }

            httpConn = (HttpURLConnection) urlConn;
            httpConn.setConnectTimeout(3000);
            httpConn.setRequestMethod("DELETE");
            httpConn.setDoInput(true);
            httpConn.connect();
            resCode = httpConn.getResponseCode();
            if (resCode == HttpURLConnection.HTTP_ACCEPTED) {
                in = httpConn.getInputStream();
            }else{
                throw new RuntimeException("rescode not 200. VALUE:"+resCode);
            }
        }catch (MalformedURLException e) {
            e.printStackTrace();
        }catch (IOException e) {
            e.printStackTrace();
        }
        return in;
    }

    public static String readString(InputStream is) throws IOException {
        char[] buf = new char[2048];
        Reader r = new InputStreamReader(is, "UTF-8");
        StringBuilder s = new StringBuilder();
        while (true) {
            int n = r.read(buf);
            if (n < 0)
                break;
            s.append(buf, 0, n);
        }
        return s.toString();
    }

    public static byte[] getDataInBytesFromUrl(String urlString) throws IOException {
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;

        try {
            URL url = new URL(urlString);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setDoInput(true);
            urlConnection.connect();

            int responseCode = urlConnection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                inputStream = urlConnection.getInputStream();
                return readInputStreamToByteArray(inputStream);
            } else {
                throw new IOException("HTTP error code: " + responseCode);
            }
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
    }

    private static byte[] readInputStreamToByteArray(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int length;

        while ((length = inputStream.read(buffer)) != -1) {
            byteArrayOutputStream.write(buffer, 0, length);
        }
        return byteArrayOutputStream.toByteArray();
    }
    public Context context;

    public NetworkHandler(Context context) {
        this.context = context;
    }

    public interface Callback {
        void onSuccess(String response);
        void onError(Exception e);
    }

    public void deleteDataFromUrl(String url, Callback callback) {
        new Thread(() -> {
            try {
                // Configurar a conexão para DELETE
                URL apiUrl = new URL(url);
                HttpURLConnection connection = (HttpURLConnection) apiUrl.openConnection();
                connection.setRequestMethod("DELETE");
                connection.setConnectTimeout(5000); // Tempo limite de conexão
                connection.setReadTimeout(5000); // Tempo limite de leitura

                int responseCode = connection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {

                    BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = in.readLine()) != null) {
                        response.append(line);
                    }
                    in.close();
                    callback.onSuccess(response.toString());
                } else {
                    callback.onError(new Exception("Failed to delete. Response code: " + responseCode));
                }
                connection.disconnect();
            } catch (Exception e) {
                callback.onError(e);
            }
        }).start();
    }

}
