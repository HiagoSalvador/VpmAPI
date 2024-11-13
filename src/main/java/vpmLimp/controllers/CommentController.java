package vpmLimp.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import vpmLimp.DTO.CommentResponse;
import vpmLimp.DTO.CommentRequest;
import vpmLimp.model.UserModel;
import vpmLimp.services.CommentService;

import java.util.List;

@RestController
@RequestMapping("/comments")
@AllArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("")
    public ResponseEntity<CommentResponse> createComment(@RequestBody CommentRequest request, @AuthenticationPrincipal UserModel user) {
        CommentResponse commentResponse = commentService.createComment(request.commentText(), user);
        return ResponseEntity.ok(commentResponse);
    }

    @GetMapping("")
    public ResponseEntity<List<CommentResponse>> getCommentsByUser(@AuthenticationPrincipal UserModel user) {
        List<CommentResponse> commentResponses = commentService.getCommentsByUser(user);
        return ResponseEntity.ok(commentResponses);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommentResponse> updateComment(@PathVariable Long id,
                                                         @RequestBody CommentRequest request,
                                                         @AuthenticationPrincipal UserModel user) {
        CommentResponse updatedComment = commentService.updateComment(id, request.commentText(), user);
        return ResponseEntity.ok(updatedComment);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long id, @AuthenticationPrincipal UserModel user) {
        commentService.deleteComment(id, user);
        return ResponseEntity.noContent().build();
    }
}
