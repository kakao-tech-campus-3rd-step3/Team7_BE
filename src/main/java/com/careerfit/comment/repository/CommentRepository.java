package com.careerfit.comment.repository;

import com.careerfit.comment.domain.Comment;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findAllByDocumentId(Long documentId);

    Page<Comment> findAllByDocumentId(Long documentId, Pageable pageable);
}
