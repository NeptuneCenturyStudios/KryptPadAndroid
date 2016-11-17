package com.neptunecentury.kryptpadapi;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;


/**
 * Created by Eric on 11/16/2016.
 */

public class HttpResponse {

    private HttpURLConnection _conn = null;
    private InputStream _response = null;
    private int _responseCode;

    /**
     * Gets the response from the server
     * @param conn
     */
    public HttpResponse(HttpURLConnection conn){
        _conn = conn;

        // Execute the request and get the response
        try {
            // Send request and get response stream
            _response = conn.getInputStream();
        } catch (IOException e) {
            // If an error occurs, get the error stream
            _response = conn.getErrorStream();
        }

        // Get the status code
        try {
            _responseCode = conn.getResponseCode();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Read the response as a string
     * @return
     */
    public String asString(){

        String content = null;

        // Process the input stream and return it as a string
        try {

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int length = 0;

            // Read the stream
            while ((length = _response.read(buffer)) != -1) {
                baos.write(buffer, 0, length);
            }

            // Return content as string
            content = baos.toString("UTF-8");

        } catch (IOException e) {
            e.printStackTrace();
        }

        // Disconnect
        _conn.disconnect();

        return content;
    }

}
