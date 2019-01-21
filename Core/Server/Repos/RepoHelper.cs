using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.EntityFrameworkCore;
using Server.Models;

namespace Server.Repos
{
    public class RepoHelper
    {

        private ChatterContext context;

        public RepoHelper(ChatterContext context)
        {
            this.context = context;
        }

        public IQueryable<Rooms> GetAllOneOnOneRoomsWithUser(int userId)
        {
            return context.Rooms.Where(
                r =>
                    r.OneOnOne &&
                    r.Idcreator == userId ||
                    r.Roomusers.Any(
                        ru =>
                            ru.Iduser == userId));
        }

        public IQueryable<Rooms> GetAllOneOnOneRoomsWithRel(Relationships rel)
        {
            return context.Rooms.Where(
                r =>
                    r.OneOnOne &&
                    (
                        (r.Idcreator == rel.IdsourceUser) &&
                        r.Roomusers.Any(
                            ru =>
                                ru.Iduser == rel.IdtargetUser)
                    )
                    ||
                    (
                        (r.Idcreator == rel.IdtargetUser) &&
                        r.Roomusers.Any(
                            ru =>
                                ru.Iduser == rel.IdsourceUser)
                    )
                    )
                ;
        }

    }
}
