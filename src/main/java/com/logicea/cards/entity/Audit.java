package com.logicea.cards.entity;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class Audit<T> {
    @CreatedBy
    @Column(name = "CreatedBy", nullable = false, unique = false, columnDefinition = "VARCHAR(30)")
    protected T CreatedBy;
    @LastModifiedBy
    @Column(name = "UpdatedBy", nullable = false, unique = false, columnDefinition = "VARCHAR(30)")
    protected T UpdatedBy;
    @CreatedDate
    @Column(name = "CreatedAt", nullable = false)
    protected Date CreatedAt;
    @LastModifiedDate
    @Column(name = "UpdatedAt", nullable = false)
    protected Date UpdatedAt;

    public Date getCreatedAt() {
        return CreatedAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.CreatedAt = createdAt;
    }

    public T getCreatedBy() {
        return CreatedBy;
    }

    public void setCreatedBy(T createdBy) {
        this.CreatedBy = createdBy;
    }

    public Date getUpdatedAt() {
        return UpdatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.UpdatedAt = updatedAt;
    }

    public T getUpdatedBy() {
        return UpdatedBy;
    }

    public void setUpdatedBy(T updatedBy) {
        this.UpdatedBy = updatedBy;
    }
}