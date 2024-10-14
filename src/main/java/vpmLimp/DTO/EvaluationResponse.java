package vpmLimp.DTO;


public record EvaluationResponse(
        String productName,
        String description,
        Long id,
        int rating,
        UserResponse.EvaluationUserName user
) {

    public EvaluationResponse(String productName, String description, int rating, UserResponse.EvaluationUserName user) {
        this(productName, description, null, rating, user); // id como null
    }


}

