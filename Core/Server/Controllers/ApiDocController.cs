using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc;
using System.Net.Http;
using Microsoft.AspNetCore.Hosting;
using System.IO;

namespace Server.Controllers
{
    public class ApiDocController : Controller
    {
        private IHostingEnvironment environment;

        public ApiDocController(IHostingEnvironment environment)
        {
            this.environment = environment;
        }

        public IActionResult Index()
        {
            return View();
        }

        private void setViewBag(string fileName)
        {
            var s = System.IO.File.ReadAllText
                (
                    Path.Combine(environment.ContentRootPath, "EndpointData", fileName)
                );
            ViewBag.Endpoints = Newtonsoft.Json.JsonConvert.DeserializeObject<Endpoint[]>
                (
                s
                );
            ViewBag.BaseUrl = Request.Scheme + "://" + Request.Host + Request.PathBase + "/";
        }

        public IActionResult UserEndpoint()
        {
            setViewBag("users.txt");
            return View();
        }

        public IActionResult MessageEndpoint()
        {
            setViewBag("messages.txt");
            return View();
        }

        public IActionResult RoomEndpoint()
        {
            setViewBag("rooms.txt");
            return View();
        }

        public IActionResult RelationshipEndpoint()
        {
            setViewBag("relationships.txt");
            return View();
        }

        public IActionResult CFileEndpoint()
        {
            setViewBag("cfiles.txt");
            return View();
        }
    }
}