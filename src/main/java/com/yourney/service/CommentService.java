
package com.yourney.service;

import java.util.Optional;

import com.yourney.model.Comment;
import com.yourney.repository.CommentRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    public Optional<Comment> findById(final Long id) {
        return commentRepository.findById(id);
    }

    public Comment save(Comment comment) {
        Comment newComment = null;
        try {
            newComment = commentRepository.save(comment);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return newComment;
    }
    
    public void deleteById(long id) {
        try {
            commentRepository.deleteById(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
