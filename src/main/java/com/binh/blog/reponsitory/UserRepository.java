package com.binh.blog.reponsitory;

import java.util.List;
import java.util.Optional;

import com.binh.blog.model.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User,Long>{
    User findByUserName(String userName);
    Optional<User> findById(Long id);
    @Query(value="select * from user where user_name like ?1",nativeQuery = true)
    List<User> findAllByName(String keyword);
}
