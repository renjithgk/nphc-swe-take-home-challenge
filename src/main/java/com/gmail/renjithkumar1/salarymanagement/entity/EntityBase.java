package com.gmail.renjithkumar1.salarymanagement.entity;

import com.gmail.renjithkumar1.salarymanagement.utils.DtoEntity;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.Instant;

@MappedSuperclass
@Data
public abstract class EntityBase implements DtoEntity {

    @CreationTimestamp
    @Column(name = "created_at", updatable = false, nullable = false)
    protected Instant createdAt;

    @UpdateTimestamp
    @Column(name = "modified_at")
    protected Instant modifiedAt;
}

