package vpmLimp.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import vpmLimp.DTO.CommentResponse;
import vpmLimp.DTO.CommentRequest;
import vpmLimp.model.CommentModel;
import vpmLimp.model.UserModel;
import vpmLimp.services.CommentService;
import vpmLimp.services.UserService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/comments")
@AllArgsConstructor
public class CommentController {

    private final CommentService commentService;
    private final UserService userService;

    @PostMapping("")
    public ResponseEntity<CommentResponse> createComment(@RequestBody CommentRequest request, @AuthenticationPrincipal UserModel user) {
        CommentModel savedComment = commentService.createComment(request.commentText(), user);
        CommentResponse commentResponse = new CommentResponse(savedComment.getId(), savedComment.getCommentText());
        return ResponseEntity.ok(commentResponse);
    }

    @GetMapping("")
    public ResponseEntity<List<CommentResponse>> getCommentsByUser(@AuthenticationPrincipal UserModel user) {
        List<CommentResponse> commentResponses = commentService.getCommentsByUserEmail(user.getEmail());
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
