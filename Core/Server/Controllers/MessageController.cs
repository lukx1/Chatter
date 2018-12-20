using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Server.MessageClasses;
using Server.Repos;

namespace Server.Controllers
{
    [Route("api/[controller]/[action]")]
    [ApiController]
    public class MessageController : ValidatingController
    {
        private readonly IMessageRepository repo = new MessageRepo();

        [HttpPost]
        public async Task<IActionResult> GetMessagesInRoom(IDMessage message)
        {
            if (IsLoginValid(message))
            {
                return Ok(await repo.GetMessagesInRoom(message.ID));
            }
            return BadRequest();
        }

        [HttpPost]
        public async Task<IActionResult> GetMessagesInRoomSince(IDSinceMessage message)
        {
            if (IsLoginValid(message))
            {
                return Ok(await repo.GetMessagesInRoomSince(message.ID, message.Since));
            }
            return BadRequest();
        }

        [HttpPost]
        public async Task<IActionResult> GetNewMessagesForUser(IDMessage message)
        {
            if (IsLoginValid(message))
            {
                return Ok(await repo.GetNewMessagesForUser(message.ID));
            }
            return BadRequest();
        }

        [HttpDelete]
        [ActionName("Message")]
        public async Task<IActionResult> RemoveMessage(IDMessage message)
        {
            if (IsLoginValid(message))
            {
                await repo.RemoveMessage(message.ID);
                return Ok();
            }
            return BadRequest();
        }

        [HttpPost]
        [ActionName("Message")]
        public async Task<IActionResult> SetMessage(MessageMessage message)
        {
            if (IsLoginValid(message))
            {
                await repo.SetMesssage(message.Message);
                return Ok();
            }
            return BadRequest();
        }

        [HttpPut]
        [ActionName("Message")]
        public async Task<IActionResult> AddMessage(MessageMessage message)
        {
            if (IsLoginValid(message))
            {
                await repo.AddMessage(message.Message);
                return Ok();
            }
            return BadRequest();
        }

    }
}