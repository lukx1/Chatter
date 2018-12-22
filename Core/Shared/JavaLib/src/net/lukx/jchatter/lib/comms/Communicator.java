package net.lukx.jchatter.lib.comms;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.istack.internal.NotNull;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.DateFormat;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Communicator {

    private Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
            .create();

    private CloseableHttpClient client = HttpClients.createDefault();

    private URI serverURI;

    private URI CombineWithServerInfo(String controller, String action) throws URISyntaxException {
        return new URI(
                serverURI + "/" + controller + "/" + action
        );
    }

    private HttpUriRequest CreateRequest(String controller, String action, final HttpMethod method, Object inData) throws IOException, URISyntaxException {

        URI uri = CombineWithServerInfo(controller,action);

        RequestBuilder b ;

        switch(method){
            default:
            case GET:
                b = RequestBuilder.get(uri);
                break;
            case POST:
                b = RequestBuilder.post(uri);
                break;
            case PATCH:
                b = RequestBuilder.patch(uri);
                break;
            case PUT:
                b = RequestBuilder.put(uri);
                break;
            case DELETE:
                b = RequestBuilder.delete(uri);
                break;
        }

        if(!b.getMethod().equals("GET")) {
            b.setEntity(new StringEntity(gson.toJson(inData), ContentType.APPLICATION_JSON));
        }
        return b.build();
    }

    private <T> T ParseToJson(String data, Type type){
        return gson.fromJson(data,type);
    }

    private <T> T ParseToJson(String data, Class<T> clazz){
        return gson.fromJson(data,clazz);
    }

    private String ObtainJson(@NotNull String controller,@NotNull String action, final HttpMethod method, Object inData) throws IOException, URISyntaxException {
        CloseableHttpResponse resp = ExecuteMessage(controller,action,method,inData);
        int statusCode = resp.getStatusLine().getStatusCode();
        if(statusCode >= 200 && statusCode < 300) {
            HttpEntity entity = resp.getEntity();
            String json = entity != null ? EntityUtils.toString(entity, "UTF-8") : null;
            resp.close();
            return json;
        }
        else {
            throw new ClientProtocolException("Error status received :"+statusCode);
        }
    }

    private CloseableHttpResponse ExecuteMessage(@NotNull String controller,@NotNull String action, final HttpMethod method, Object inData) throws IOException, URISyntaxException {
        HttpUriRequest req = CreateRequest(controller,action,method,inData);
        CloseableHttpResponse response = client.execute(req);
        return response;
    }

    public <T> T Obtain(@NotNull String controller, @NotNull String action, final HttpMethod method, final Object inData, Type type) throws IOException, URISyntaxException {
        return ParseToJson(ObtainJson(controller,action,method,inData),type);
    }


    public <T> T Obtain(@NotNull String controller, @NotNull String action, final HttpMethod method, final Object inData, Class<T> clazz) throws IOException, URISyntaxException {
        if(clazz == Void.class){
            ExecuteMessage(controller,action,method,inData);
            return null;
        }
        return ParseToJson(ObtainJson(controller,action,method,inData),clazz);
    }

    public void Close() throws IOException {
        client.close();
    }

    public Communicator() {

    }

    public URI getServerURI() {
        return serverURI;
    }

    /***
     * Sets server URL
     * Do not add a trailing slash
     * @param serverURI to set
     */
    public void setServerURI(URI serverURI) {
        this.serverURI = serverURI;
    }

    public enum HttpMethod {
        GET, POST, PATCH, PUT, DELETE
    }

}
