package com.neptunecentury.kryptpadapi;

import android.os.AsyncTask;

import com.google.gson.Gson;

import java.lang.reflect.Field;
import java.util.ArrayList;


/**
 * Created by Eric Butler on 11/9/2016.
 */
public abstract class KryptPadApi {

    private static final String HOST = "http://test.kryptpad.com/";
    //private static final String HOST = "http://10.0.2.2:50821/";

    public static class AuthenticateAsync extends AsyncTask<Void, Void, String> {
        /**
         * Stores the anonymous class to call when the api method is complete
         */
        private AsyncTaskComplete _complete;
        private String _username;
        private String _password;

        public AuthenticateAsync(String username, String password, AsyncTaskComplete complete) {
            _complete = complete;
            _username = username;
            _password = password;
        }

        @Override
        protected String doInBackground(Void... params) {

            try {

                ApiCredentials creds = new ApiCredentials();
                creds.username = _username;
                creds.password = _password;
                creds.grant_type = "password";
                creds.client_id = "KryptPadAndroid";

                //Gson gson = new Gson();
                //String data = gson.toJson(creds);
                String data = formUrlEncodeString(creds);

                // Write the data to the stream
                //writer.write("client_id=KryptPadAndroid&username=test@test.com&grant_type=password&password=Abcd!234");
                String response = HttpRequest.post(HOST + "token")
                        .bodyString(data)
                        .execute()
                        .returnContent()
                        .asString();

                System.out.println(response);

                // Parse response
                Gson gson = new Gson();
                TokenResponse token = gson.fromJson(response, TokenResponse.class);


                response = HttpRequest.get(HOST + "api/profiles")
                        .authorizeBearer(token.access_token)
                        .execute()
                        .returnContent()
                        .asString();

                System.out.println(response);

            } catch (Exception ex) {

            } finally {

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
    public static String formUrlEncodeString(Object obj) {
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
        for (String s : values) {
            String delimiter = "&";
            if (values.indexOf(s) == 0) {
                delimiter = "";
            }

            output += delimiter + s;
        }

        return output;

    }
}