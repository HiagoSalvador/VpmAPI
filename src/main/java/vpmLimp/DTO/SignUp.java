package vpmLimp.DTO;

public record SignUp(
        String name,
        String email,
        String password,
        String address,
        String city,
        String state,
        String zipCode,
        String phone,
        String cpf,
        String role
) {}
