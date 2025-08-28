package com.codility.hateoas.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName(“Task Entity Tests”)
class TaskTest {

```
private Task task;
private LocalDate testDate;

@BeforeEach
void setUp() {
testDate = LocalDate.of(2022, 7, 27);
task = new Task();
}

@Test
@DisplayName("Should create task with default constructor")
void testDefaultConstructor() {
Task newTask = new Task();
assertNotNull(newTask);
assertNull(newTask.getId());
assertNull(newTask.getTaskId());
assertNull(newTask.getAuthor());
assertNull(newTask.getContent());
assertNull(newTask.getCreateDate());
}

@Test
@DisplayName("Should create task with parameterized constructor")
void testParameterizedConstructor() {
Task newTask = new Task(1L, "john.doe", testDate, "Test content");

assertNotNull(newTask);
assertEquals(1L, newTask.getTaskId());
assertEquals("john.doe", newTask.getAuthor());
assertEquals(testDate, newTask.getCreateDate());
assertEquals("Test content", newTask.getContent());
}

@Test
@DisplayName("Should set and get all properties correctly")
void testGettersAndSetters() {
task.setId(100L);
task.setTaskId(1L);
task.setAuthor("peggy carter");
task.setCreateDate(testDate);
task.setContent("captain britain");
task.setTitle("Strategic Planning");
task.setDescription("Plan operations");
task.setStatus("OPEN");

assertEquals(100L, task.getId());
assertEquals(1L, task.getTaskId());
assertEquals("peggy carter", task.getAuthor());
assertEquals(testDate, task.getCreateDate());
assertEquals("captain britain", task.getContent());
assertEquals("Strategic Planning", task.getTitle());
assertEquals("Plan operations", task.getDescription());
assertEquals("OPEN", task.getStatus());
}

@Test
@DisplayName("Should build task using builder pattern")
void testBuilderPattern() {
Task builtTask = Task.builder()
.taskId(2L)
.author("steve rogers")
.createDate(testDate)
.content("captain america")
.title("Team Coordination")
.description("Coordinate activities")
.status("IN_PROGRESS")
.build();

assertNotNull(builtTask);
assertEquals(2L, builtTask.getTaskId());
assertEquals("steve rogers", builtTask.getAuthor());
assertEquals(testDate, builtTask.getCreateDate());
assertEquals("captain america", builtTask.getContent());
assertEquals("Team Coordination", builtTask.getTitle());
assertEquals("Coordinate activities", builtTask.getDescription());
assertEquals("IN_PROGRESS", builtTask.getStatus());
}

@Test
@DisplayName("Should set current date when createDate is null in builder")
void testBuilderWithNullCreateDate() {
Task builtTask = Task.builder()
.taskId(3L)
.author("tony stark")
.content("iron man")
.build();

assertNotNull(builtTask);
assertNotNull(builtTask.getCreateDate());
assertEquals(LocalDate.now(), builtTask.getCreateDate());
}

@Test
@DisplayName("Should convert task to DTO correctly")
void testToDto() {
task.setId(1L);
task.setTaskId(2L);
task.setAuthor("natasha romanoff");
task.setCreateDate(testDate);
task.setContent("black widow");
task.setTitle("Intelligence");
task.setDescription("Gather intel");
task.setStatus("COMPLETED");

TaskDto dto = task.toDto();

assertNotNull(dto);
assertEquals(1L, dto.getId());
assertEquals(2L, dto.getTaskId());
assertEquals("natasha romanoff", dto.getAuthor());
assertEquals(testDate, dto.getCreateDate());
assertEquals("black widow", dto.getContent());
assertEquals("Intelligence", dto.getTitle());
assertEquals("Gather intel", dto.getDescription());
assertEquals("COMPLETED", dto.getStatus());
}

@Test
@DisplayName("Should handle null values in toDto")
void testToDtoWithNullValues() {
task.setId(1L);
task.setTaskId(2L);
task.setAuthor("test author");
task.setCreateDate(testDate);
task.setContent("test content");
// title, description, status remain null

TaskDto dto = task.toDto();

assertNotNull(dto);
assertEquals(1L, dto.getId());
assertEquals(2L, dto.getTaskId());
assertEquals("test author", dto.getAuthor());
assertEquals(testDate, dto.getCreateDate());
assertEquals("test content", dto.getContent());
assertNull(dto.getTitle());
assertNull(dto.getDescription());
assertNull(dto.getStatus());
}

@Test
@DisplayName("Should build minimal task with required fields only")
void testBuilderWithMinimalFields() {
Task minimalTask = Task.builder()
.taskId(1L)
.author("minimal author")
.content("minimal content")
.build();

assertNotNull(minimalTask);
assertEquals(1L, minimalTask.getTaskId());
assertEquals("minimal author", minimalTask.getAuthor());
assertEquals("minimal content", minimalTask.getContent());
assertEquals(LocalDate.now(), minimalTask.getCreateDate());
assertNull(minimalTask.getTitle());
assertNull(minimalTask.getDescription());
assertNull(minimalTask.getStatus());
}

@Test
@DisplayName("Should handle builder method chaining")
void testBuilderMethodChaining() {
Task chainedTask = Task.builder()
.taskId(1L)
.author("chain author")
.content("chain content")
.title("chain title")
.description("chain description")
.status("OPEN")
.createDate(testDate);

// Verify the builder returns itself for method chaining
assertNotNull(chainedTask);
assertTrue(chainedTask instanceof Task.TaskBuilder);

Task finalTask = chainedTask.build();
assertNotNull(finalTask);
assertEquals("chain author", finalTask.getAuthor());
assertEquals("OPEN", finalTask.getStatus());
}
```

}

