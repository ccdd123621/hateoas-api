package com.codility.hateoas.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Table(name = “tasks”)
public class Task {

```
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;

@Column(name = "task_id", unique = true)
@NotNull
private Long taskId;

@Column(name = "author")
@NotBlank
private String author;

@Column(name = "create_date")
@NotNull
private LocalDate createDate;

@Column(name = "content", length = 1000)
@NotBlank
private String content;

@Column(name = "title")
private String title;

@Column(name = "description")
private String description;

@Column(name = "status")
private String status;

// Default constructor
public Task() {}

// Constructor with required fields
public Task(Long taskId, String author, LocalDate createDate, String content) {
this.taskId = taskId;
this.author = author;
this.createDate = createDate;
this.content = content;
}

// Builder pattern
public static TaskBuilder builder() {
return new TaskBuilder();
}

// Getters and setters
public Long getId() { return id; }
public void setId(Long id) { this.id = id; }

public Long getTaskId() { return taskId; }
public void setTaskId(Long taskId) { this.taskId = taskId; }

public String getAuthor() { return author; }
public void setAuthor(String author) { this.author = author; }

public LocalDate getCreateDate() { return createDate; }
public void setCreateDate(LocalDate createDate) { this.createDate = createDate; }

public String getContent() { return content; }
public void setContent(String content) { this.content = content; }

public String getTitle() { return title; }
public void setTitle(String title) { this.title = title; }

public String getDescription() { return description; }
public void setDescription(String description) { this.description = description; }

public String getStatus() { return status; }
public void setStatus(String status) { this.status = status; }

// Convert to DTO
public TaskDto toDto() {
return new TaskDto(this.id, this.taskId, this.author, this.createDate, this.content,
this.title, this.description, this.status);
}

// Builder class
public static class TaskBuilder {
private Long taskId;
private String author;
private LocalDate createDate;
private String content;
private String title;
private String description;
private String status;

public TaskBuilder taskId(Long taskId) {
this.taskId = taskId;
return this;
}
