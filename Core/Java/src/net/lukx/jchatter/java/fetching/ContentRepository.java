package net.lukx.jchatter.java.fetching;

import com.sun.org.apache.xml.internal.security.utils.Base64;
import javafx.scene.image.Image;
import net.lukx.jchatter.lib.repos.CFileRepo;

import java.io.*;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.Objects;

public class ContentRepository {

    private CFileRepo fileRepo;

    private File storeDir;

    public ContentRepository(CFileRepo fileRepo, File imageStoreDir) {
        if(!imageStoreDir.exists()){
            imageStoreDir.mkdirs();
        }
        this.fileRepo = fileRepo;
        this.storeDir = imageStoreDir;
    }

    public File hasLocal(byte[] uuid){
        String b64 = Base64.encode(uuid);
        for (File f : Objects.requireNonNull(storeDir.listFiles())) {
            if(f.getName().equals(b64)){
                return f;
            }
        }
        return null;
    }

    public File fetchAndSave(byte[] uuid) throws IOException, URISyntaxException {
        byte[] contents = fileRepo.getFileContents(uuid);
        File file = new File(Paths.get(storeDir.getAbsolutePath(),Base64.encode(uuid)).toUri());
        if(contents == null){
            return null;
        }
        try(DataOutputStream w = new DataOutputStream(new FileOutputStream(file))){
            w.write(contents);
        }
        return file;
    }

    public File fetchFile(byte[] uuid) throws IOException, URISyntaxException {
        return fetchFile(uuid,false);
    }

    public File fetchFile(byte[] uuid, boolean autosave) throws IOException, URISyntaxException {
        File f;
        if((f = hasLocal(uuid)) != null){
            return f;
        }
        else if((f = fetchAndSave(uuid)) != null && autosave){
            return f;
        }
        else {
            throw new FileNotFoundException("File not found locally or on server");
        }
    }

    public Image fetchImage(byte[] uuid) throws IOException, URISyntaxException {
        File f;
        if((f = hasLocal(uuid)) != null){
            return new Image("file:///"+f.toURI().toString());
        }
        else if((f = fetchAndSave(uuid)) != null) {
            return new Image("file:///"+f.toURI().toString());
        }
        else {
            throw new FileNotFoundException("Image not found locally or on server");
        }
    }

    public Image getFallbackImage(){
        return new Image("/pictures/nopicture.png");
    }

    public Image fetchImageWithFallback(byte[] uuid){
        if(uuid == null){
            return getFallbackImage();
        }
        Image image;
        try {
            image = fetchImage(uuid);
            if (image != null) {
                return image;
            }
        }
        catch(IOException | URISyntaxException e){
            return getFallbackImage();
        }
        return getFallbackImage();
    }

    public File getStoreDir() {
        return storeDir;
    }

    public void setStoreDir(File storeDir) {
        this.storeDir = storeDir;
    }
}
