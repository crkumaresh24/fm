package com.apj.platform.fm.v1.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.apj.platform.fm.v1.entities.FileMetadata;

@Repository
public interface FileRepo extends JpaRepository<FileMetadata, Long> {
    List<FileMetadata> findAllByCreatedByAndStatusNot(String createdBy, String status);

    List<FileMetadata> findAllByPathAndCreatedByAndStatusNot(String path, String createdBy, String status);

    List<FileMetadata> findAllByTypeAndCreatedByAndStatusNot(String path, String createdBy, String status);
}
