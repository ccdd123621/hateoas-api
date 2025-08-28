package com.codility.hateoas.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public class TaskDto {

```
private Long id;

@NotNull
private Long taskId;

@NotBlank
private String author;

@NotNull
@JsonFormat(pattern = "yyyy-MM-dd")
private LocalDate createDate;

@NotBlank
private String content;

private String title;

private String description;

private String status;

// Default constructor
public TaskDto() {}

// Constructor with all fields
public TaskDto(Long id, Long taskId, String author, LocalDate createDate,
String content, String title, String description, String status) {
this.id = id;
this.taskId = taskId;
this.author = author;
this.createDate = createDate;
this.content = content;
this.title = title;
this.description = description;
this.status = status;
}

// Constructor for minimal fields (for backwards compatibility)
public TaskDto(Long id, Long taskId, String author, LocalDate createDate, String content) {
this(id, taskId, author, createDate, content, null, null, null);
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

// Helper methods
public boolean hasStatus() {
return status != null && !status.trim().isEmpty();
}

public String taskStatus() {
return status != null ? status : "OPEN";
}
```

}
