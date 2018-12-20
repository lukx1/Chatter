using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Server.Models;

namespace Server.Repos
{
    public class RelationshipRepo : IRelationshipRepository
    {
        private readonly ChatterContext context;

        public RelationshipRepo()
        {
            this.context = new ChatterContext();
        }

        public void AddRel(Relationships rel)
        {
            context.Relationships.Add(rel);
            context.SaveChanges();
        }

        public IEnumerable<Relationships> GetRelForUser(int id)
        {
            return context.Relationships.Where(r => r.IdsourceUser == id);
        }

        public bool RemoveRel(int id)
        {
            var rel = context.Relationships.Find(id);
            if(rel == null)
            {
                return false;
            }
            context.Relationships.Remove(rel);
            context.SaveChanges();
            return true;
        }

        public void SetRel(Relationships rel)
        {
            var r = context.Relationships.Find(rel.Id);
            r.DateCreated = rel.DateCreated;
            r.IdtargetUser = rel.IdtargetUser;
            r.RelationType = rel.RelationType;
            context.SaveChanges();
        }
    }
}
