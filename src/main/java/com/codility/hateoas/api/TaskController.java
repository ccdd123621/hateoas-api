package com.codility.hateoas.api;

import com.codility.hateoas.domain.Comment;
import com.codility.hateoas.domain.CommentDto;
import com.codility.hateoas.domain.Task;
import com.codility.hateoas.domain.TaskDto;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.CollectionModel;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping(”/task”)
public class TaskController {

```
private final TaskRepository repository;

@Autowired
public TaskController(TaskRepository repository) {
this.repository = repository;
}

@GetMapping
public CollectionModel<EntityModel<TaskDto>> findAll() {
List<EntityModel<TaskDto>> tasks = repository.findAll().stream()
.map(task -> {
TaskDto taskDto = task.toDto();
EntityModel<TaskDto> taskModel = EntityModel.of(taskDto);

// Add self link
taskModel.add(linkTo(methodOn(TaskController.class).findById(task.getId())).withSelfRel());

// Add task-specific link
taskModel.add(linkTo(methodOn(TaskController.class).findById(task.getId())).withRel("task"));

// Add comments link if task has comments
taskModel.add(linkTo(methodOn(TaskController.class).findAllForTask(task.getId())).withRel("comments"));

return taskModel;
})
.collect(Collectors.toList());

return CollectionModel.of(tasks)
.add(linkTo(methodOn(TaskController.class).findAll()).withSelfRel());
}

@GetMapping("/{taskId}")
public EntityModel<TaskDto> findById(@PathVariable Long taskId) {
Optional<Task> task = repository.findById(taskId);

if (task.isPresent()) {
TaskDto taskDto = task.get().toDto();
EntityModel<TaskDto> taskModel = EntityModel.of(taskDto);

// Add self link
taskModel.add(linkTo(methodOn(TaskController.class).findById(taskId)).withSelfRel());

// Add link to all tasks
taskModel.add(linkTo(methodOn(TaskController.class).findAll()).withRel("tasks"));

// Add comments link
taskModel.add(linkTo(methodOn(TaskController.class).findAllForTask(taskId)).withRel("comments"));

return taskModel;
} else {
throw new TaskNotFoundException("Task not found with id: " + taskId);
}
}

@GetMapping("/{taskId}/comment")
public CollectionModel<EntityModel<CommentDto>> findAllForTask(@PathVariable Long taskId) {
List<EntityModel<CommentDto>> comments = repository.findAllByTaskId(taskId).stream()
.map(comment -> {
CommentDto commentDto = comment.toDto();
EntityModel<CommentDto> commentModel = EntityModel.of(commentDto);

// Add self link to individual comment
commentModel.add(linkTo(methodOn(TaskController.class).findByTaskIdAndCommentId(taskId, comment.getId())).withSelfRel());

// Add link back to the task
commentModel.add(linkTo(methodOn(TaskController.class).findById(taskId)).withRel("task"));

return commentModel;
})
.collect(Collectors.toList());

return CollectionModel.of(comments)
.add(linkTo(methodOn(TaskController.class).findAllForTask(taskId)).withSelfRel())
.add(linkTo(methodOn(TaskController.class).findById(taskId)).withRel("task"));
}

@GetMapping("/{taskId}/comment/{commentId}")
public EntityModel<CommentDto> findByTaskIdAndCommentId(@PathVariable Long taskId, @PathVariable Long commentId) {
Optional<Comment> comment = repository.findByTaskIdAndCommentId(taskId, commentId);

if (comment.isPresent()) {
CommentDto commentDto = comment.get().toDto();
EntityModel<CommentDto> commentModel = EntityModel.of(commentDto);

// Add self link
commentModel.add(linkTo(methodOn(TaskController.class).findByTaskIdAndCommentId(taskId, commentId)).withSelfRel());

// Add link to task
commentModel.add(linkTo(methodOn(TaskController.class).findById(taskId)).withRel("task"));

// Add link to all comments for this task
commentModel.add(linkTo(methodOn(TaskController.class).findAllForTask(taskId)).withRel("comments"));

return commentModel;
} else {
throw new CommentNotFoundException("Comment not found");
}
}

@PostMapping
public EntityModel<TaskDto> create(@RequestBody TaskDto dto) {
Task.TaskBuilder builder = Task.builder()
.title(dto.getTitle())
.description(dto.getDescription());

if (dto.hasStatus()) {
builder.status(dto.taskStatus());
}

Task savedTask = repository.save(builder.build());
TaskDto taskDto = savedTask.toDto();

EntityModel<TaskDto> taskModel = EntityModel.of(taskDto);
taskModel.add(linkTo(methodOn(TaskController.class).findById(savedTask.getId())).withSelfRel());
taskModel.add(linkTo(methodOn(TaskController.class).findAll()).withRel("tasks"));
taskModel.add(linkTo(methodOn(TaskController.class).findAllForTask(savedTask.getId())).withRel("comments"));

return taskModel;
}
```

}
