package com.binh.blog.service;

import com.binh.blog.model.User;
import com.binh.blog.model.Post;
import com.binh.blog.reponsitory.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    public List<Post> getAllPosts(){
        return postRepository.findAll();
    }

    public void save(Post post) {
        postRepository.save(post);
    }

    public List<Post> findByUser(User user){
        return postRepository.findByCreatorId(user.getId());
    }

    public boolean deletePost(Long postId){
        Post thePost = postRepository.findById(postId).get();
        if(thePost == null)
            return false;
        postRepository.deleteById(postId);
        return true;
    }

    public Post getPost(Long id) {
        return postRepository.findById(id).get();
    }

    public Post find(Long postId) {
        return postRepository.findById(postId).get();
    }
    public List<Post> findByTitle(String keyword){
        return postRepository.findAllByTitle("%"+keyword+"%");
    }
    public Long countByType(String type){
        List<Post> Posts = postRepository.findAll();
        Long count = 0L;
        for(Post post:Posts){
            if(post.getType().equalsIgnoreCase(type)) count++;
        }
        return count;
    }
    
}
