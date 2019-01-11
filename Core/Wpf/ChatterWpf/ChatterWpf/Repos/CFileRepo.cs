using System;
using System.Reflection;
using Newtonsoft.Json;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using ChatterWpf.Comms;
using ChatterWpf.Models;
using System.Net.Http;

public class CFileRepo : AbstractRepo
{
    public CFileRepo(Messenger messenger) : base(messenger)
    {
        
    }

    protected override string getController()
    {
        return "CFile";
    }

    //CFile.class ?? typeof(CFile)
    public CFile getFile(byte[] uuid)
    {
        return messenger.Obtain<CFile>(getController(), "File", HttpMethod.Post, createUUIDObject(uuid));
    }

    public bool removeFile(byte[] uuid)
    {
        return messenger.Obtain<bool>(getController(), "File", HttpMethod.Delete, createUUIDObject(uuid));
    }

    public byte[] addFile(byte[] Content, CFile file)
    {
        return messenger.Obtain<byte[]>(
                getController(),
                "File",
                HttpMethod.Put,
                createCustomObjectWithHeader(
                        new KeyValuePair("Content", Content),
                        new KeyValuePair("CFile", file)
                )
        );
    }

    public void setFile(CFile file)
    {
        messenger.Obtain(getController(), "File", new HttpMethod("Patch"), file);
    }

    public CFile[] getFilesByUser(int id)
    {
        return messenger.Obtain<CFile[]>(
                getController(),
                "GetFilesByUser",
                HttpMethod.Post,
                createIdObject(id));
    }

    public CFile[] getFilesInRoom(int id)
    {
        return messenger.Obtain<CFile[]>(
                getController(),
                "GetFilesInRoom",
                HttpMethod.Post,
                createIdObject(id));
    }

    public byte[] getFileContents(byte[] uuid)
    {
        return messenger.Obtain<byte[]>(
                getController(),
                "getFileContents",
                HttpMethod.Post,
                createUUIDObject(uuid));
    }

    //TypeToken??
    public Dictionary<byte[], byte[]> getFilesContents(byte[] uuid)
    {
        return messenger.Obtain<Dictionary<byte[], byte[]>>(
            getController(),
            "getFilesContents",
            HttpMethod.Post,
            createUUIDObject(uuid));
    }


    private object createUUIDObject(byte[] uuid)
    {
        return new UUIDObject(uuid,loginHeader.Login,loginHeader.Password);
    }

    public class UUIDObject
    {

        public string Login { get; set; }
        public string Password { get; set; }
        public byte[] UUID { get; set; }

        public UUIDObject(byte[] uuid, string Login,string Password)
        {
            this.UUID = uuid;
            this.Login = Login;
            this.Password = Password;
        }
    }
}
