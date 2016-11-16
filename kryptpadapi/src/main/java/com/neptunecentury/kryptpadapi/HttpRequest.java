package com.neptunecentury.kryptpadapi;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

/**
 * HttpRequest is a fluent-api style library used to access http resources
 */

public class HttpRequest {

    private HttpURLConnection _conn = null;
    private HttpResponse _response = null;

    /**
     * Builds an HttpRequest object
     * @param url
     * @throws IOException
     */
    private HttpRequest(String url) throws IOException {
        // Create and open a connection to the urk
        URL requestUrl = new URL(url);
        // Open connection
        _conn = (HttpURLConnection) requestUrl.openConnection();
    }

    /**
     * Configures the HttpRequest to send a post request
     * @param content
     * @return
     * @throws IOException
     */
    public static HttpRequest post(String url, String content) throws IOException {
        // Create request instance
        HttpRequest request = new HttpRequest(url);

        // Return the HttpRequest connection
        return request;
    }

    /**
     * Sets the body of the request to be sent to the server
     * @param content
     * @return
     * @throws ProtocolException
     */
    public HttpRequest bodyString(String content) throws ProtocolException {
        // Configure connection to send a post request
        _conn.setDoOutput(true);
        _conn.setRequestMethod("POST");
        _conn.setRequestProperty("Content-type", "application/x-www-form-urlencoded");
        _conn.setRequestProperty("Accept", "*/*");

        // Return the HttpRequest connection
        return this;
    }

    /**
     * Sends the request and gets the response
     * @return
     */
    public HttpRequest execute(){
        // Execute the request and get the response
        _response = new HttpResponse(_conn);

        return this;
    }

    /**
     * Returns the response from the server
     * @return
     * @throws IOException
     */
    public HttpResponse returnContent() throws IOException {
        // Error, cannot return content until request is executed
        if (_response == null){
            throw new IOException("Cannot return content until request has been executed.");
        }

        return _response;
    }

}
