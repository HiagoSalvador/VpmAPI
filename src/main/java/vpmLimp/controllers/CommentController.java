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
import java.util.List;

@RestController
@RequestMapping("/comments")
@AllArgsConstructor
public class CommentController {

    private final CommentService commentService;

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
    public ResponseEntity<CommentResponse> updateComment(@PathVariable Long id,
                                                         @RequestBody CommentRequest request,
                                                         @AuthenticationPrincipal UserModel user) {
        CommentModel existingComment = commentService.getCommentById(id); // Método para buscar o comentário
        commentService.validateOwnership(existingComment, user); // Valida a propriedade

        CommentModel updatedComment = commentService.updateComment(id, request.commentText(), user);
        CommentResponse commentResponse = new CommentResponse(updatedComment.getId(), updatedComment.getCommentText());
        return ResponseEntity.ok(commentResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long id, @AuthenticationPrincipal UserModel user) {
        CommentModel existingComment = commentService.getCommentById(id);
        commentService.validateOwnership(existingComment, user); // Valida a propriedade

        commentService.deleteComment(id, user);
        return ResponseEntity.noContent().build();
    }
}
