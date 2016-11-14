package com.neptunecentury.kryptpadapi;

import android.os.AsyncTask;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.lang.reflect.Field;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.StringJoiner;


/**
 * Created by Eric Butler on 11/9/2016.
 */
public abstract class KryptPadApi {

    //private static final String HOST = "http://test.kryptpad.com/";
    private static final String HOST = "http://localhost:50821/";

    public static class AuthenticateAsync extends AsyncTask<Void, Void, String> {
        /**
         * Stores the anonymous class to call when the api method is complete
         */
        private AsyncTaskComplete _complete;

        public AuthenticateAsync(AsyncTaskComplete complete) {
            _complete = complete;
        }

        @Override
        protected String doInBackground(Void... params) {


            HttpURLConnection conn = null;

            try {
                URL url = new URL(HOST + "token");
                conn = (HttpURLConnection) url.openConnection();

                ApiCredentials creds = new ApiCredentials();
                creds.email = "test@test.com";
                creds.password = "Abcd!234";
                creds.grant_type = "password";
                creds.clientId = "KryptPadAndroid";

                //Gson gson = new Gson();
                //String data = gson.toJson(creds);
                String data = formUrlEncodeString(creds);

                // Prepare post
                conn.setRequestMethod("POST");
                conn.setDoOutput(true);
                conn.setRequestProperty("Content-type", "application/x-www-form-urlencoded");
                conn.setRequestProperty("Accept", "*/*");

                OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream());

                // Write the data to the stream
                writer.write("clientId=KryptPadAndroid&email=test@test.com&grant_type=password&password=Abcd!234");
                writer.flush();


                InputStream response = conn.getInputStream();

                if (conn.getResponseCode() == 404) {

                }

            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            } finally {
                if (conn != null) {
                    conn.disconnect();
                }
            }

            return "1234";
        }

        @Override
        protected void onPostExecute(final String data) {
            // Call the AsyncTaskComplete class we have stored
            if (_complete != null) {
                _complete.complete(data, null);
            }
        }
    }

    /**
     * Helper methods
     */
    public static String formUrlEncodeString(Object obj){
        ArrayList<String> values = new ArrayList<String>();

        for (Field field : obj.getClass().getDeclaredFields()) {
            //field.setAccessible(true); // You might want to set modifier to public first.
            Object value = null;

            // Attempt to get the value of the field
            try {
                value = field.get(obj);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

            // If we have a field, add this to the string
            if (value != null) {
                values.add(field.getName() + "=" + value);
            }
        }

        String output = "";
        for (String s : values){
            String delimiter = "&";
            if (values.indexOf(s) == 0){
               delimiter  = "";
            }

            output += delimiter + s;
        }

        return output;

    }
}