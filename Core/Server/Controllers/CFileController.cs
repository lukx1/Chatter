﻿using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Server.Repos;
using Server.MessageClasses;
using System.Text.RegularExpressions;

namespace Server.Controllers
{
    [Route("api/[controller]/[action]")]
    [ApiController]
    public class CFileController : ValidatingController
    {
        private readonly ICFileRepository repository = new CFileRepo();

        [HttpPost]
        [ActionName("File")]
        public async Task<IActionResult> GetFile(UUIDMessage message)
        {
            if (IsLoginValid(message))
            {
                return Ok(await repository.GetFile(message.UUID));
            }
            return BadRequest();
        }

        [HttpDelete]
        [ActionName("File")]
        public async Task<IActionResult> DeleteFile(UUIDMessage message)
        {
            if (IsLoginValid(message))
            {
                await repository.GetFile(message.UUID);
                return Ok();
            }
            return BadRequest();
        }

        [HttpPut]
        [ActionName("File")]
        public async Task<IActionResult> AddFile(ContentCFileMessage message)
        {
            if (IsLoginValid(message))
            {
                await repository.AddFile(message.Content, message.CFile);
                return Ok();
            }
            return BadRequest();
        }

        [HttpPatch]
        [ActionName("File")]
        public async Task<IActionResult> SetFile(CfilesMessage message)
        {
            if (Regex.IsMatch(message.CFile.FileName, @"(\.\.)|(\/)|(\\)"))
            {
                return BadRequest("File names can't be relative or contain folder paths");
            }

            if (IsLoginValid(message))
            {
                await repository.SetFile(message.CFile);
                return Ok();
            }
            return BadRequest();
        }

        [HttpPatch]
        public async Task<IActionResult> GetFilesByUser(IDMessage message)
        {
            if (IsLoginValid(message))
            {
                return Ok(await repository.GetFilesByUser(message.ID));
            }
            return BadRequest();
        }

        [HttpPost]
        public async Task<IActionResult> GetFilesInRoom(IDMessage message)
        {
            if (IsLoginValid(message))
            {
                return Ok(await repository.GetFilesInRoom(message.ID));
            }
            return BadRequest();
        }

        [HttpPost]
        public async Task<IActionResult> GetFileContents(UUIDMessage message)
        {
            if (IsLoginValid(message))
            {
                return Ok(await repository.GetFileContents(message.UUID));
            }
            return BadRequest();
        }

        [HttpPost]
        public async Task<IActionResult> GetFilesContents(UUIDsMessage message)
        {
            if (IsLoginValid(message))
            {
                return Ok(await repository.GetFilesContents(message.UUIDs));
            }
            return BadRequest();
        }
    }
}