package net.lukx.jchatter.lib.repos;

import net.lukx.jchatter.lib.comms.Communicable;
import net.lukx.jchatter.lib.comms.LoginHeader;

import java.util.HashMap;

public abstract class AbstractRepo {

    /***
     * @return the controller this class send data to
     */
    protected abstract String getController();


    Communicable communicable;

    private LoginHeader loginHeader = new LoginHeader();

    AbstractRepo(Communicable communicable){
        this.communicable = communicable;
    }

    @SuppressWarnings("unused")
    private class IDObject {
        private String Login = getLoginHeader().getLogin();
        private String Password = getLoginHeader().getPassword();
        private int ID;

        IDObject(int id){
            this.ID = id;
        }
    }

    @SuppressWarnings("unused")
    public class KeyValuePair {
        String key;
        Object value;

        public KeyValuePair(){

        }

        KeyValuePair(String key, Object value){
            this.key = key;
            this.value = value;
        }
    }

    Object createCustomObjectWithHeader(KeyValuePair... keyValuePair){
        @SuppressWarnings("unchecked")
        HashMap<String,Object> hm = (HashMap<String,Object>)createCustomObject(keyValuePair);
        hm.put("Login",getLoginHeader().getLogin());
        hm.put("Password",getLoginHeader().getPassword());
        return hm;
    }

    private Object createCustomObject(KeyValuePair... keyValuePair){
        HashMap<String,Object> map = new HashMap<>();
        for (KeyValuePair kv : keyValuePair)
        {
            map.put(kv.key,kv.value);
        }
        return map;
    }

    Object createIdObject(int id){
        return new IDObject(id);
    }

    LoginHeader getLoginHeader() {
        return loginHeader;
    }
}
