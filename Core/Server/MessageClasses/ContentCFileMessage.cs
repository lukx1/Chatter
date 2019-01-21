using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace Server.MessageClasses
{
    public class ContentCFileMessage : CfilesMessage
    {
        public string Content { get; set; }
        public byte[] HiddenContent { get; set; }
    }
}
