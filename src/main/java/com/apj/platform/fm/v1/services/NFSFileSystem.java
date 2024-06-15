package com.apj.platform.fm.v1.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

@Primary
@Component
@Profile("nfs")
public class NFSFileSystem implements IFileSystem {

    @Value("${rootPath:/}")
    private String rootPath;

    @Override
    public long upload(String user, long id, byte[] contents) throws IOException {
        Files.createDirectories(Paths.get(rootPath, user));
        Files.write(Paths.get(rootPath, user, String.valueOf(id)), contents, StandardOpenOption.CREATE,
                StandardOpenOption.TRUNCATE_EXISTING);
        return getSize(user, id);
    }

    @Override
    public long getSize(String user, long id) throws IOException {
        return Files.size(Paths.get(rootPath, user, String.valueOf(id)));
    }

    @Override
    public byte[] download(String user, long id) throws IOException {
        return Files.readAllBytes(Paths.get(rootPath, user, String.valueOf(id)));
    }

    @Override
    public void delete(String user, long id) throws IOException {
        Files.delete(Paths.get(rootPath, user, String.valueOf(id)));
    }

}
