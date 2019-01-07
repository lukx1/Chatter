package night.legacy.org.chatterandroid;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import net.lukx.jchatter.lib.comms.Communicable;
import net.lukx.jchatter.lib.comms.Communicator;
import net.lukx.jchatter.lib.comms.HttpMethod;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.URISyntaxException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class AndroidConnection implements Communicable {

    public static final MediaType JSON = MediaType.get("application/json; charset=utf-8");

    private Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
            .create();

    private OkHttpClient client = new OkHttpClient();

    private URI ServerURI;

    private String CombineWithServerInfo(String controller, String action) throws URISyntaxException {

        return ServerURI + "/" + controller + "/" + action;
    }



    private Request getRequest(String url, String json,HttpMethod method)
    {
        RequestBody body = RequestBody.create(JSON, json);
        Request b = null;

        switch(method){
            case POST:
                b = new Request.Builder()
                        .url(url)
                        .post(body)
                        .build();
                break;
            case GET:
                b = new Request.Builder()
                        .url(url)
                        .get()
                        .build();
                break;
            case PUT:
                b = new Request.Builder()
                        .url(url)
                        .put(body)
                        .build();
                break;
            case PATCH:
                b = new Request.Builder()
                        .url(url)
                        .patch(body)
                        .build();
                break;
            case DELETE:
                b = new Request.Builder()
                        .url(url)
                        .delete()
                        .build();
                break;
            default:
                b = null;
                break;
        }
        return  b;
    }

    private String ObtainJson(String controller,String action, final HttpMethod method, Object inData) throws Exception {
        Response response = ExecuteMessage(controller,action,method,inData);

        if(response.isSuccessful()) {
            return response.body().string();
        }
        else {
            throw new Exception("Error status received :"+response.code());
        }
    }

    private Response ExecuteMessage(String controller,String action, final HttpMethod method, Object inData) throws IOException, URISyntaxException {
        String json = gson.toJson(inData);
        Request req = getRequest(CombineWithServerInfo(controller,action),gson.toJson(inData),method);
        return client.newCall(req).execute();
    }

    private <T> T ParseToJson(String data, Type type){
        return gson.fromJson(data,type);
    }

    private <T> T ParseToJson(String data, Class<T> clazz){
        return gson.fromJson(data,clazz);
    }

    @Override
    public <T> T Obtain(String controller, String action, HttpMethod httpMethod, Object o, Type type) throws IOException, URISyntaxException {
        try {
            return ParseToJson(ObtainJson(controller,action,httpMethod,o),type);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    @Override
    public <T> T Obtain(String controller, String action, HttpMethod method, Object inData, Class<T> clazz) throws IOException, URISyntaxException {
        if(clazz == Void.class){
            ExecuteMessage(controller,action,method,inData);
            return null;
        }
        try {
            return ParseToJson(ObtainJson(controller,action,method,inData),clazz);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void Close() throws IOException {

    }

    @Override
    public URI getServerURI() {
        return ServerURI;
    }

    @Override
    public void setServerURI(URI uri) {
        ServerURI = uri;
    }
}
