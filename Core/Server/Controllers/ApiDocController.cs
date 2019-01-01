using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Hosting;
using System.IO;

namespace Server.Controllers
{
    public class ApiDocController : Controller
    {
        private readonly IHostingEnvironment environment;

        public ApiDocController(IHostingEnvironment environment)
        {
            this.environment = environment;
        }

        public IActionResult Index()
        {
            return View();
        }

        public IActionResult JavaLib()
        {
            return View();
        }

        private void SetViewBag(string fileName)
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
            SetViewBag("users.txt");
            return View();
        }

        public IActionResult MessageEndpoint()
        {
            SetViewBag("messages.txt");
            return View();
        }

        public IActionResult RoomEndpoint()
        {
            SetViewBag("rooms.txt");
            return View();
        }

        public IActionResult RelationshipEndpoint()
        {
            SetViewBag("relationships.txt");
            return View();
        }

        public IActionResult CFileEndpoint()
        {
            SetViewBag("cfiles.txt");
            return View();
        }

        public IActionResult CsharpExamples()
        {
            return View();
        }

        public IActionResult CppExamples()
        {
            return View();
        }
    }
}