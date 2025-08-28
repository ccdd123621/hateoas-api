package com.codility.hateoas.api;

import com.codility.hateoas.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

```
/**
* Find all comments for a specific task, ordered by creation date (most recent first)
* @param taskId the ID of the task
* @return list of comments for the task
*/
List<Comment> findByTaskIdOrderByCreateDateDesc(Long taskId);

/**
* Find all comments for a specific task, ordered by creation date (oldest first)
* @param taskId the ID of the task
* @return list of comments for the task
*/
List<Comment> findByTaskIdOrderByCreateDateAsc(Long taskId);

/**
* Find a specific comment by task ID and comment ID
* @param taskId the ID of the task
* @param commentId the ID of the comment
* @return optional comment if found
*/
Optional<Comment> findByTaskIdAndId(Long taskId, Long commentId);

/**
* Count the number of comments for a specific task
* @param taskId the ID of the task
* @return number of comments
*/
long countByTaskId(Long taskId);

/**
* Check if a comment exists for a specific task
* @param taskId the ID of the task
* @param commentId the ID of the comment
* @return true if comment exists
*/
boolean existsByTaskIdAndId(Long taskId, Long commentId);

/**
* Find comments by author
* @param author the author name
* @return list of comments by the author
*/
List<Comment> findByAuthorOrderByCreateDateDesc(String author);

/**
* Find comments by author and task
* @param author the author name
* @param taskId the ID of the task
* @return list of comments by the author for the specific task
*/
List<Comment> findByAuthorAndTaskIdOrderByCreateDateDesc(String author, Long taskId);

/**
* Find comments created on a specific date
* @param createDate the creation date
* @return list of comments created on that date
*/
List<Comment> findByCreateDate(LocalDate createDate);

/**
* Find comments created between two dates
* @param startDate the start date (inclusive)
* @param endDate the end date (inclusive)
* @return list of comments created between the dates
*/
List<Comment> findByCreateDateBetweenOrderByCreateDateDesc(LocalDate startDate, LocalDate endDate);

/**
* Find comments containing specific text in content
* @param content the text to search for
* @return list of comments containing the text
*/
List<Comment> findByContentContainingIgnoreCaseOrderByCreateDateDesc(String content);

/**
* Find comments for a task containing specific text
* @param taskId the ID of the task
* @param content the text to search for
* @return list of comments for the task containing the text
*/
List<Comment> findByTaskIdAndContentContainingIgnoreCaseOrderByCreateDateDesc(Long taskId, String content);

/**
* Custom query to find recent comments for a task (last N days)
* @param taskId the ID of the task
* @param days number of days to look back
* @return list of recent comments
*/
@Query("SELECT c FROM Comment c WHERE c.taskId = :taskId AND c.createDate >= :sinceDate ORDER BY c.createDate DESC")
List<Comment> findRecentCommentsForTask(@Param("taskId") Long taskId, @Param("sinceDate") LocalDate sinceDate);

/**
* Custom query to find the latest comment for each task
* @return list of latest comments per task
*/
@Query("SELECT c FROM Comment c WHERE c.createDate = (SELECT MAX(c2.createDate) FROM Comment c2 WHERE c2.taskId = c.taskId)")
List<Comment> findLatestCommentPerTask();

/**
* Custom query to find comments with pagination support
* @param taskId the ID of the task
* @param limit maximum number of comments to return
* @param offset number of comments to skip
* @return list of comments with pagination
*/
@Query("SELECT c FROM Comment c WHERE c.taskId = :taskId ORDER BY c.createDate DESC LIMIT :limit OFFSET :offset")
List<Comment> findCommentsForTaskWithPagination(@Param("taskId") Long taskId, @Param("limit") int limit, @Param("offset") int offset);

/**
* Delete all comments for a specific task
* @param taskId the ID of the task
* @return number of deleted comments
*/
long deleteByTaskId(Long taskId);

/**
* Delete comments older than a specific date
* @param date the cutoff date
* @return number of deleted comments
*/
long deleteByCreateDateBefore(LocalDate date);

/**
* Find comments by multiple authors
* @param authors list of author names
* @return list of comments by any of the specified authors
*/
List<Comment> findByAuthorInOrderByCreateDateDesc(List<String> authors);

/**
* Count comments by author for a specific task
* @param author the author name
* @param taskId the ID of the task
* @return number of comments by the author for the task
*/
long countByAuthorAndTaskId(String author, Long taskId);

/**
* Check if task has any comments
* @param taskId the ID of the task
* @return true if task has comments
*/
boolean existsByTaskId(Long taskId);

/**
* Find the first comment for a task (oldest)
* @param taskId the ID of the task
* @return optional first comment
*/
Optional<Comment> findFirstByTaskIdOrderByCreateDateAsc(Long taskId);

/**
* Find the last comment for a task (newest)
* @param taskId the ID of the task
* @return optional last comment
*/
Optional<Comment> findFirstByTaskIdOrderByCreateDateDesc(Long taskId);

/**
* Custom native query for complex searches
* @param searchTerm the search term
* @return list of comments matching the search
*/
@Query(value = "SELECT * FROM comments c WHERE " +
"LOWER(c.content) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
"LOWER(c.author) LIKE LOWER(CONCAT('%', :searchTerm, '%')) " +
"ORDER BY c.create_date DESC", nativeQuery = true)
List<Comment> searchComments(@Param("searchTerm") String searchTerm);

/**
* Find comments statistics by task
* @param taskId the ID of the task
* @return comment statistics
*/
@Query("SELECT COUNT(c), MIN(c.createDate), MAX(c.createDate) FROM Comment c WHERE c.taskId = :taskId")
Object[] getCommentStatisticsForTask(@Param("taskId") Long taskId);
```

}
