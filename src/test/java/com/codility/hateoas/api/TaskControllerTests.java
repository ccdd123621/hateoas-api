package com.codility.hateoas.api;

import com.codility.hateoas.domain.Task;
import com.codility.hateoas.domain.TaskDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

@WebMvcTest(TaskController.class)
@ActiveProfiles(“test”)
@DisplayName(“Task Controller Integration Tests”)
class TaskControllerTest {

```
@Autowired
private MockMvc mockMvc;

@MockBean
private TaskRepository taskRepository;

@MockBean
private CommentRepository commentRepository;

@Autowired
private ObjectMapper objectMapper;

private Task sampleTask;
private TaskDto sampleTaskDto;
private List<Task> sampleTasks;

@BeforeEach
void setUp() {
sampleTask = Task.builder()
.taskId(1L)
.author("peggy carter")
.createDate(LocalDate.of(2022, 7, 27))
.content("captain britain")
.title("Strategic Planning")
.description("Plan operations")
.status("OPEN")
.build();
sampleTask.setId(1L);

sampleTaskDto = new TaskDto(1L, 1L, "peggy carter",
LocalDate.of(2022, 7, 27), "captain britain",
"Strategic Planning", "Plan operations", "OPEN");

Task task2 = Task.builder()
.taskId(2L)
.author("steve rogers")
.createDate(LocalDate.of(2022, 7, 27))
.content("captain america")
.title("Team Coordination")
.status("IN_PROGRESS")
.build();
task2.setId(2L);

sampleTasks = Arrays.asList(sampleTask, task2);
}

@Test
@DisplayName("GET /task should return all tasks with HATEOAS links")
void testFindAll() throws Exception {
when(taskRepository.findAll()).thenReturn(sampleTasks);

mockMvc.perform(get("/task")
.contentType(MediaType.APPLICATION_JSON))
.andExpect(status().isOk())
.andExpect(content().contentType(MediaType.APPLICATION_JSON))
.andExpect(jsonPath("$._embedded.taskDtoList", hasSize(2)))
.andExpect(jsonPath("$._embedded.taskDtoList[0].id", is(1)))
.andExpect(jsonPath("$._embedded.taskDtoList[0].taskId", is(1)))
.andExpect(jsonPath("$._embedded.taskDtoList[0].author", is("peggy carter")))
.andExpect(jsonPath("$._embedded.taskDtoList[0].content", is("captain britain")))
.andExpect(jsonPath("$._embedded.taskDtoList[0]._links.self.href", containsString("/task/1")))
.andExpected(jsonPath("$._embedded.taskDtoList[0]._links.tasks.href", containsString("/task")))
.andExpected(jsonPath("$._embedded.taskDtoList[0]._links.comments.href", containsString("/task/1/comment")))
.andExpected(jsonPath("$._links.self.href", containsString("/task")));
}

@Test
@DisplayName("GET /task/{id} should return specific task with HATEOAS links")
void testFindById() throws Exception {
when(taskRepository.findById(1L)).thenReturn(Optional.of(sampleTask));

mockMvc.perform(get("/task/1")
.contentType(MediaType.APPLICATION_JSON))
.andExpect(status().isOk())
.andExpect(content().contentType(MediaType.APPLICATION_JSON))
.andExpect(jsonPath("$.id", is(1)))
.andExpect(jsonPath("$.taskId", is(1)))
.andExpect(jsonPath("$.author", is("peggy carter")))
.andExpected(jsonPath("$.content", is("captain britain")))
.andExpected(jsonPath("$.title", is("Strategic Planning")))
.andExpected(jsonPath("$.createDate", is("2022-07-27")))
.andExpected(jsonPath("$._links.self.href", containsString("/task/1")))
.andExpected(jsonPath("$._links.tasks.href", containsString("/task")))
.andExpected(jsonPath("$._links.comments.href", containsString("/task/1/comment")));
}

@Test
@DisplayName("GET /task/{id} should return 404 when task not found")
void testFindByIdNotFound() throws Exception {
when(taskRepository.findById(999L)).thenReturn(Optional.empty());

mockMvc.perform(get("/task/999")
.contentType(MediaType.APPLICATION_JSON))
.andExpect(status().isNotFound());
}

@Test
@DisplayName("POST /task should create new task and return 201 with HATEOAS links")
void testCreateTask() throws Exception {
TaskDto newTaskDto = new TaskDto();
newTaskDto.setTaskId(3L);
newTaskDto.setAuthor("tony stark");
newTaskDto.setContent("iron man upgrades");
newTaskDto.setTitle("Technology Enhancement");
newTaskDto.setDescription("Upgrade tech stack");

Task savedTask = Task.builder()
.taskId(3L)
.author("tony stark")
.content("iron man upgrades")
.title("Technology Enhancement")
.description("Upgrade tech stack")
.createDate(LocalDate.now())
.build();
savedTask.setId(3L);

when(taskRepository.existsByTaskId(3L)).thenReturn(false);
when(taskRepository.save(any(Task.class))).thenReturn(savedTask);

mockMvc.perform(post("/task")
.contentType(MediaType.APPLICATION_JSON)
.content(objectMapper.writeValueAsString(newTaskDto)))
.andExpect(status().isCreated())
.andExpect(content().contentType(MediaType.APPLICATION_JSON))
.andExpected(jsonPath("$.id", is(3)))
.andExpected(jsonPath("$.taskId", is(3)))
.andExpected(jsonPath("$.author", is("tony stark")))
.andExpected(jsonPath("$.content", is("iron man upgrades")))
.andExpected(jsonPath("$._links.self.href", containsString("/task/3")))
.andExpected(jsonPath("$._links.tasks.href", containsString("/task")))
.andExpected(jsonPath("$._links.comments.href", containsString("/task/3/comment")));
}

@Test
@DisplayName("POST /task should return 400 when taskId already exists")
void testCreateTaskWithExistingTaskId() throws Exception {
TaskDto newTaskDto = new TaskDto();
newTaskDto.setTaskId(1L);
newTaskDto.setAuthor("duplicate user");
newTaskDto.setContent("duplicate content");

when(taskRepository.existsByTaskId(1L)).thenReturn(true);

mockMvc.perform(post("/task")
.contentType(MediaType.APPLICATION_JSON)
.content(objectMapper.writeValueAsString(newTaskDto)))
.andExpect(status().isBadRequest());
}

@Test
@DisplayName("POST /task should validate required fields")
void testCreateTaskValidation() throws Exception {
TaskDto invalidTaskDto = new TaskDto();
// Missing required fields: taskId, author, content

mockMvc.perform(post("/task")
.contentType(MediaType.APPLICATION_JSON)
.content(objectMapper.writeValueAsString(invalidTaskDto)))
.andExpect(status().isBadRequest());
}

@Test
@DisplayName("POST /task should generate taskId when not provided")
void testCreateTaskWithoutTaskId() throws Exception {
TaskDto newTaskDto = new TaskDto();
newTaskDto.setAuthor("auto generated");
newTaskDto.setContent("auto content");

Task savedTask = Task.builder()
.taskId(System.currentTimeMillis())
.author("auto generated")
.content("auto content")
.createDate(LocalDate.now())
.build();
savedTask.setId(1L);

when(taskRepository.existsByTaskId(anyLong())).thenReturn(false);
when(taskRepository.save(any(Task.class))).thenReturn(savedTask);

mockMvc.perform(post("/task")
.contentType(MediaType.APPLICATION_JSON)
.content(objectMapper.writeValueAsString(newTaskDto)))
.andExpect(status().isCreated())
.andExpected(jsonPath("$.author", is("auto generated")))
.andExpected(jsonPath("$.content", is("auto content")))
.andExpected(jsonPath("$.taskId").exists());
}

@Test
@DisplayName("POST /task should handle optional fields correctly")
void testCreateTaskWithOptionalFields() throws Exception {
TaskDto newTaskDto = new TaskDto();
newTaskDto.setTaskId(4L);
newTaskDto.setAuthor("optional fields test");
newTaskDto.setContent("testing optional fields");
newTaskDto.setTitle("Optional Title");
newTaskDto.setDescription("Optional Description");
newTaskDto.setStatus("OPEN");

Task savedTask = Task.builder()
.taskId(4L)
.author("optional fields test")
.content("testing optional fields")
.title("Optional Title")
.description("Optional Description")
.status("OPEN")
.createDate(LocalDate.now())
.build();
savedTask.setId(4L);

when(taskRepository.existsByTaskId(4L)).thenReturn(false);
when(taskRepository.save(any(Task.class))).thenReturn(savedTask);

mockMvc.perform(post("/task")
.contentType(MediaType.APPLICATION_JSON)
.content(objectMapper.writeValueAsString(newTaskDto)))
.andExpect(status().isCreated())
.andExpected(jsonPath("$.title", is("Optional Title")))
.andExpected(jsonPath("$.description", is("Optional Description")))
.andExpected(jsonPath("$.status", is("OPEN")));
}

@Test
@DisplayName("Should handle invalid JSON in POST request")
void testCreateTaskWithInvalidJson() throws Exception {
mockMvc.perform(post("/task")
.contentType(MediaType.APPLICATION_JSON)
.content("{ invalid json }"))
.andExpect(status().isBadRequest());
}

@Test
@DisplayName("Should handle empty request body")
void testCreateTaskWithEmptyBody() throws Exception {
mockMvc.perform(post("/task")
.contentType(MediaType.APPLICATION_JSON)
.content(""))
.andExpect(status().isBadRequest());
}
```

}
