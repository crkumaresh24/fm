package com.apj.platform.fm.v1.services;

public interface IFileSystem {
    long upload(long id, byte[] contents);

    byte[] download(long id);

    void delete(long id);
}
