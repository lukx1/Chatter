using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Server.Models;
using System.Buffers.Text;
using System.IO;
using Microsoft.AspNetCore.Hosting;

namespace Server.Repos
{
    public class CFileRepo : ICFileRepository
    {
        private ChatterContext context = new ChatterContext();
        private IHostingEnvironment environment;
        private string userContentDir => Path.Combine(environment.ContentRootPath, "Content", "UserContent");
        private static readonly char[] padding = { '=' };
        public CFileRepo(IHostingEnvironment environment)
        {
            this.environment = environment;
 
        }

        private string UuidToPathSafe(byte[] b)
        {
            return System.Convert.ToBase64String(b)
                .TrimEnd(padding).Replace('+', '-').Replace('/', '_');
        }

        private void OldFileCheck()
        {
            foreach (var file in context.Cfiles)
            {
                var fileAgeDays = (DateTime.Now - file.DateUploaded).Days;
                if (fileAgeDays > 3)
                {
                    if (file.Expired)
                    {
                        continue;
                    }
                    else
                    {
                        file.Expired = true;
                        deleteFile(file.Uuid);
                    }
                }
                if(fileAgeDays > 30)
                {
                    context.Cfiles.Remove(file);
                }
            }
            context.SaveChanges();
        }

        private async Task AddFileToDisk(byte[] Content, Cfiles file)
        {
            string name = UuidToPathSafe(file.Uuid);
            await Task.Run(() =>
            {
               using(var writer = new BinaryWriter(new FileStream(Path.Combine(userContentDir, name),FileMode.Create)))
               {
                   writer.Write(Content);
               }
           });
        }

        public async Task<byte[]> AddFile(byte[] Content, Cfiles file)
        {
            var task2 = context.Cfiles.AddAsync(file);
            await task2;
            await context.SaveChangesAsync();
            file.Uuid = file.Uuid;
            var task1 = AddFileToDisk(Content, file);
           
            await task1;
            
            await context.SaveChangesAsync();
            return file.Uuid;
        }

        public async Task<Cfiles> GetFile(byte[] uuid)
        {
            return await context.Cfiles.FindAsync(uuid);
        }

        private bool deleteFile(byte[] uuid)
        {
            try
            {
                File.Delete(Path.Combine(userContentDir, UuidToPathSafe(uuid)));
                return true;
            }
            catch (FileNotFoundException e)
            {
                return false;
            }
        }

        public async Task<bool> RemoveFile(byte[] uuid)
        {
            deleteFile(uuid);
            context.Cfiles.Remove(await context.Cfiles.FindAsync(uuid));
            await context.SaveChangesAsync();
            return true;
        }

        public async Task SetFile(Cfiles file)
        {
            var f = await context.Cfiles.FindAsync(file.Uuid);
            f.FileName = file.FileName;      
            await context.SaveChangesAsync();
        }

        public async Task<IEnumerable<Cfiles>> GetFilesByUser(int ID)
        {
            return context.Cfiles.Where(r => r.Iduploader == ID).ToList();
        }

        public async Task<IEnumerable<Cfiles>> GetFilesInRoom(int ID)
        {
            return context.Cfiles.Where(r => r.Idroom == ID).ToList();
        }

        public async Task<string> GetFileContents(byte[] UUID)
        {
            var file = Path.Combine(userContentDir, UuidToPathSafe(UUID));
            if (!File.Exists(file))
            {
                return null;
            }
            return Convert.ToBase64String(File.ReadAllBytes(file));
        }

        public async Task<IDictionary<byte[], string>> GetFilesContents(IEnumerable<byte[]> UUIDs)
        {
            var dict = new Dictionary<byte[], string>();
            foreach (var uuid in UUIDs)
            {
                dict.Add(uuid,await GetFileContents(uuid));
            }
            return dict;
        }
    }
}
