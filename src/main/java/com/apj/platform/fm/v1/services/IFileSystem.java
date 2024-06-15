package com.apj.platform.fm.v1.services;

import java.io.IOException;

public interface IFileSystem {
    long upload(String user, long id, byte[] contents) throws IOException;

    long getSize(String user, long id) throws IOException;

    byte[] download(String user, long id) throws IOException;

    void delete(String user, long id) throws IOException;
}
