package voyager.petshop.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginForm {

    private String credential;
    private String password;

    public LoginForm(String credential, String password) {
        this.credential = credential;
        this.password = password;
    }
}
