package vpmLimp.DTO;


import lombok.Value;

@Value
public class UpdateUser {

    private String email;

    private String password;

    private String address;

    private String city;

    private String state;

    private String zipCode;

    private String phone;
}
