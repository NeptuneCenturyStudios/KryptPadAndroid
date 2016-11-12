package com.neptunecentury.kryptpadapi;

import android.os.AsyncTask;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;


/**
 * Created by Eric Butler on 11/9/2016.
 */
public abstract class KryptPadApi {

    private static final String HOST = "http://test.kryptpad.com/";

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

            try {
                URL url = new URL(HOST + "api/account/tes3t");
                HttpURLConnection conn = (HttpURLConnection)url.openConnection();

                ApiCredentials creds = new ApiCredentials();
                creds.email = "test@test.com";
                creds.password = "Abcd!234";
                creds.clientId = "KryptPadAndroid";

                Gson gson = new Gson();
                String data = gson.toJson(creds);

                InputStream response = conn.getInputStream();

                if (conn.getResponseCode() == 404) {

                }

            } catch (Exception ex) {
                System.out.println(ex.getMessage());
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


}