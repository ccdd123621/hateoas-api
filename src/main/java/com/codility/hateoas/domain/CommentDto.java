package com.codility.hateoas.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public class CommentDto {

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

// Default constructor
public CommentDto() {}

// Constructor with all fields
public CommentDto(Long id, Long taskId, String author, LocalDate createDate, String content) {
this.id = id;
this.taskId = taskId;
this.author = author;
this.createDate = createDate;
this.content = content;
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
```

}
