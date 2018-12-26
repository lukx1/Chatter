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
        private string userContentDir => Path.Combine(environment.WebRootPath, "Content", "UserContent");

        public CFileRepo(IHostingEnvironment environment)
        {
            this.environment = environment;
 
        }

        private async Task AddFileToDisk(byte[] Content, Cfiles file)
        {
            string name = Convert.ToBase64String(file.Uuid);
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
            var task1 = AddFileToDisk(Content, file);
            var task2 =  context.Cfiles.AddAsync(file);
            await task1;
            await task2;
            await context.SaveChangesAsync();
            return file.Uuid;
        }

        public async Task<Cfiles> GetFile(byte[] uuid)
        {
            return await context.Cfiles.FindAsync(uuid);
        }

        public async Task<bool> RemoveFile(byte[] uuid)
        {
            try
            {
                File.Delete(Path.Combine(userContentDir, Convert.ToBase64String(uuid)));
            }
            catch (FileNotFoundException e)
            {
                return false;
            }
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

        public async Task<byte[]> GetFileContents(byte[] UUID)
        {
            var file = Path.Combine(userContentDir, Convert.ToBase64String(UUID));
            if (!File.Exists(file))
            {
                return null;
            }
            return File.ReadAllBytes(file);
        }

        public async Task<IDictionary<byte[], byte[]>> GetFilesContents(IEnumerable<byte[]> UUIDs)
        {
            var dict = new Dictionary<byte[], byte[]>();
            foreach (var uuid in UUIDs)
            {
                dict.Add(uuid,await GetFileContents(uuid));
            }
            return dict;
        }
    }
}
