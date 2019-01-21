using System;
using System.Buffers.Text;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Server.Repos;
using Server.MessageClasses;
using System.Text.RegularExpressions;
using Microsoft.AspNetCore.Hosting;
using Microsoft.CodeAnalysis.CSharp.Syntax;
using Microsoft.Extensions.Configuration.EnvironmentVariables;

namespace Server.Controllers
{
    [Route("api/[controller]/[action]")]
    [ApiController]
    public class CFileController : ValidatingController
    {
        private readonly ICFileRepository repository;
        private IHostingEnvironment environment;

        public CFileController(IHostingEnvironment environment)
        {
            this.environment = environment;
            repository = new CFileRepo(environment);
        }

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
            message.HiddenContent = Convert.FromBase64String(message.Content);
            message.Content = null;
            if (IsLoginValid(message))
            {
                if (message.HiddenContent == null || message.HiddenContent == new byte[0] || message.HiddenContent.Length < 1)
                    return BadRequest("No content received");
                if (message.HiddenContent.Length > (1000000 * 3))
                    return BadRequest("File too large (max 3MB)");
                if (Regex.IsMatch(message.CFile.FileName, "^.*\\.((jpg)|(jpeg)|(png))$", RegexOptions.IgnoreCase) && message.HiddenContent.Length > 500000)
                    return BadRequest("Image too large (max 500KB)");
                var resp = await repository.AddFile(message.HiddenContent, message.CFile);
                return Ok(resp);
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

        [HttpPost]
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