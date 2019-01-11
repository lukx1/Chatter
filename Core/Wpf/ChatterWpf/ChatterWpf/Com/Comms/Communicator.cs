using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Net;

//HELP

[PublicApi]
public class Communicator : ICommunicable
{

    //Json + Jsonbuilder ....new IsoDateTimeConverter()

    private HttpClient client = new DefaultHttpClient();
    //private CloseableHttpClient client = HttpClients.createDefault();

    private URI serverURI;

    private URI CombineWithServerInfo(string controller, string action)
    {
        return new URI(
                serverURI + "/" + controller + "/" + action
        );
    }


    //WebRequest?
    //private HttpUriRequest CreateRequest(String controller, String action, final HttpMethod method, Object inData)


        //
    private  T ParseToJson<T>(string data, Type type)
    {
        return gson.fromJson(data, type);
    }


    //Class<T> clazz
    private T ParseToJson<T>(string data, Class<T> clazz)
    {
        return gson.fromJson(data, clazz);
    }

    //final HttpMethod method readonly?const?
    private string ObtainJson(string controller, string action, HttpMethod method, Object inData)
    {
        CloseableHttpResponse resp = ExecuteMessage(controller, action, method, inData);
        int statusCode = resp.getStatusLine().getStatusCode();
        if(statusCode >= 200 && statusCode< 300) {
        HttpEntity entity = resp.getEntity();
        string json = entity != null ? EntityUtils.toString(entity, "UTF-8") : null;
        resp.close();
            return json;
        }
        else {
            throw new ClientProtocolException("Error status received :"+statusCode);
        }
    }

    //final HttpMethod method ... HttpUriRequest WebRequest?
    private CloseableHttpResponse ExecuteMessage(string controller, string action, HttpMethod method, Object inData)
    {
        WebRequest req = CreateRequest(controller, action, method, inData);
        return client.execute(req);
    }

    //final Httpmethod, Object
    public override T Obtain<T>(string controller, string action, HttpMethod method, Object inData, Type type)
    {
        return ParseToJson(ObtainJson(controller, action, method, inData), type);
    }


    //final Httpmethod,Object
    public override  T Obtain<T>(string controller, string action, HttpMethod method, Object inData, Type type)
    {
        return ParseToJson(ObtainJson(controller, action, method, inData), type);
    }

    public override void Close()
    {
        client.close();
    }

    public Communicator()
    {

    }

    public override URI getServerURI()
    {
        return serverURI;
    }

    public override void setServerURI(URI serverURI)
    {
        this.serverURI = serverURI;
    }

}
