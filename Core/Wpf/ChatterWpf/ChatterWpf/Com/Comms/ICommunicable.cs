using System;
using System.Linq;
using System.Web;
using ChatterWpf.ChatterWpf;
//Help
public interface ICommunicable
{
	public T Obtain<T>(string controller, string action, HttpMethod method, Object inData, Type type);

    public T Obtain<T>(string controller, string action, HttpMethod method, Object inData); //Chybí type

    public void Close();

    public URI ServerURI { get; }

    public  void setServerURI(URI serverURI);
}
