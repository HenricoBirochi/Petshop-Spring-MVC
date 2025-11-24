package voyager.petshop.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import voyager.petshop.models.User;
import voyager.petshop.models.enums.UserRoles;
import voyager.petshop.repositories.UserRepository;
import voyager.petshop.services.HashingOfUserPasswordService;

@Component
public class DataSeeder implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private HashingOfUserPasswordService hashingService;

    @Override
    public void run(String... args) throws Exception {
        if (userRepository.findByUserName("admin") == null) {
            User user = new User();
            user.setName("Administrator");
            user.setUserName("admin");
            user.setEmail("admin@local");
            user.setPassword(hashingService.hashPassword("admin"));
            user.setUserRole(UserRoles.ADMIN);
            userRepository.save(user);
            System.out.println("Seeded admin user (admin/admin)");
        }
    }

}
