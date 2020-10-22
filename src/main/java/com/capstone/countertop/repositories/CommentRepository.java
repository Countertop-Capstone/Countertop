package com.capstone.countertop.repositories;

import com.capstone.countertop.models.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long>{

    Comment getOne(long id);
    Comment findByTerm(String term);
    @Query("FROM comment c WHERE c.text LIKE %:term%")
    List<Comment> searchByDescriptionLike(@Param("term") String term);
}
