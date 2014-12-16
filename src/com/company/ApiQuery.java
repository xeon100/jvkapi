package com.company;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

/**
 * Created by Xeon on 11.12.2014.
 */
public class ApiQuery {
    private URL query;
    private String apiRoot;
    private String apiMethodName;
    private String apiApplicationName;
    private HashMap<String, Object> parameters;
    private String response;

    private StringBuilder queryBuilder;

    public ApiQuery(String apiRoot, String apiApplicationName, String apiMethodName,
             HashMap<String, Object> parameters) {
        this.apiRoot = apiRoot;
        this.apiApplicationName = apiApplicationName;
        this.apiMethodName = apiMethodName;
        this.parameters = parameters;

        queryBuilder = new StringBuilder();
        queryBuilder.append(this.apiRoot+'/'
                +this.apiApplicationName+'/'+apiMethodName+'?');

        String prefix = "";
        for (String parameterKey : parameters.keySet()) {
            queryBuilder.append(prefix+parameterKey+"="+parameters.get(parameterKey));
            prefix = "&";
        }

        try {
            query = new URL(queryBuilder.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

    }

    public void connect() throws InterruptedException {

        try {
            StringBuilder sb = new StringBuilder();
            HttpsURLConnection httpsURLConnection = (HttpsURLConnection) query.openConnection();
            BufferedReader reader = new BufferedReader(new InputStreamReader(httpsURLConnection.getInputStream()));
            while(reader.ready())
                sb.append(reader.readLine());
            response = sb.toString();
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getApiApplicationName() {
        return apiApplicationName;
    }

    public URL getQuery() {
        return query;
    }

    public String getApiRoot() {
        return apiRoot;
    }

    public String getApiMethodName() {
        return apiMethodName;
    }

    public HashMap<String, Object> getParameters() {
        return parameters;
    }

    public String getResponse() {
        return response;
    }

}
