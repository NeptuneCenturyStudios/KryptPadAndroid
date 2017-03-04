package com.neptunecentury.kryptpadapi;

import android.os.AsyncTask;

import com.google.gson.Gson;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;

/**
 * Provides methods for communicating with the Krypt Pad api.
 */
public abstract class KryptPadApi {

    private static final String HOST = "http://test.kryptpad.com/";
    //private static final String HOST = "http://10.0.2.2:50821/";

    private static TokenResponse _token = null;

    /**
     * Gets the token response object
     * @return TokenResponse
     */
    public static TokenResponse getTokenResponse() {
        return _token;
    }

    /**
     * Sets the token response object
     * @param value
     */
    private static void setTokenResponse(TokenResponse value) {
        _token = value;
    }

    /**
     * Calls the authentication endpoint to verify user credentials
     */
    public static class AuthenticateAsync extends AsyncTask<Void, Void, Boolean> {
        // Stores the anonymous class that will be called when the async task is complete
        private final AsyncTaskComplete _complete;
        private String _username;
        private String _password;

        public AuthenticateAsync(String username, String password, AsyncTaskComplete complete) {
            _complete = complete;
            _username = username;
            _password = password;
        }

        @Override
        protected Boolean doInBackground(Void... params) {

            try {

                // Create object to send to server
                ApiCredentials creds = new ApiCredentials();
                creds.username = _username;
                creds.password = _password;
                creds.grant_type = "password";
                creds.client_id = "KryptPadAndroid";

                // Form encode the object
                String data = formUrlEncodeString(creds);

                // Send request to server
                String response = HttpRequest.post(HOST + "token")
                        .bodyString(data)
                        .execute()
                        .returnContent()
                        .asString();

                // Parse response
                Gson gson = new Gson();
                TokenResponse token = gson.fromJson(response, TokenResponse.class);

                // Set global token object for future requests
                setTokenResponse(token);

                return true;

            } catch (Exception e) {
                e.printStackTrace();
                // TODO: Handle exceptions
            }

            return false;
        }

        @Override
        protected void onPostExecute(final Boolean data) {
            // Call the AsyncTaskComplete class we have stored
            if (_complete != null) {
                _complete.complete(data, null);
            }
        }

    }

    /**
     * Gets the profiles of an account
     */
    public static class GetProfilesAsync extends  AsyncTask<Void, Void, ApiProfileResult>{
        // Stores the anonymous class that will be called when the async task is complete
        private final AsyncTaskComplete _complete;

        public GetProfilesAsync(AsyncTaskComplete complete) {
            _complete = complete;
        }

        @Override
        protected ApiProfileResult doInBackground(Void... params) {
            try {

                // Get the profiles
                String response = HttpRequest.get(HOST + "/api/profiles")
                        .authorizeBearer(_token.access_token)
                        .execute()
                        .returnContent()
                        .asString();

                // Parse response
                Gson gson = new Gson();
                ApiProfileResult result = gson.fromJson(response, ApiProfileResult.class);

                // Return the result we got from the api
                return result;

            } catch (IOException e) {
                e.printStackTrace();
                // TODO: Handle exceptions

            }

            return null;
        }

        @Override
        protected void onPostExecute(final ApiProfileResult data) {
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