package com.apj.platform.fm.v1.controllers;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.apj.platform.fm.v1.commons.vo.SystemException;
import com.apj.platform.fm.v1.entities.FileMetadata;
import com.apj.platform.fm.v1.services.FileService;
import com.apj.platform.fm.v1.services.exceptions.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/api/v1/files")
public class FileController {
    private final FileService fileServices;

    FileController(FileService fileServices) {
        this.fileServices = fileServices;
    }

    @PostMapping
    public Long upload(@RequestParam("file") MultipartFile file,
            @RequestParam("path") String path,
            @RequestParam("type") String type,
            @RequestParam("name") String name) throws IOException, SystemException {
        return this.fileServices.createFile(file.getBytes(), path, type, name).getId();
    }

    @PutMapping("/{id}/overwrite")
    public FileMetadata overwrite(@PathVariable("id") Long id, @RequestParam("file") MultipartFile file)
            throws IOException, SystemException {
        return this.fileServices.overwrite(id, file.getBytes());
    }

    @PutMapping("/{id}")
    public FileMetadata update(@PathVariable("id") Long id, @RequestParam("path") String path,
            @RequestParam("name") String name) throws IOException, FileNotFoundException {
        return this.fileServices.update(id, path, name);
    }

    @GetMapping("/list")
    public List<FileMetadata> listAll() throws FileNotFoundException {
        return this.fileServices.listAll();
    }

    @GetMapping("/listByPath")
    public List<FileMetadata> listAllByPath(@RequestParam("path") String path) throws FileNotFoundException {
        return this.fileServices.listAllByPath(path);
    }

    @GetMapping("/listByType")
    public List<FileMetadata> listAllByType(@RequestParam("type") String type) throws FileNotFoundException {
        return this.fileServices.listAllByType(type);
    }

    @GetMapping("/{id}")
    public FileMetadata read(@PathVariable("id") Long id) throws FileNotFoundException {
        return this.fileServices.read(id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) throws SystemException {
        this.fileServices.delete(id);
    }

    @GetMapping(value = "/{id}/download", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public @ResponseBody byte[] download(@PathVariable("id") Long id) throws IOException, SystemException {
        return this.fileServices.download(id);
    }
}
