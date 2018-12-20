using Server.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace Server.Repos
{
    public class BaseRepo
    {
        protected virtual ChatterContext Context { get; set; }

        public BaseRepo()
        {
            this.Context = new ChatterContext();
        }
    }
}
