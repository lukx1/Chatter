using Server.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace Server.Repos
{
    public interface ICFileRepository
    {
        Task<Cfiles> GetFile(byte[] uuid);
        Task<bool> RemoveFile(byte[] uuid);
        Task AddFile(byte[] Content, Cfiles file);
        Task SetFile(Cfiles file);
        Task<IEnumerable<Cfiles>> GetFilesByUser(int ID);
        Task<IEnumerable<Cfiles>> GetFilesInRoom(int ID);
        Task<byte[]> GetFileContents(byte[] UUID);
        Task<IDictionary<byte[], byte[]>> GetFilesContents(IEnumerable<byte[]> UUIDs);
    }

}
