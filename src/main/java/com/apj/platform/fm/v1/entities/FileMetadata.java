package com.apj.platform.fm.v1.entities;

import java.util.Date;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "files")
@EntityListeners(AuditingEntityListener.class)
public class FileMetadata {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_files")
    @SequenceGenerator(name = "seq_files", allocationSize = 1)
    private Long id;
    private String name;
    private String type;
    private String path;
    private long size;
    private String status;
    @CreatedBy
    private String createdBy;
    @LastModifiedBy
    private String modifiedBy;
    @CreatedDate
    private Date createdAt;
    @LastModifiedDate
    private Date modifiedAt;
}
