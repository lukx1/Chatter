package net.lukx.jchatter.java.supporting;

import com.sun.org.apache.xml.internal.security.exceptions.Base64DecodingException;
import com.sun.org.apache.xml.internal.security.utils.Base64;
import net.lukx.jchatter.lib.models.CFile;
import net.lukx.jchatter.lib.repos.CFileRepo;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CFileResolver {

    private CFileRepo repo;
    private Pattern filePattern = Pattern.compile("!<.{24}>!");

    public CFileResolver(CFileRepo cFileRepo){
        this.repo = cFileRepo;
    }

    public Iterable<byte[]> resolveFileNamesFromMessage(String message) throws Base64DecodingException {
        Matcher m = filePattern.matcher(message);
        List<byte[]> matches = new ArrayList<>();
        while (m.find()){
            matches.add(Base64.decode(m.group().substring(2,26)));
        }
        return matches;
    }

    public Iterable<CFile> fetchCFiles(Iterable<byte[]> uuids) throws IOException, URISyntaxException {
        List<CFile> files = new ArrayList<>();
        for (byte[] uuid : uuids) {
                files.add((repo.getFile(uuid)));
        }
        return files;
    }

}
