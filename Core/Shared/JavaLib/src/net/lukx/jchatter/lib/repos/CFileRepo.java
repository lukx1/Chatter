package net.lukx.jchatter.lib.repos;

import com.google.gson.reflect.TypeToken;
import net.lukx.jchatter.lib.PublicApi;
import net.lukx.jchatter.lib.comms.Communicable;
import net.lukx.jchatter.lib.comms.HttpMethod;
import net.lukx.jchatter.lib.models.CFile;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;

/***
 * Repo for obtaining files and their content
 * from the server
 */
@PublicApi
public class CFileRepo extends AbstractRepo{

    /***
     * Creates an instance of this class
     * @param communicable to send requests with
     */
    public CFileRepo(Communicable communicable) {
        super(communicable);
    }

    /***
     * {@inheritDoc}
     */
    @Override
    protected String getController() {
        return "CFile";
    }

    /***
     * Gets information about a file located on the server. Return a file or sends 404 if not found
     * @param uuid of file to obtain
     * @return file
     * @throws IOException if exception occurs
     * @throws URISyntaxException if uri is malformed
     */
    @PublicApi
    public CFile getFile(byte[] uuid) throws IOException, URISyntaxException {
        return communicable.Obtain(getController(),"File", HttpMethod.POST,createUUIDObject(uuid),CFile.class);
    }

    /***
     *Deletes a file. Return true if file has been deleted, false otherwise
     * @param uuid of the file
     * @return true if removed, false otherwise
     * @throws IOException if IOException occurs
     * @throws URISyntaxException if uri is malformed
     */
    @PublicApi
    public boolean removeFile(byte[] uuid) throws IOException, URISyntaxException {
        return communicable.Obtain(getController(),"File", HttpMethod.DELETE,createUUIDObject(uuid),boolean.class);
    }

    /***
     *Uploads a file to the server.Maximum content size is 3MB. File upload quotas apply. Empty files are not allowed. If the upload is successful the server responds with UUID assigned to the file.
     * @param Content data
     * @param file to add
     * @throws IOException if exception occurs
     * @throws URISyntaxException if uri is malformed
     */
    @PublicApi
    public byte[] addFile(byte[] Content, CFile file) throws IOException, URISyntaxException {
        return communicable.Obtain(
                getController(),
                "File",
                HttpMethod.PUT,
                createCustomObjectWithHeader(
                        new KeyValuePair("Content",Content),
                        new KeyValuePair("CFile",file)
                ),
                byte[].class
        );
    }

    /***
     * Updates information about a file. DOES NOT manipulate it's contents
     * @param file to update
     * @throws IOException if exception occurs
     * @throws URISyntaxException if uri is malformed
     */
    @PublicApi
    public void setFile(CFile file) throws IOException, URISyntaxException {
        communicable.Obtain(getController(),"File", HttpMethod.PATCH,file,void.class);
    }

    /***
     *Gets all files uploaded by a user. Does not download their contents.
     * @param id of the user
     * @return files
     * @throws IOException if exception occurs
     * @throws URISyntaxException if uri is malformed
     */
    @PublicApi
    public CFile[] getFilesByUser(int id) throws IOException, URISyntaxException {
        return communicable.Obtain(
                getController(),
                "GetFilesByUser",
                HttpMethod.POST,
                createIdObject(id),
                CFile[].class);
    }

    /***
     * Gets all files uploaded to a room. Does not download their contents.
     * @param id of the room
     * @return files
     * @throws IOException if exception occurs
     * @throws URISyntaxException if uri is malformed
     */
    @PublicApi
    public CFile[] getFilesInRoom(int id) throws IOException, URISyntaxException {
        return communicable.Obtain(
                getController(),
                "GetFilesInRoom",
                HttpMethod.POST,
                createIdObject(id),
                CFile[].class);
    }

    /***
     * Gets contents of a specified file
     * @param uuid of the file
     * @return contents
     * @throws IOException if exception occurs
     * @throws URISyntaxException if uri is malformed
     */
    @PublicApi
    public byte[] getFileContents(byte[] uuid) throws IOException, URISyntaxException {
        return communicable.Obtain(
                getController(),
                "getFileContents",
                HttpMethod.POST,
                createUUIDObject(uuid),
                byte[].class);
    }

    /***
     * Gets contents of specified files. Must request atleast one file to download
     * @param uuid of the files
     * @return byte[n,128] 2 Dimensional array of n UUIDs
     * @throws IOException if exception occurs
     * @throws URISyntaxException if uri is malformed
     */
    @PublicApi
    public Map<byte[],byte[]> getFilesContents(byte[] uuid) throws IOException, URISyntaxException {
        return communicable.Obtain(
                getController(),
                "getFilesContents",
                HttpMethod.POST,
                createUUIDObject(uuid),
                new TypeToken<Map<byte[],byte[]>>(){}.getType());
    }

    private Object createUUIDObject(byte[] uuid){
        return new UUIDObject(uuid);
    }

    @SuppressWarnings("unused")
    private class UUIDObject {

        private String Login = getLoginHeader().getLogin();
        private String  Password = getLoginHeader().getPassword();
        private byte[] UUID;

        UUIDObject(byte[] uuid){
            this.UUID = uuid;
        }
    }
}
