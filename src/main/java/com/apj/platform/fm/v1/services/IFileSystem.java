package com.apj.platform.fm.v1.services;

import java.io.IOException;

public interface IFileSystem {
    long upload(long id, byte[] contents) throws IOException;

    long getSize(long id) throws IOException;

    byte[] download(long id) throws IOException;

    void delete(long id) throws IOException;
}
