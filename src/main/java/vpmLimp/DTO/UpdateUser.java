package vpmLimp.DTO;

public record UpdateUser(
        String email,
        String password,
        String address,
        String city,
        String state,
        String zipCode,
        String phone
) {
}
