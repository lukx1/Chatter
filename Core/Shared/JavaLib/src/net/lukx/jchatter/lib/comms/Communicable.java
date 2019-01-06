package net.lukx.jchatter.lib.comms;

import com.sun.istack.internal.NotNull;
import net.lukx.jchatter.lib.PublicApi;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.URISyntaxException;

public interface Communicable {
    @PublicApi
    <T> T Obtain(@NotNull String controller, @NotNull String action, Communicator.HttpMethod method, Object inData, Type type) throws IOException, URISyntaxException;

    @PublicApi
    <T> T Obtain(@NotNull String controller, @NotNull String action, Communicator.HttpMethod method, Object inData, Class<T> clazz) throws IOException, URISyntaxException;

    @PublicApi
    void Close() throws IOException;

    @PublicApi
    URI getServerURI();

    @PublicApi
    void setServerURI(URI serverURI);
}
