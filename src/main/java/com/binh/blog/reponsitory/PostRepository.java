package com.binh.blog.reponsitory;

import com.binh.blog.model.Post;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post,Long>{

    List<Post> findByCreatorId(Long id);
    Optional<Post> findById(Long id);
    @Query(value="select * from post where title like ?1",nativeQuery = true)
    List<Post> findAllByTitle(String keyword);
}