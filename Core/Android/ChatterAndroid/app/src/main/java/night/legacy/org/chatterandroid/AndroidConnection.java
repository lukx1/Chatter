package night.legacy.org.chatterandroid;

import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import net.lukx.jchatter.lib.comms.Communicable;
import net.lukx.jchatter.lib.comms.Communicator;
import net.lukx.jchatter.lib.comms.HttpMethod;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.ExecutionException;

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

    private String ObtainJson(String controller,String action, final HttpMethod method, String inData) throws Exception {
        String response = ExecuteMessage(controller,action,method,inData);
        String resp = response;
        if(resp == null || resp.length() == 0)
            throw new Exception("Response empty");
        return resp;
    }

    private String ExecuteMessage(String controller,String action, final HttpMethod method, String inData) throws IOException, URISyntaxException, ExecutionException, InterruptedException {

        Request req = getRequest(CombineWithServerInfo(controller,action),inData,method);
        String result = new ExecuteConnection().execute(req).get();
        return result;
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
            String json = gson.toJson(o);
            return ParseToJson(ObtainJson(controller,action,httpMethod,json),type);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    @Override
    public <T> T Obtain(String controller, String action, HttpMethod method, Object inData, Class<T> clazz) throws IOException, URISyntaxException {
        if(clazz == Void.class){
            String json = gson.toJson(inData);
            try {
                ExecuteMessage(controller,action,method,json);
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }
        try {
            String json = gson.toJson(inData);
            return ParseToJson(ObtainJson(controller,action,method,json),clazz);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void Close() throws IOException {
        //client.connectionPool().evictAll();
    }

    @Override
    public URI getServerURI() {
        return ServerURI;
    }

    @Override
    public void setServerURI(URI uri) {
        ServerURI = uri;
    }

    class ExecuteConnection extends AsyncTask<Request,Void,String>
    {
        @Override
        protected String doInBackground(Request... requests) {
            try {
                return client.newCall(requests[0]).execute().body().string();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
