package voyager.petshop.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import voyager.petshop.models.User;

public interface UserRepository extends JpaRepository<User, UUID> {
    User findByEmail(String email);
    User findByUserName(String userName);
}
