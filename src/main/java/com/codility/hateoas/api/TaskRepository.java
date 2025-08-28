package com.codility.hateoas.api;

import com.codility.hateoas.domain.Comment;
import com.codility.hateoas.domain.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

```
// Find task by taskId (business ID)
Optional<Task> findByTaskId(Long taskId);

// Check if task exists by taskId
boolean existsByTaskId(Long taskId);
```

}

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

```
// Find all comments for a specific task
List<Comment> findByTaskIdOrderByCreateDateDesc(Long taskId);

// Find a specific comment by task ID and comment ID
Optional<Comment> findByTaskIdAndId(Long taskId, Long commentId);

// Count comments for a task
long countByTaskId(Long taskId);

// Check if comment exists for task
boolean existsByTaskIdAndId(Long taskId, Long commentId);
```

}
