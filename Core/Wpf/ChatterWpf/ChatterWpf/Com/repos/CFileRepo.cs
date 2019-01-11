using System;
using System.Reflection;
using Newtonsoft.Json;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using Wpf.ChatterWpf.ChatterWpf.Com.Comms;

public class CFileRepo
{
    public CFileRepo(ICommunicable communicable)
    {
        base(communicable);
    }

    protected override string getController()
    {
        return "CFile";
    }

    //CFile.class ?? typeof(CFile)
    public CFile getFile(byte[] uuid)
    {
        return communicable.Obtain(getController(), "File", HttpMethod.POST, createUUIDObject(uuid), typeof(CFile));
    }

    public bool removeFile(byte[] uuid)
    {
        return communicable.Obtain(getController(), "File", HttpMethod.DELETE, createUUIDObject(uuid), typeof(bool));
    }

    public byte[] addFile(byte[] Content, CFile file)
    {
        return communicable.Obtain(
                getController(),
                "File",
                HttpMethod.PUT,
                createCustomObjectWithHeader(
                        new KeyValuePair("Content", Content),
                        new KeyValuePair("CFile", file)
                ),
                typeof(byte[])
        );
    }

    public void setFile(CFile file)
    {
        communicable.Obtain(getController(), "File", HttpMethod.PATCH, file, typeof(void));
    }

    public CFile[] getFilesByUser(int id)
    {
        return communicable.Obtain(
                getController(),
                "GetFilesByUser",
                HttpMethod.POST,
                createIdObject(id),
                typeof(CFile[]));
    }

    public CFile[] getFilesInRoom(int id)
    {
        return communicable.Obtain(
                getController(),
                "GetFilesInRoom",
                HttpMethod.POST,
                createIdObject(id),
                typeof(CFile[]));
    }

    public byte[] getFileContents(byte[] uuid)
    {
        return communicable.Obtain(
                getController(),
                "getFileContents",
                HttpMethod.POST,
                createUUIDObject(uuid),
                typeof(byte[]));
    }
    //TypeToken??
    public Dictionary<byte[], byte[]> getFilesContents(byte[] uuid){
        return communicable.Obtain(
                getController(),
                "getFilesContents",
                HttpMethod.POST,
                createUUIDObject(uuid),
                new TypeToken<Dictionary<byte[], byte[]>>(){}.getType());
    }


    private Object createUUIDObject(byte[] uuid)
    {
        return new UUIDObject(uuid);
    }

    private class UUIDObject
    {

        private string Login = getLoginHeader().getLogin();
        private string Password = getLoginHeader().getPassword();
        private byte[] UUID;

        UUIDObject(byte[] uuid)
        {
            this.UUID = uuid;
        }
    }
}
