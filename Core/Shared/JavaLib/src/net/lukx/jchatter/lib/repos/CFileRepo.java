package net.lukx.jchatter.lib.repos;

import com.google.gson.reflect.TypeToken;
import net.lukx.jchatter.lib.comms.Communicator;
import net.lukx.jchatter.lib.models.CFile;
import net.lukx.jchatter.lib.models.Message;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.UUID;

public class CFileRepo extends AbstractRepo{

    public CFileRepo(Communicator communicator) {
        super(communicator);
    }

    @Override
    protected String getController() {
        return "CFile";
    }

    public CFile getFile(byte[] uuid) throws IOException, URISyntaxException {
        return communicator.Obtain(getController(),"File", Communicator.HttpMethod.POST,createUUIDObject(uuid),CFile.class);
    }

    public boolean removeFile(byte[] uuid) throws IOException, URISyntaxException {
        return communicator.Obtain(getController(),"File", Communicator.HttpMethod.DELETE,createUUIDObject(uuid),boolean.class);
    }

    public void addFile(byte[] Content, CFile file) throws IOException, URISyntaxException {
        communicator.Obtain(
                getController(),
                "File",
                Communicator.HttpMethod.PUT,
                createCustomObjectWithHeader(
                        new KeyValuePair("Content",Content),
                        new KeyValuePair("CFile",file)
                ),
                void.class
        );
    }

    public void setFile(CFile file) throws IOException, URISyntaxException {
        communicator.Obtain(getController(),"File", Communicator.HttpMethod.PATCH,file,void.class);
    }

    public Iterable<CFile> getFilesByUser(int id) throws IOException, URISyntaxException {
        return communicator.Obtain(
                getController(),
                "GetFilesByUser",
                Communicator.HttpMethod.POST,
                createIdObject(id),
                new TypeToken<Iterable<CFile>>(){}.getType());
    }

    public Iterable<CFile> getFilesInRoom(int id) throws IOException, URISyntaxException {
        return communicator.Obtain(
                getController(),
                "GetFilesInRoom",
                Communicator.HttpMethod.POST,
                createIdObject(id),
                new TypeToken<Iterable<CFile>>(){}.getType());
    }

    public byte[] getFileContents(byte[] uuid) throws IOException, URISyntaxException {
        return communicator.Obtain(
                getController(),
                "getFileContents",
                Communicator.HttpMethod.POST,
                createUUIDObject(uuid),
                byte[].class);
    }

    public Map<byte[],byte[]> getFilesContents(byte[] uuid) throws IOException, URISyntaxException {
        return communicator.Obtain(
                getController(),
                "getFilesContents",
                Communicator.HttpMethod.POST,
                createUUIDObject(uuid),
                new TypeToken<Map<byte[],byte[]>>(){}.getType());
    }

    private Object createUUIDObject(byte[] uuid){
        return new UUIDObject(uuid);
    }

    private class UUIDObject {

        private String Login = getLoginHeader().getLogin();
        private String  Password = getLoginHeader().getPassword();
        private byte[] UUID;

        public UUIDObject(byte[] uuid){
            this.UUID = uuid;
        }
    }
}
