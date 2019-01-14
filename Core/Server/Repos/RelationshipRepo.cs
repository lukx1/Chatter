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

        private bool DoesSameRelExist(Relationships rel)
        {
            return context.Relationships.Any(r =>
                r.IdsourceUser == rel.IdsourceUser && r.IdtargetUser == rel.IdtargetUser);
        }

        private bool AreUsersFriends(Relationships rel)
        {
            return (rel.RelationType & (1 << 1)) > 0;
        }


        private void attemptCreateNewFriendsChat(Relationships rel)
        {
            if (!AreUsersFriends(rel))
            {
                return;
            }
            if (new RepoHelper(context).GetAllOneOnOneRoomsWithRel(rel).Any())
            {
                return;
            }
            createNewFriendsChat(rel);
        }

        private void createNewFriendsChat(Relationships rel)
        {
            Rooms newRoom = new Rooms()
            {
                OneOnOne = true,
                Idcreator = rel.IdsourceUser,
                Name = "1o1",
            };
            context.Rooms.Add(newRoom);

            Roomusers ruSource = new Roomusers()
            {
                Iduser = rel.IdsourceUser,
                IdroomNavigation = newRoom
            };
            Roomusers ruTarget = new Roomusers()
            {
                Iduser = rel.IdtargetUser,
                IdroomNavigation = newRoom,
            };
            context.Roomusers.Add(ruSource);
            context.Roomusers.Add(ruTarget);
            context.SaveChanges();
        }

        public bool AddRel(Relationships rel)
        {
            if (DoesSameRelExist(rel))
            {
                return false;
            }
            rel.DateCreated = DateTime.Now;
            context.Relationships.Add(rel);
            context.SaveChanges();
            attemptCreateNewFriendsChat(rel);
            return true;
        }

        public IEnumerable<Relationships> GetRelAboutUser(int id)
        {
            return context.Relationships.Where(r => r.IdtargetUser == id);
        }

        public IEnumerable<Relationships> GetRelForUser(int id)
        {
            return context.Relationships.Where(r => r.IdsourceUser == id);
        }

        public bool RemoveRel(int id)
        {
            var rel = context.Relationships.Find(id);
            if (rel == null)
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
            attemptCreateNewFriendsChat(r);
        }
    }
}
