package com.igorkucher.toDoApp.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class TodoDto {
    private long id;

    @NotBlank(message = "The 'title' cannot be empty")
    private String title;

    @NotNull
    private String createdAt;

    @NotNull
    private long ownerId;


    public TodoDto() {
    }

    public TodoDto(long id, String title, String createdAt, long ownerId) {
        this.id = id;
        this.title = title;
        this.createdAt = createdAt;
        this.ownerId = ownerId;
    }



    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(long ownerId) {
        this.ownerId = ownerId;
    }
}
