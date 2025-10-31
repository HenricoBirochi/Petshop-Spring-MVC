package voyager.petshop.models;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "user_id")
    private UUID userId;

    @Column(length = 100, nullable = false)
    private String name;

    @Column(name = "user_name", length = 150, nullable = false, unique = true)
    private String userName;

    @Column(length = 200, nullable = false)
    private String password;

}
