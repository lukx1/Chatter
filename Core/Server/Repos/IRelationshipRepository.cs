using Server.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace Server.Repos
{
    public interface IRelationshipRepository
    {
        IEnumerable<Relationship> GetRelForUser(int id);
        void SetRel(Relationship rel);
        bool RemoveRel(int id);
        void AddRel(Relationship rel);
    }

}
