package vpmLimp.DTO;


public record EvaluationResponse(
        String productName,
        String description,
        Long id,
        int rating,
        UserResponse.EvaluationUserName user
) {
}

