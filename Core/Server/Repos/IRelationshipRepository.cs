using Server.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace Server.Repos
{
    public interface IRelationshipRepository
    {
        IEnumerable<Relationships> GetRelForUser(int id);
        void SetRel(Relationships rel);
        bool RemoveRel(int id);
        bool AddRel(Relationships rel);
        IEnumerable<Relationships> GetRelAboutUser(int id);
    }

}
