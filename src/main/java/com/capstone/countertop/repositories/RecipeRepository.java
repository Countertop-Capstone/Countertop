package com.capstone.countertop.repositories;

import com.capstone.countertop.models.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {

    Recipe findByName(String Name);
    Recipe findFirstByName(String Name);
    Recipe findOne(Long skill);

    Recipe getOne(Long id);

    @Query("FROM recipe r WHERE r.title LIKE %:term%")
    List<Recipe> searchByTitleLike(@Param("term") String term);

    @Query("FROM recipe r WHERE r.description LIKE %:term%")
    List<Recipe> searchByDescriptionLike(@Param("term") String term);

    List<Recipe> findAllByDate(Date date);
}
