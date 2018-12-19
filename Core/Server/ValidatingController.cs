using Microsoft.AspNetCore.Mvc;
using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace Server
{
    public class ValidatingController : ControllerBase
    {
        protected bool IsLoginValid<T>(T o) where T : LoginHeader
        {
            return true;
            //TODO:Finish
        }
    }
}
