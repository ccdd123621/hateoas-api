package com.codility.hateoas.config;

import com.codility.hateoas.api.CommentRepository;
import com.codility.hateoas.api.TaskRepository;
import com.codility.hateoas.domain.Comment;
import com.codility.hateoas.domain.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DataLoader implements CommandLineRunner {

```
private final TaskRepository taskRepository;
private final CommentRepository commentRepository;
```
