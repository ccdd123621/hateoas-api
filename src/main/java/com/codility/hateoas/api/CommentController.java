package com.codility.hateoas.api;

import com.codility.hateoas.domain.Comment;
import com.codility.hateoas.domain.CommentDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping(”/task/{taskId}/comment”)
public class CommentController {

```
private final TaskRepository taskRepository;
private final CommentRepository commentRepository;

@Autowired
public CommentController(TaskRepository taskRepository, CommentRepository commentRepository) {
this.taskRepository = taskRepository;
this.commentRepository = commentRepository;
}

@GetMapping
public CollectionModel<EntityModel<CommentDto>> findAllForTask(@PathVariable Long taskId) {
// Verify task exists
if (!taskRepository.existsById(taskId)) {
throw new TaskNotFoundException(taskId);
}

List<EntityModel<CommentDto>> comments = commentRepository.findByTaskIdOrderByCreateDateDesc(taskId)
.stream()
.map(comment -> toEntityModel(comment, taskId))
.collect(Collectors.toList());

return CollectionModel.of(comments)
.add(linkTo(methodOn(CommentController.class).findAllForTask(taskId)).withSelfRel())
.add(linkTo(methodOn(TaskController.class).findById(taskId)).withRel("task"));
}

@GetMapping("/{commentId}")
public EntityModel<CommentDto> findByTaskIdAndCommentId(@PathVariable Long taskId, @PathVariable Long commentId) {
// Verify task exists
if (!taskRepository.existsById(taskId)) {
throw new TaskNotFoundException(taskId);
}

Comment comment = commentRepository.findByTaskIdAndId(taskId, commentId)
.orElseThrow(() -> new CommentNotFoundException(taskId, commentId));

return toEntityModel(comment, taskId);
}

@PostMapping
public ResponseEntity<EntityModel<CommentDto>> createComment(@PathVariable Long taskId,
@Valid @RequestBody CommentDto dto) {
// Verify task exists
if (!taskRepository.existsById(taskId)) {
throw new TaskNotFoundException(taskId);
}

Comment comment = new Comment();
comment.setTaskId(taskId);
comment.setAuthor(dto.getAuthor());
comment.setContent(dto.getContent());
comment.setCreateDate(dto.getCreateDate() != null ? dto.getCreateDate() : LocalDate.now());

Comment savedComment = commentRepository.save(comment);

return ResponseEntity.status(HttpStatus.CREATED)
.body(toEntityModel(savedComment, taskId));
}

private EntityModel<CommentDto> toEntityModel(Comment comment, Long taskId) {
CommentDto commentDto = comment.toDto();
EntityModel<CommentDto> commentModel = EntityModel.of(commentDto);

// Add self link
commentModel.add(linkTo(methodOn(CommentController.class)
.findByTaskIdAndCommentId(taskId, comment.getId())).withSelfRel());

// Add link to task
commentModel.add(linkTo(methodOn(TaskController.class).findById(taskId)).withRel("task"));

// Add link to all comments for this task
commentModel.add(linkTo(methodOn(CommentController.class).findAllForTask(taskId)).withRel("comments"));

return commentModel;
}
```

}
