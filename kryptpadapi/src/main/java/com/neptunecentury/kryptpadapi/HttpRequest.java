package com.neptunecentury.kryptpadapi;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
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
     * @param url
     * @return
     * @throws IOException
     */
    public static HttpRequest post(String url) throws IOException {
        // Create request instance
        HttpRequest request = new HttpRequest(url);
        // Configure for POST
        request._conn.setDoOutput(true);
        request._conn.setRequestMethod("POST");

        // Return the HttpRequest connection
        return request;
    }

    /**
     * Configures the HttpRequest to send a get request
     * @param url
     * @return
     * @throws IOException
     */
    public static HttpRequest get(String url) throws IOException {
        // Create request instance
        HttpRequest request = new HttpRequest(url);
        // Configure for GET
        request._conn.setRequestMethod("GET");

        // Return the HttpRequest connection
        return request;
    }

    /**
     * Sets the body of the request to be sent to the server
     * @param content
     * @return
     * @throws ProtocolException
     */
    public HttpRequest bodyString(String content) throws IOException {
        // Configure connection to send a post request
        _conn.setRequestProperty("Content-type", "application/x-www-form-urlencoded");
        _conn.setRequestProperty("Accept", "*/*");

        OutputStreamWriter writer = new OutputStreamWriter(_conn.getOutputStream());

        // Write the data to the stream
        writer.write(content);
        writer.flush();

        // Return the HttpRequest connection
        return this;
    }

    /**
     * Adds an authorization header to the request
     * @param value
     * @return
     */
    public HttpRequest authorizeBearer(String value){
        // Set the authorization header
        _conn.setRequestProperty("Authorization", "BEARER " + value);

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
