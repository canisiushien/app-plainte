package bf.plainte.app.dto;

import lombok.Data;

@Data
public class SignupDTO {

    private String username;

    private String email;

    private String password;

    private String userConfirmPass;

    private String nom;

    private String prenom;

    private String telephone;
}
