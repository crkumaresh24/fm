package com.apj.platform.fm.v1.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.apj.platform.fm.v1.entities.FileMetadata;

@Repository
public interface FileRepo extends JpaRepository<FileMetadata, Long> {
    List<FileMetadata> findAllByCreatedBy(String createdBy);

    List<FileMetadata> findAllByPathAndCreatedBy(String path, String createdBy);

    List<FileMetadata> findAllByTypeAndCreatedBy(String path, String createdBy);
}
