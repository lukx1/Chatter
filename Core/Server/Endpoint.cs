using System;
using System.Collections.Generic;
using System.Linq;
using System.Net.Http;
using System.Threading.Tasks;

namespace Server
{
    public class Endpoint
    {
        public string Name { get; set; }
        public HttpMethod Method { get; set; }
        public string URL { get; set; }
        public string Description { get; set; }
        public string HttpResponses { get; set; }
        public IEnumerable<Parameter> Input { get; set; } = Enumerable.Empty<Parameter>();
        public IEnumerable<Parameter> Output { get; set; } = Enumerable.Empty<Parameter>();
        public string Example { get; set; } = "";

        public string getMethodColor()
        {
            if(Method == HttpMethod.Get)
            {
                return "yellow";
            }
            else if(Method == HttpMethod.Post)
            {
                return "blue";
            }
            else if(Method == HttpMethod.Delete)
            {
                return "red";
            }
            else if(Method == HttpMethod.Put)
            {
                return "cyan";
            }
            return "white";
        }

        public string getHttpResponseColor(string s)
        {
            if(!int.TryParse(s, out int value))
            {
                return "white";
            }
            if(value >= 200 && value < 300)
            {
                return "lightgreen";
            }
            else
            {
                return "red";
            }
        }

        public class Parameter
        {
            public string Key { get; set; }
            public bool Optional { get; set; } = false;
            public string Value { get; set; }
        }

    }
}
