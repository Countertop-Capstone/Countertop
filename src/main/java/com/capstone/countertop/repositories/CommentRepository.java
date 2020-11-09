package com.capstone.countertop.repositories;

import com.capstone.countertop.models.Comment;
import com.capstone.countertop.models.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long>{

    Comment getOne(Long id);

    List<Comment> findAllByRecipe(Recipe id);

    List<Comment> findAllByApiId(long id);

    //List<Comment> findAllByDate(Date date);

    @Query("FROM Comment c WHERE c.commentBody LIKE %:term%")
    List<Comment> searchByCommentLike(@Param("term") String term);

}
