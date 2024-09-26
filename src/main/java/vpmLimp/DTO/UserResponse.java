package vpmLimp.DTO;

import lombok.Data;
import vpmLimp.model.UserModel;

@Data
public class UserResponse {

    private String cpf;

    private String name;

    private String email;

    private String address;

    private String city;

    private String state;

    private String zipCode;

    private String phone;


    public UserResponse(UserModel user) {
        this.name = user.getName();
        this.email = user.getEmail();
        this.phone = user.getPhone();
        this.address = user.getAddress();
        this.city = user.getCity();
        this.state = user.getState();
        this.zipCode = user.getZipCode();
        this.cpf = user.getCpf();
    }




}
