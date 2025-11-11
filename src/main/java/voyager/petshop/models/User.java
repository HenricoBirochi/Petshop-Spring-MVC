package voyager.petshop.models;

import java.util.UUID;

import voyager.petshop.models.interfaces.IModel;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import voyager.petshop.models.enums.UserRoles;

@Entity
@Table(name = "users")
@Getter
@Setter
public class User implements IModel {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "user_id")
    private UUID userId;

    @Column(length = 100, nullable = false)
    private String name;

    @Column(name = "user_name", length = 150, nullable = false, unique = true)
    private String userName;

    @Column(length = 150, nullable = false, unique = true)
    private String email;

    @Column(length = 200, nullable = false)
    private String password;

    @Column(name = "user_role", nullable = false)
    private UserRoles userRole;

}
