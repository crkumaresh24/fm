package com.apj.platform.fm.v1.services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.apj.platform.fm.v1.entities.FileMetadata;
import com.apj.platform.fm.v1.repos.FileRepo;
import com.apj.platform.fm.v1.services.exceptions.FileNotFoundException;

@Service
public class FileService {

    private final FileRepo fileRepo;
    private final UsernameAuditorAware usernameAuditorAware;

    public FileService(FileRepo fileRepo) {
        this.fileRepo = fileRepo;
        this.usernameAuditorAware = new UsernameAuditorAware();
    }

    public FileMetadata createFile(byte[] contents, String path, String type, String name) {
        FileMetadata fileMetadata = new FileMetadata();
        fileMetadata.setPath(path);
        fileMetadata.setType(type);
        fileMetadata.setName(name);
        fileMetadata.setSize(contents.length);
        return this.fileRepo.save(fileMetadata);
    }

    public FileMetadata overwrite(Long id, byte[] bytes) throws FileNotFoundException {
        FileMetadata fileMetadata = read(id);
        fileMetadata.setSize(bytes.length);
        return this.fileRepo.save(fileMetadata);
    }

    public FileMetadata update(Long id, String path, String name) throws FileNotFoundException {
        FileMetadata fileMetadata = read(id);
        if (StringUtils.hasText(path)) {
            fileMetadata.setPath(path);
        }
        if (StringUtils.hasText(name)) {
            fileMetadata.setName(name);
        }
        return this.fileRepo.save(fileMetadata);
    }

    public FileMetadata read(Long id) throws FileNotFoundException {
        return this.fileRepo.findById(id).orElseThrow(() -> new FileNotFoundException(id));
    }

    public void delete(Long id) {
        this.fileRepo.deleteById(id);
    }

    public byte[] download(Long id) throws FileNotFoundException {
        FileMetadata fileMetadata = read(id);
        return "test".getBytes();
    }

    public List<FileMetadata> listAll() {
        String username = this.usernameAuditorAware.getCurrentAuditor().get();
        return this.fileRepo.findAllByCreatedBy(username);
    }

    public List<FileMetadata> listAllByPath(String path) {
        String username = this.usernameAuditorAware.getCurrentAuditor().get();
        return this.fileRepo.findAllByPathAndCreatedBy(path, username);
    }

    public List<FileMetadata> listAllByType(String type) {
        String username = this.usernameAuditorAware.getCurrentAuditor().get();
        return this.fileRepo.findAllByTypeAndCreatedBy(type, username);
    }
}
