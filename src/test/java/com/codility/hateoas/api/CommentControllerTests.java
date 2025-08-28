package com.codility.hateoas.api;

import com.codility.hateoas.domain.Comment;
import com.codility.hateoas.domain.CommentDto;
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
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

@WebMvcTest(CommentController.class)
@ActiveProfiles(“test”)
@DisplayName(“Comment Controller Integration Tests”)
class CommentControllerTest {

```
@Autowired
private MockMvc mockMvc;

@MockBean
private TaskRepository taskRepository;

@MockBean
private CommentRepository commentRepository;

@Autowired
private ObjectMapper objectMapper;

private Comment sampleComment1;
private Comment sampleComment2;
private List<Comment> sampleComments;
private final Long taskId = 1L;

@BeforeEach
void setUp() {
sampleComment1 = new Comment();
sampleComment1.setId(1L);
sampleComment1.setTaskId(taskId);
sampleComment1.setAuthor("natasha romanoff");
sampleComment1.setCreateDate(LocalDate.of(2022, 7, 28));
sampleComment1.setContent("This task requires careful consideration");

sampleComment2 = new Comment();
sampleComment2.setId(2L);
sampleComment2.setTaskId(taskId);
sampleComment2.setAuthor("clint barton");
sampleComment2.setCreateDate(LocalDate.of(2022, 7, 29));
sampleComment2.setContent("I agree with the strategic approach");

sampleComments = Arrays.asList(sampleComment1, sampleComment2);
}

@Test
@DisplayName("GET /task/{taskId}/comment should return all comments for task with HATEOAS links")
void testFindAllForTask() throws Exception {
when(taskRepository.existsById(taskId)).thenReturn(true);
when(commentRepository.findByTaskIdOrderByCreateDateDesc(taskId)).thenReturn(sampleComments);

mockMvc.perform(get("/task/{taskId}/comment", taskId)
.contentType(MediaType.APPLICATION_JSON))
.an
```
