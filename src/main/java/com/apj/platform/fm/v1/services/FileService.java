package com.apj.platform.fm.v1.services;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.apj.platform.fm.v1.commons.vo.SystemException;
import com.apj.platform.fm.v1.constants.FileStatus;
import com.apj.platform.fm.v1.entities.FileMetadata;
import com.apj.platform.fm.v1.repos.FileRepo;
import com.apj.platform.fm.v1.services.exceptions.FileNotFoundException;
import com.apj.platform.fm.v1.services.exceptions.UploadFailedException;

import lombok.extern.slf4j.Slf4j;

import com.apj.platform.fm.v1.services.exceptions.DeleteFailedException;
import com.apj.platform.fm.v1.services.exceptions.DownloadFailedException;

@Slf4j
@Service
public class FileService {

    private final FileRepo fileRepo;
    private final IFileSystem fileSystem;
    private final UsernameAuditorAware usernameAuditorAware;

    public FileService(FileRepo fileRepo, IFileSystem fileSystem) {
        this.fileRepo = fileRepo;
        this.fileSystem = fileSystem;
        this.usernameAuditorAware = new UsernameAuditorAware();
    }

    public FileMetadata createFile(byte[] contents, String path, String type, String name)
            throws SystemException {
        FileMetadata fileMetadata = new FileMetadata();
        fileMetadata.setPath(path);
        fileMetadata.setType(type);
        fileMetadata.setName(name);
        fileMetadata.setStatus(FileStatus.CREATED);
        fileMetadata = this.fileRepo.save(fileMetadata);
        try {
            long size = this.fileSystem.upload(fileMetadata.getCreatedBy(), fileMetadata.getId(), contents);
            fileMetadata.setStatus(FileStatus.UPLOAD_SUCCESS);
            fileMetadata.setSize(size);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            fileMetadata.setStatus(FileStatus.UPLOAD_FAILED);
            this.fileRepo.save(fileMetadata);
            throw new UploadFailedException(String.valueOf(fileMetadata.getId()), e.getMessage());
        }
        return this.fileRepo.save(fileMetadata);
    }

    public FileMetadata overwrite(Long id, byte[] contents) throws SystemException {
        FileMetadata fileMetadata = read(id);
        fileMetadata.setStatus(FileStatus.OVERWRITE_IN_PROGRESS);
        fileMetadata = this.fileRepo.save(fileMetadata);
        try {
            delete(fileMetadata);
            long size = this.fileSystem.upload(fileMetadata.getCreatedBy(), fileMetadata.getId(), contents);
            fileMetadata.setStatus(FileStatus.UPLOAD_SUCCESS);
            fileMetadata.setSize(size);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            fileMetadata.setStatus(FileStatus.OVERWRITE_FAILED);
            this.fileRepo.save(fileMetadata);
            throw new UploadFailedException(String.valueOf(fileMetadata.getId()), e.getMessage());
        }
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

    public void delete(Long id) throws SystemException {
        delete(read(id));
    }

    private void delete(FileMetadata fileMetadata) throws SystemException {
        String status = fileMetadata.getStatus();
        fileMetadata.setStatus(FileStatus.DELETE_IN_PROGRESS);
        fileMetadata = this.fileRepo.save(fileMetadata);
        try {
            this.fileSystem.delete(fileMetadata.getCreatedBy(), fileMetadata.getId());
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            fileMetadata.setStatus(status);
            fileMetadata = this.fileRepo.save(fileMetadata);
            throw new DeleteFailedException(String.valueOf(fileMetadata.getId()), e.getMessage());
        }
        fileMetadata.setSize(0);
        fileMetadata.setStatus(FileStatus.DELETED);
        fileMetadata = this.fileRepo.save(fileMetadata);
    }

    public byte[] download(Long id) throws SystemException {
        FileMetadata fileMetadata = read(id);
        byte[] contents = null;
        try {
            contents = this.fileSystem.download(fileMetadata.getCreatedBy(), fileMetadata.getId());
        } catch (Exception e) {
            throw new DownloadFailedException(String.valueOf(id), e.getMessage());
        }
        return contents;
    }

    public List<FileMetadata> listAll() {
        String username = this.usernameAuditorAware.getCurrentAuditor().get();
        return this.fileRepo.findAllByCreatedByAndStatusNot(username, FileStatus.DELETED);
    }

    public List<FileMetadata> listAllByPath(String path) {
        String username = this.usernameAuditorAware.getCurrentAuditor().get();
        return this.fileRepo.findAllByPathAndCreatedByAndStatusNot(path, username, FileStatus.DELETED);
    }

    public List<FileMetadata> listAllByType(String type) {
        String username = this.usernameAuditorAware.getCurrentAuditor().get();
        return this.fileRepo.findAllByTypeAndCreatedByAndStatusNot(type, username, FileStatus.DELETED);
    }
}
