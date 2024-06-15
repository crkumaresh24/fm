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
    public long upload(long id, byte[] contents) throws IOException {
        Files.write(Paths.get(rootPath, String.valueOf(id)), contents, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        return getSize(id);
    }

    @Override
    public long getSize(long id) throws IOException {
        return Files.size(Paths.get(rootPath, String.valueOf(id)));
    }

    @Override
    public byte[] download(long id) throws IOException {
        return Files.readAllBytes(Paths.get(rootPath, String.valueOf(id)));
    }

    @Override
    public void delete(long id) throws IOException {
        Files.delete(Paths.get(rootPath, String.valueOf(id)));
    }

}
