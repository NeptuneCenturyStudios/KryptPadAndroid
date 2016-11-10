package com.neptunecentury.kryptpadapi;

import android.os.AsyncTask;


/**
 * Created by Eric Butler on 11/9/2016.
 */
public abstract class KryptPadApi{

    public static void Authenticate(String email, String password){

    }

    public static class AuthenticateAsync extends AsyncTask<Void, Void, String> {
        /**
         * Stores the anonymous class to call when the api method is complete
         */
        private AsyncTaskComplete _complete;

        public AuthenticateAsync(AsyncTaskComplete complete){
            _complete = complete;
        }

        @Override
        protected String doInBackground(Void... params){
            return "1234";
        }

        @Override
        protected void onPostExecute(final String data) {
            // Call the AsyncTaskComplete class we have stored
            if (_complete != null){
                _complete.complete(data, null);
            }
        }
    }


}