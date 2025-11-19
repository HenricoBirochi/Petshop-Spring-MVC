package voyager.petshop.models.enums;

import lombok.Getter;

@Getter
public enum UserRoles {
    MASTER("master"),
    ADMIN("admin"),
    USER("user");

    private String role;

    UserRoles(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}
