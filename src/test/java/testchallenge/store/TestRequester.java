package testchallenge.store;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.*;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONException;
import org.json.JSONObject;
import org.skyscreamer.jsonassert.JSONParser;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Objects;


public class TestRequester {

    public Object runGet(String url)  {
        HttpRequestBase request =new HttpGet(url);
        request.addHeader("content-type", "application/json");
        String result = runRequest(request);
        if(Objects.nonNull(result) && !result.isEmpty()){
            try {
                return JSONParser.parseJSON(result);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return new JSONObject();
    }

    public Object runDelete(String url) {
        HttpRequestBase request =new HttpDelete(url);
        request.addHeader("content-type", "application/json");
        String result = runRequest(request);
        if(Objects.nonNull(result) && !result.isEmpty()){
            try {
                return JSONParser.parseJSON(result);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return new JSONObject();
    }

    public Object runPut(String url, StringEntity params) {
        HttpEntityEnclosingRequestBase request =new HttpPut(url);
        request.addHeader("content-type", "application/json");
        request.setEntity(params);
        String result = runRequest(request);
        if(Objects.nonNull(result) && !result.isEmpty()){
            try {
                return JSONParser.parseJSON(result);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return new JSONObject();
    }

    public Object runPost(String url, StringEntity params) {
        HttpEntityEnclosingRequestBase request =new HttpPost(url);
        request.addHeader("content-type", "application/json");
        request.setEntity(params);
        String result = runRequest(request);
        if(Objects.nonNull(result) && !result.isEmpty()){
            try {
                return JSONParser.parseJSON(result);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return new JSONObject();
    }

    private String runRequest(HttpRequestBase request) {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        try {
            HttpResponse response = httpClient.execute(request);

            StringBuffer buff = new StringBuffer();
            if (response != null) {
                InputStream ins = response.getEntity().getContent(); //Get the data in the entity
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(ins));
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    buff.append(inputLine);
                }
                in.close();
            }
            //System.out.println(response.toString());
            System.out.println("RESPONSE: " + buff.toString());

            return buff.toString();

        } catch (Exception ex) {
            // handle exception here
            ex.printStackTrace();
        } finally {
            try {
                httpClient.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        return null;
    }
}
