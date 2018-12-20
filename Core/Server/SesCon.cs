using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace Server
{
    public class SesCon
    {
        private ISession session => ControllerBase.HttpContext.Session;

        private ControllerBase ControllerBase;

        public DateTime RequestCounterExpires
        {
            get
            {
                var x = session.GetString("RequestCounterExpires");
                if(x == null)
                {
                    session.SetString("RequestCounterExpires", DateTime.Now.AddHours(1).ToString());
                    return RequestCounterExpires;
                }
                return DateTime.Parse(x);
            }
            set
            {
                session.SetString("RequestCounterExpires", value.ToString());
            }
        }

        public int RequestsPastHour
        {
            get
            {
                var rq = session.GetInt32("RequestsPastHour");
                if (rq == null || RequestCounterExpires > DateTime.Now)
                {
                    RequestsPastHour = 0;
                }
                return (int)rq;
            }
            set
            {
                session.SetInt32("RequestsPastHour", value);
            }
        }

        public const int MaxRequestPerHour = 1024;

        public SesCon(ControllerBase controllerBase)
        {
            this.ControllerBase = controllerBase;
        }

        public bool AreTooManyRequests()
        {
            return RequestsPastHour > MaxRequestPerHour;
        }
    }
}
