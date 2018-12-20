using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using Server.Models;
using Server.MessageClasses;
using Server.Repos;

namespace Server.Controllers
{
    [Route("api/[controller]/[action]")]
    [ApiController]
    public class RelationshipController : ValidatingController
    {
        private readonly IRelationshipRepository relationshipRepository = new RelationshipRepo();

        [HttpPost]
        public IActionResult GetRelForUser(IDMessage message)
        {
            if (IsLoginValid(message))
            {
                return Ok(relationshipRepository.GetRelForUser(message.ID));
            }
            return BadRequest();
        }

        [HttpPost]
        [ActionName("Rel")]
        public IActionResult SetRel(RelMessage message)
        {
            if (IsLoginValid(message))
            {
                relationshipRepository.SetRel(message.Relationship);
                return Ok();
            }
            return BadRequest();
        }

        [HttpDelete]
        [ActionName("Rel")]
        public IActionResult RemoveRel(RelMessage message)
        {
            if (IsLoginValid(message))
            {
                relationshipRepository.SetRel(message.Relationship);
                return Ok();
            }
            return BadRequest();
        }

        [HttpPut]
        [ActionName("Rel")]
        public IActionResult AddRel(RelMessage message)
        {
            if (IsLoginValid(message))
            {
                relationshipRepository.SetRel(message.Relationship);
                return Ok();
            }
            return BadRequest();
        }
    }
}