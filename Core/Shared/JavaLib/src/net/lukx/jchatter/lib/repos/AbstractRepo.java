package net.lukx.jchatter.lib.repos;

import net.lukx.jchatter.lib.PublicApi;
import net.lukx.jchatter.lib.comms.Communicable;
import net.lukx.jchatter.lib.comms.LoginHeader;

import java.util.HashMap;

public abstract class AbstractRepo {

    /***
     * @return the controller this class send data to
     */
    protected abstract String getController();

    @PublicApi
    protected Communicable communicable;

    @PublicApi
    protected LoginHeader loginHeader = new LoginHeader();

    AbstractRepo(Communicable communicable){
        this.communicable = communicable;
    }

    @PublicApi
    protected class IDObject {
        @PublicApi
        private String Login = getLoginHeader().getLogin();
        @PublicApi
        private String Password = getLoginHeader().getPassword();
        @PublicApi
        private int ID;

        IDObject(int id){
            this.ID = id;
        }
    }

    @PublicApi
    public class KeyValuePair {
        String key;
        Object value;

        @PublicApi
        public KeyValuePair(){

        }

        KeyValuePair(String key, Object value){
            this.key = key;
            this.value = value;
        }
    }

    @PublicApi
    protected Object createCustomObjectWithHeader(KeyValuePair... keyValuePair){
        @SuppressWarnings("unchecked")
        HashMap<String,Object> hm = (HashMap<String,Object>)createCustomObject(keyValuePair);
        hm.put("Login",getLoginHeader().getLogin());
        hm.put("Password",getLoginHeader().getPassword());
        return hm;
    }

    @PublicApi
    protected Object createCustomObject(KeyValuePair... keyValuePair){
        HashMap<String,Object> map = new HashMap<>();
        for (KeyValuePair kv : keyValuePair)
        {
            map.put(kv.key,kv.value);
        }
        return map;
    }

    @PublicApi
    protected Object createIdObject(int id){
        return new IDObject(id);
    }

    @PublicApi
    public LoginHeader getLoginHeader() {
        return loginHeader;
    }
}
