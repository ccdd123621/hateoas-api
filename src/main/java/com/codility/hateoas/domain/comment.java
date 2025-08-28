package com.codility.hateoas.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Table(name = “comments”)
public class Comment {

```
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;

@Column(name = "task_id")
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

// Default constructor
public Comment() {}

// Constructor with required fields
public Comment(Long taskId, String author, LocalDate createDate, String content) {
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

// Convert to DTO
public CommentDto toDto() {
return new CommentDto(this.id, this.taskId, this.author, this.createDate, this.content);
}
```

}
