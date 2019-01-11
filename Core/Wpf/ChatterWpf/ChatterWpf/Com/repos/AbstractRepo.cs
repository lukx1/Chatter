using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Collections.Generic.Dictionary;

public abstract class AbstractRepo
{
    protected abstract string getController();

    protected ICommunicable communicable;

    private LoginHeader loginHeader = new LoginHeader();

   

    AbstractRepo(ICommunicable communicable)
    {
        this.communicable = communicable;
    }

    private class IDObject
    {
        private string Login = getLoginHeader().getLogin();
        private string Password = getLoginHeader().getPassword();
        private int ID;

        IDObject(int id)
        {
            this.ID = id;
        }
    }

    public class KeyValuePair
    {
        string key;
        Object value;

        public KeyValuePair()
        {

        }

        KeyValuePair(string key, Object value)
        {
            this.key = key;
            this.value = value;
        }
    }
    //KeyValuePair...keyValuePair ?
    protected Object createCustomObjectWithHeader(KeyValuePair keyValuePair)
    {
        Dictionary<String, Object> hm = (Dictionary<String, Object>)createCustomObject(keyValuePair);
        hm[Login] = getLoginHeader().getLogin();
        hm[Password] = getLoginHeader().getPassword();
        return hm;
    }

    protected Object createCustomObject(KeyValuePair keyValuePair)
    {
        Dictionary<String, Object> map = new Dictionary<>();
        foreach(KeyValuePair kv in keyValuePair)
        {
            map[kv.key] = kv.value;
        }
        return map;
    }



    Object createIdObject(int id)
    {
        return new IDObject(id);
    }

    LoginHeader getLoginHeader()
    {
        return loginHeader;
    }
}
