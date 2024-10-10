package vpmLimp.DTO;


public record EvaluationResponse(
        String productName,
        String description,
        int rating,
        UserResponse.EvaluationUserName user
) {


}

