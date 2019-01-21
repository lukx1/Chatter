using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using ChatterWpf.Comms;

public abstract class AbstractRepo
{
    protected abstract string getController();

    protected Messenger messenger;

    public LoginHeader loginHeader { get; private set; } = new LoginHeader();

    public AbstractRepo(Messenger messenger)
    {
        this.messenger = messenger;
    }

    public class IDObject
    {
        public string Login { get; set; }
        public string Password { get; set; }
        public int ID { get; set; }

        public IDObject(string Login, string Password, int ID)
        {
            this.Login = Login;
            this.Password = Password;
            this.ID = ID;
        }
    }

    public class KeyValuePair
    {
        public string Key { get; set; }
        public object Value { get; set; }

        public KeyValuePair()
        {

        }

        public KeyValuePair(string key, object value)
        {
            this.Key = key;
            this.Value = value;
        }
    }

    //KeyValuePair...keyValuePair ?
    protected object createCustomObjectWithHeader(params KeyValuePair[] keyValuePair)
    {
        var hm = (Dictionary<string, object>)createCustomObject(keyValuePair);
        hm.Add("Login",loginHeader.Login);
        hm.Add("Password", loginHeader.Password);
        return hm;
    }

    protected object createCustomObject(params KeyValuePair[] keyValuePair)
    {
        var map = new Dictionary<string, object>();
        foreach(KeyValuePair kv in keyValuePair)
        {
            map[kv.Key] = kv.Value;
        }
        return map;
    }

    protected object createIdObject(int id)
    {
        return new IDObject(loginHeader.Login,loginHeader.Password,id);
    }

}
