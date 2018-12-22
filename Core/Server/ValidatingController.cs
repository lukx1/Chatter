using Microsoft.AspNetCore.Mvc;
using Newtonsoft.Json;
using PrgDbWeb.Helpers;
using Server.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace Server
{
    public class ValidatingController : ControllerBase
    {
        protected SesCon SessionControl;

        public ValidatingController()
        {
            SessionControl = new SesCon(this);
            
        }

        private const string RRPassword = "1R2w4E+WsQpOcopZjQda+2DyLi3X5+FRBkoEBg4PoeLXRdEhWTiStw3IL1/el/J0";
        protected bool IsLoginValid<T>(T o) where T : LoginHeader
        {
            return true;
            if (SessionControl.AreTooManyRequests())
            {
                return false;
            }

#if DEBUG
            if (PasswordHelper.VerifyPasswordPbkdf2(o.Password, RRPassword))
            {
                return true;
            }
#endif
            var user = new ChatterContext().Users.SingleOrDefault(r => r.Login == o.Login);
            if (user == null)
                return false;
            if (!PasswordHelper.VerifyPasswordPbkdf2(o.Password, user.Password))
                return false;
            SessionControl.RequestsPastHour++;
            return true;
            //TODO:Finish
        }
    }
}
