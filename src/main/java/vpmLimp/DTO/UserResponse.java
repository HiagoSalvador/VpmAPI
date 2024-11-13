package vpmLimp.DTO;

import vpmLimp.model.UserModel;

public record UserResponse(
        String cpf,
        String name,
        String email,
        String address,
        String city,
        String state,
        String zipCode,
        String phone
) {
    public UserResponse(UserModel user) {
        this(
                user.getCpf(),
                user.getName(),
                user.getEmail(),
                user.getAddress(),
                user.getCity(),
                user.getState(),
                user.getZipCode(),
                user.getPhone()
        );
    }

    public record EvaluationUserName(
            String name
    ) {
    }
}
