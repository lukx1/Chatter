using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Server.MessageClasses;
using Server.Models;
using Server.Repos;
using PrgDbWeb.Helpers;

namespace Server.Controllers
{
    [Route("api/[controller]/[action]")]
    [ApiController]
    public class UserController : ValidatingController
    {
        public IUserRepository UserRepository = new UserRepo();

#if DEBUG
        private const string RRPassword = "1R2w4E+WsQpOcopZjQda+2DyLi3X5+FRBkoEBg4PoeLXRdEhWTiStw3IL1/el/J0";

        [HttpPost]
        public IActionResult ResetRequestCounter(string password)
        {
            if (PasswordHelper.VerifyPasswordPbkdf2(password, RRPassword))
            {
                SessionControl.RequestsPastHour = 0;
                SessionControl.RequestCounterExpires = DateTime.Now.AddHours(1);
                return Ok();
            }
            return Forbid();
        }
#endif
        [HttpPost]
        public IActionResult GetUsers(LoginHeader header)
        {
            if (IsLoginValid(header))
            {
                return Ok(UserRepository.GetUsers());
            }
            return BadRequest();
        }

        // GET: api/User/5
        [HttpPost]
        public IActionResult GetUserWithLogin(LoginMessage message)
        {
            if (IsLoginValid(message))
            {
                return Ok(UserRepository.GetUserWithLogin(message.Login));
            }
            return BadRequest();
        }

        // POST: api/User
        [HttpDelete]
        [ActionName("User")]
        public IActionResult RemoveUser(IDMessage message)
        {
            if (IsLoginValid(message))
            {
                if (UserRepository.RemoveUser(message.ID))
                    return Ok();
                else
                    return BadRequest();
            }
            return BadRequest();
        }

        [HttpPut]
        public IActionResult RegisterUser(UserMessage message)
        {
            if (SessionControl.AreTooManyRequests())
                return Forbid("Too many requests");
            UserRepository.RegisterUser(message.User);
            return Ok();
        }

        // DELETE: api/ApiWithActions/5
        [HttpPost]
        public IActionResult ValidateLogin(LoginHeader message)
        {
            if(UserRepository.IsLoginValid(message.Login, message.Password))
            {
                return Ok();
            }
            return Forbid();
        }

        [HttpPost]
        [ActionName("User")]
        public IActionResult SetUser(UserMessage message)
        {
            if (IsLoginValid(message))
            {
                UserRepository.SetUser(message.User);
                return Ok();
            }
            return BadRequest();
        }

    }
}
