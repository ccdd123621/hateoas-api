package com.codility.hateoas.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName(“Comment Entity Tests”)
class CommentTest {

```
private Comment comment;
private LocalDate testDate;

@BeforeEach
void setUp() {
testDate = LocalDate.of(2022, 7, 28);
comment = new Comment();
}

@Test
@DisplayName("Should create comment with default constructor")
void testDefaultConstructor() {
Comment newComment = new Comment();
assertNotNull(newComment);
assertNull(newComment.getId());
assertNull(newComment.getTaskId());
assertNull(newComment.getAuthor());
assertNull(newComment.getContent());
assertNull(newComment.getCreateDate());
}

@Test
@DisplayName("Should create comment with parameterized constructor")
void testParameterizedConstructor() {
Comment newComment = new Comment(1L, "jane.doe", testDate, "Test comment content");

assertNotNull(newComment);
assertEquals(1L, newComment.getTaskId());
assertEquals("jane.doe", newComment.getAuthor());
assertEquals(testDate, newComment.getCreateDate());
assertEquals("Test comment content", newComment.getContent());
}

@Test
@DisplayName("Should set and get all properties correctly")
void testGettersAndSetters() {
comment.setId(100L);
comment.setTaskId(1L);
comment.setAuthor("natasha romanoff");
comment.setCreateDate(testDate);
comment.setContent("This task requires careful consideration");

assertEquals(100L, comment.getId());
assertEquals(1L, comment.getTaskId());
assertEquals("natasha romanoff", comment.getAuthor());
assertEquals(testDate, comment.getCreateDate());
assertEquals("This task requires careful consideration", comment.getContent());
}

@Test
@DisplayName("Should convert comment to DTO correctly")
void testToDto() {
comment.setId(1L);
comment.setTaskId(2L);
comment.setAuthor("clint barton");
comment.setCreateDate(testDate);
comment.setContent("I agree with the strategic approach");

CommentDto dto = comment.toDto();

assertNotNull(dto);
assertEquals(1L, dto.getId());
assertEquals(2L, dto.getTaskId());
assertEquals("clint barton", dto.getAuthor());
assertEquals(testDate, dto.getCreateDate());
assertEquals("I agree with the strategic approach", dto.getContent());
}

@Test
@DisplayName("Should handle null values properly")
void testWithNullValues() {
comment.setId(1L);
comment.setTaskId(null);
comment.setAuthor(null);
comment.setCreateDate(null);
comment.setContent(null);

assertEquals(1L, comment.getId());
assertNull(comment.getTaskId());
assertNull(comment.getAuthor());
assertNull(comment.getCreateDate());
assertNull(comment.getContent());

// toDto should still work with null values
CommentDto dto = comment.toDto();
assertNotNull(dto);
assertEquals(1L, dto.getId());
assertNull(dto.getTaskId());
assertNull(dto.getAuthor());
assertNull(dto.getCreateDate());
assertNull(dto.getContent());
}

@Test
@DisplayName("Should handle long content strings")
void testLongContent() {
String longContent = "This is a very long comment that might exceed normal lengths. ".repeat(20);

comment.setId(1L);
comment.setTaskId(1L);
comment.setAuthor("test.author");
comment.setCreateDate(testDate);
comment.setContent(longContent);

assertEquals(longContent, comment.getContent());
assertTrue(comment.getContent().length() > 1000);

CommentDto dto = comment.toDto();
assertEquals(longContent, dto.getContent());
}

@Test
@DisplayName("Should maintain data integrity after multiple operations")
void testDataIntegrity() {
// Initial setup
comment.setId(1L);
comment.setTaskId(100L);
comment.setAuthor("initial.author");
comment.setCreateDate(testDate);
comment.setContent("initial content");

// Verify initial state
assertEquals(1L, comment.getId());
assertEquals(100L, comment.getTaskId());
assertEquals("initial.author", comment.getAuthor());

// Update some fields
comment.setAuthor("updated.author");
comment.setContent("updated content");

// Verify updated state
assertEquals(1L, comment.getId()); // ID should remain unchanged
assertEquals(100L, comment.getTaskId()); // TaskID should remain unchanged
assertEquals(testDate, comment.getCreateDate()); // Date should remain unchanged
assertEquals("updated.author", comment.getAuthor()); // Should be updated
assertEquals("updated content", comment.getContent()); // Should be updated
}

@Test
@DisplayName("Should create valid DTO from comment with all fields populated")
void testCompleteCommentToDto() {
comment.setId(42L);
comment.setTaskId(99L);
comment.setAuthor("bruce banner");
comment.setCreateDate(LocalDate.of(2022, 8, 15));
comment.setContent("The technology upgrades look promising and should improve our efficiency significantly.");

CommentDto dto = comment.toDto();

assertNotNull(dto);
assertEquals(42L, dto.getId());
assertEquals(99L, dto.getTaskId());
assertEquals("bruce banner", dto.getAuthor());
assertEquals(LocalDate.of(2022, 8, 15), dto.getCreateDate());
assertEquals("The technology upgrades look promising and should improve our efficiency significantly.", dto.getContent());
}

@Test
@DisplayName("Should handle special characters in content and author")
void testSpecialCharacters() {
String authorWithSpecialChars = "test@user.com";
String contentWithSpecialChars = "Content with special chars: !@#$%^&*()_+-=[]{}|;':\",./<>?";

comment.setId(1L);
comment.setTaskId(1L);
comment.setAuthor(authorWithSpecialChars);
comment.setCreateDate(testDate);
comment.setContent(contentWithSpecialChars);

assertEquals(authorWithSpecialChars, comment.getAuthor());
assertEquals(contentWithSpecialChars, comment.getContent());

CommentDto dto = comment.toDto();
assertEquals(authorWithSpecialChars, dto.getAuthor());
assertEquals(contentWithSpecialChars, dto.getContent());
}

@Test
@DisplayName("Should handle boundary values for IDs")
void testBoundaryValues() {
comment.setId(Long.MAX_VALUE);
comment.setTaskId(Long.MIN_VALUE);
comment.setAuthor("boundary.test");
comment.setCreateDate(testDate);
comment.setContent("Testing boundary values");

assertEquals(Long.MAX_VALUE, comment.getId());
assertEquals(Long.MIN_VALUE, comment.getTaskId());

CommentDto dto = comment.toDto();
assertEquals(Long.MAX_VALUE, dto.getId());
assertEquals(Long.MIN_VALUE, dto.getTaskId());
}
```

}
