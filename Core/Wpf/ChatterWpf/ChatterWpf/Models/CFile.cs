using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace ChatterWpf.Models
{
    public class CFile
    {

        public byte[] uuid;

        public int iduploader;

        public int idroom;

        public string fileName;

        public DateTime dateUploaded;

        public bool expired;
    }

}