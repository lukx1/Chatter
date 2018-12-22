package net.lukx.jchatter.lib.repos;

import net.lukx.jchatter.lib.comms.Communicator;
import net.lukx.jchatter.lib.comms.LoginHeader;

import java.util.HashMap;

public abstract class AbstractRepo {
    protected abstract String getController();

    protected Communicator communicator;

    private LoginHeader loginHeader = new LoginHeader();

    public AbstractRepo(Communicator communicator){
        this.communicator = communicator;
    }

    private class IDObject {
        private String Login = getLoginHeader().getLogin();
        private String Password = getLoginHeader().getPassword();
        private int ID;

        public IDObject(int id){
            this.ID = id;
        }
    }

    public class KeyValuePair {
        public String key;
        public Object value;

        public KeyValuePair(){

        }

        public KeyValuePair(String key, Object value){
            this.key = key;
            this.value = value;
        }
    }

    protected Object createCustomObjectWithHeader(KeyValuePair... keyValuePair){
        @SuppressWarnings("unchecked")
        HashMap<String,Object> hm = (HashMap<String,Object>)createCustomObject(keyValuePair);
        hm.put("Login",getLoginHeader().getLogin());
        hm.put("Password",getLoginHeader().getPassword());
        return hm;
    }

    protected Object createCustomObject(KeyValuePair... keyValuePair){
        HashMap<String,Object> map = new HashMap<>();
        for (KeyValuePair kv : keyValuePair)
        {
            map.put(kv.key,kv.value);
        }
        return map;
    }

    protected Object createIdObject(int id){
        return new IDObject(id);
    }

    public LoginHeader getLoginHeader() {
        return loginHeader;
    }
}
