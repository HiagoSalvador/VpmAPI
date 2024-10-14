package vpmLimp.controllers;

import lombok.AllArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import vpmLimp.DTO.CommentResponse;
import vpmLimp.DTO.CommentRequest;
import vpmLimp.model.CommentModel;
import vpmLimp.model.UserModel;
import vpmLimp.services.CommentService;
import vpmLimp.services.UserService;

import java.util.List;
import java.util.stream.Collectors;


@RequestMapping("/comments")
@AllArgsConstructor
@RestController
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private UserService userService;

    @PostMapping("")
    public ResponseEntity<CommentResponse> createComment(@RequestBody CommentRequest request) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email = userDetails.getUsername();

        CommentModel comment = new CommentModel();
        comment.setCommentText(request.commentText());

        UserModel user = userService.getUserByEmail(email);
        comment.setUser(user);

        CommentModel savedComment = commentService.saveComment(comment);

        CommentResponse commentResponse = new CommentResponse(savedComment.getId(), savedComment.getCommentText());

        return ResponseEntity.ok(commentResponse);
    }

    @GetMapping("")
    public ResponseEntity<List<CommentResponse>> getCommentsByUser() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email = userDetails.getUsername();

        List<CommentModel> comments = commentService.findAllFilteredByUser(email);

        List<CommentResponse> commentResponses = comments.stream()
                .map(comment -> new CommentResponse(comment.getId(), comment.getCommentText()))
                .collect(Collectors.toList());

        return ResponseEntity.ok(commentResponses);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommentResponse> updateComment(@PathVariable Long id, @RequestBody CommentRequest request) {
        CommentModel updatedComment = commentService.updateComment(id, request.commentText());

        CommentResponse commentResponse = new CommentResponse(updatedComment.getId(), updatedComment.getCommentText());

        return ResponseEntity.ok(commentResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long id) {
        commentService.deleteComment(id);
        return ResponseEntity.noContent().build();
    }

}
