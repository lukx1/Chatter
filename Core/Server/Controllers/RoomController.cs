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
    public class RoomController : ValidatingController
    {
        private readonly IRoomRepository roomRepository = new RoomRepo();

        [HttpPost]
        public IActionResult GetRoomsWithUser(IDMessage message)
        {
            if (IsLoginValid(message))
            {
                return Ok(roomRepository.GetRoomsWithUser(message.ID));
            }
            return BadRequest();
        }

        [HttpPost]
        public IActionResult GetUsersInRoom(IDMessage message)
        {
            if (IsLoginValid(message))
            {
                var users = roomRepository.GetUsersInRoom(message.ID);
                return Ok(users);
            }
            return BadRequest();
        }

        [HttpPut]
        [ActionName("Room")]
        public IActionResult AddRoom(RoomMessage message)
        {
            if (IsLoginValid(message)) { 
                return Ok(roomRepository.AddRoom(message.Room));
            }
            return BadRequest();
        }

        [HttpDelete]
        [ActionName("Room")]
        public IActionResult RemoveRoom(IDMessage message)
        {
            if (IsLoginValid(message))
            {
                roomRepository.RemoveRoom(message.ID);
                
                return Ok();
            }
            return BadRequest();
        }

        [HttpPost]
        [ActionName("Room")]
        public IActionResult SetRoom(RoomMessage message)
        {
            if (IsLoginValid(message))
            {
                roomRepository.SetRoom(message.Room);
                return Ok();
            }
            return BadRequest();
        }

        [HttpPut]
        public IActionResult AddUserToRoom(IDUserIDRoomMessage message)
        {
            if (IsLoginValid(message))
            {
                roomRepository.AddUserToRoom(message.IDUser, message.IDRoom);
                return Ok();
            }
            return BadRequest();
        }

        [HttpDelete]
        public IActionResult RemoveUserFromRoom(IDUserIDRoomMessage message)
        {
            if (IsLoginValid(message))
            {
                return Ok(roomRepository.RemoveUserFromRoom(message.IDUser, message.IDRoom));
            }
            return BadRequest();
        }
    }
}