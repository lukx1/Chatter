using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Net.Http.Headers;
using System.Security.Cryptography.X509Certificates;
using System.Text;
using System.Threading.Tasks;

namespace ChatterWpf.Comms
{
    /// <summary>
    /// Odesílá a přijímá http zprávy
    /// </summary>
    /// Předem známo jako HttpAdvancedClient.
    /// <see cref="ReadMessage{T}"/> na čtení
    public class Messenger
    {
        private HttpClient client;

        /// <summary>
        /// Vytvoří instanci a uloží si kontaktní server
        /// </summary>
        /// <param name="serverUrl"></param>
        public Messenger(string serverUrl)
        {
            client = new HttpClient();
            client.DefaultRequestHeaders
                  .Accept
                  .Add(new MediaTypeWithQualityHeaderValue("application/json"));//ACCEPT header
            client.Timeout = TimeSpan.FromSeconds(3);
            client.BaseAddress = new Uri(serverUrl);

        }

        /// <summary>
        /// Přečte zprávu genericky
        /// </summary>
        /// Není možné přeložit pomocí interfacu. T musí být identický 
        /// s přijmutým jsonem
        /// <typeparam name="T"> result message</typeparam>
        /// <returns>Message</returns>
        public static T ReadMessage<T>(string message)
        {
            return JsonConvert.DeserializeObject<T>(message);
        }

        /// <summary>
        /// Univerzálně přečte zprávu
        /// </summary>
        /// <returns></returns>
        public static Dictionary<string, string> ReadMessageAsDict(string jsonResponse)
        {
            return JsonConvert.DeserializeObject<Dictionary<string, string>>(jsonResponse);
        }

        /// <summary>
        /// Asynchroní odesílání zprávy
        /// </summary>
        /// 
        ///     Exceptiony:
        ///         HTTP:
        ///             V případu chyby HTTP hází Aggregate Exception který
        ///             obsahuje HttpRequestException který obsahuje SocketException
        ///         JSon:
        ///             Pokud je špatně zadán generický parametr může být hozen
        ///             JsonException
        /// <param name="message">Zpráva k odeslání</param>
        /// <param name="controller">Jméno kontroleru</param>
        /// <param name="httpMethod">Druh http zprávy</param>>
        /// 
        /// <returns>Odpověď serveru</returns>
        public async Task<T> SendAsync<T>(object message, string controller, HttpMethod httpMethod)
        {  
            return JsonConvert.DeserializeObject<T>(await RawSend(message,controller,httpMethod));
        }

        private async Task<string> RawSend(object message, string controller, HttpMethod httpMethod)
        {
            var json = JsonConvert.SerializeObject(message);
            HttpResponseMessage response = null;
            try
            {
                var task = client.SendAsync(new HttpRequestMessage(httpMethod, "api/" + controller) { Content = new StringContent(json, Encoding.UTF8, "application/json") });
                task.Wait();
                response = task.Result;
            }
            catch(Exception e)
            {
                throw e;
                Console.WriteLine();
            }
            //var response = task.Result;
            var task2 = response.Content.ReadAsStringAsync();
            task2.Wait();
            var serverJson = task2.Result;

            if (!IsSuccessStatusCode((int)response.StatusCode))
            {
                throw new HttpRequestException("Bad status code received " + response.StatusCode);
            }

            return serverJson;
        }

        public bool IsSuccessStatusCode(int code) => code >= 200 && code < 300;

        public void Obtain(string controller, string action, HttpMethod method, object inData)
        {
            try
            {
                RawSend(inData, controller + "/" + action, method).Wait();
            }
            catch (AggregateException e)
            {
                throw e.InnerException;
            }
        }

        public T Obtain<T>(string controller, string action, HttpMethod method, object inData)
        {
            var task = SendAsync<T>(inData, controller + "/" + action, method);
            task.Wait(3000);
            return task.Result;
        }

    }
}
