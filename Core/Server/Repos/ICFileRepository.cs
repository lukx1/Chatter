using Server.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace Server.Repos
{
    public interface ICFileShareRepository
    {
        CFile GetFile(int idFile);
        bool RemoveFile(int idFile);
        void AddFile(CFile file);
    }

}
