using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace ChatterWpf.Models
{
    public class CFile
    {

        public byte[] uuid{get;set;}

        public int iduploader{get;set;}

        public int idroom{get;set;}

        public string fileName{get;set;}

        public DateTime dateUploaded{get;set;}

        public bool expired{get;set;}
    }

}