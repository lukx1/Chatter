package net.lukx.jchatter.lib.comms;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.istack.internal.NotNull;
import net.lukx.jchatter.lib.PublicApi;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Core class for sending http requests and
 * reading responses from the server
 */
@PublicApi
public class Communicator implements Communicable {

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

    private HttpUriRequest CreateRequest(String controller, String action, final HttpMethod method, Object inData) throws URISyntaxException {

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

    private <T> T ParseToJson(String data, Class<T> clazz)
    {
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
            throw new ClientProtocolException("Error status received :"+statusCode+"\r\n"+EntityUtils.toString(resp.getEntity(), "UTF-8"));
        }
    }

    private CloseableHttpResponse ExecuteMessage(@NotNull String controller,@NotNull String action, final HttpMethod method, Object inData) throws IOException, URISyntaxException {
        HttpUriRequest req = CreateRequest(controller,action,method,inData);
        return client.execute(req);
    }

    /**
     * Gets object from server
     * @param controller at the server
     * @param action at the controller
     * @param method http method
     * @param inData data sent to the server
     * @param type of data received from server to deserialize
     * @param <T> must be same as type
     * @return object obtained from server or null if no object is received
     * @throws IOException if an {@link IOException} occurs
     * @throws URISyntaxException if URI is incorrectly set in {@link #setServerURI(URI)}
     */
    @Override
    @PublicApi
    public <T> T Obtain(@NotNull String controller, @NotNull String action, final HttpMethod method, final Object inData, Type type) throws IOException, URISyntaxException {
        return ParseToJson(ObtainJson(controller,action,method,inData),type);
    }

    /**
     * Gets object from server
     * @param controller at the server
     * @param action at the controller
     * @param method http method
     * @param inData data sent to the server
     * @param clazz class type of data recived from server. Can be void if no data is expected
     * @param <T> must be same as clazz
     * @return object obtained from server or null if no object is received
     * @throws IOException if an {@link IOException} occurs
     * @throws URISyntaxException if URI is incorrectly set in {@link #setServerURI(URI)}
     */
    @Override
    @PublicApi
    public <T> T Obtain(@NotNull String controller, @NotNull String action, final HttpMethod method, final Object inData, Class<T> clazz) throws IOException, URISyntaxException {
        if(clazz == Void.class){
            CloseableHttpResponse resp = ExecuteMessage(controller,action,method,inData);
            if(resp.getStatusLine().getStatusCode() > 300){
                throw new ClientProtocolException("Error status received :"+resp.getStatusLine().getStatusCode()+"\r\n"+EntityUtils.toString(resp.getEntity(), "UTF-8"));
            }
            return null;
        }
        return ParseToJson(ObtainJson(controller,action,method,inData),clazz);
    }

    /**
     * Closes httpclient
     * @throws IOException if an exception occours
     */
    @Override
    @PublicApi
    public void Close() throws IOException {
        client.close();
    }

    /**
     * Creates a thread safe instance of communicator
     */
    @PublicApi
    public Communicator() {

    }

    /**
     * @return currently set URI
     */
    @Override
    @PublicApi
    public URI getServerURI() {
        return serverURI;
    }

    /***
     * Sets server URL
     * Do not add a trailing slash
     * @param serverURI to set
     */
    @Override
    @PublicApi
    public void setServerURI(URI serverURI) {
        this.serverURI = serverURI;
    }


}
