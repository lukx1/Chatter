using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Server.Models;
using System.Buffers.Text;
using System.IO;

namespace Server.Repos
{
    public class CFileRepo : ICFileRepository
    {
        private ChatterContext context = new ChatterContext();

        private async Task AddFileToDisk(byte[] Content, Cfiles file)
        {
            throw new NotImplementedException();
            string name = new Guid(file.FileName).ToString();
            await Task.Run(() =>
           {
               
           });
        }

        public async Task AddFile(byte[] Content, Cfiles file)
        {
            throw new NotImplementedException();
            await context.Cfiles.AddAsync(file);
            await context.SaveChangesAsync();
        }

        public async Task<Cfiles> GetFile(byte[] uuid)
        {
            return await context.Cfiles.FindAsync(uuid);
        }

        public async Task<bool> RemoveFile(byte[] uuid)
        {
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

        public Task<IEnumerable<Cfiles>> GetFilesByUser(int ID)
        {
            throw new NotImplementedException();
        }

        public Task<IEnumerable<Cfiles>> GetFilesInRoom(int ID)
        {
            throw new NotImplementedException();
        }

        public Task<byte[]> GetFileContents(byte[] UUID)
        {
            throw new NotImplementedException();
        }

        public Task<IDictionary<byte[], byte[]>> GetFilesContents(IEnumerable<byte[]> UUIDs)
        {
            throw new NotImplementedException();
        }
    }
}
