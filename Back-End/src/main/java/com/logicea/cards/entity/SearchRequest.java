package com.logicea.cards.entity;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

public class SearchRequest {

    @Column(nullable = true)
    private String name;

    @Column(nullable = true)
    private String color;

    @Column(nullable = true)
    private String status;

    @Column(nullable = true)
    private Date created_at;

    @Column(nullable = true)
    private String sortField;

    @Column(nullable = true)
    private int page;

    @Column(nullable = true)
    private int size;


    public SearchRequest(String name, String color, String status, Date date, String sortField, int page, int size) {
        this.name = name;
        this.color = color;
        this.status = status;
        this.created_at = date;
        this.sortField = sortField;
        this.page = page;
        this.size = size;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSortField() {
        return sortField;
    }

    public void setSortField(String sortField) {
        this.sortField = sortField;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
