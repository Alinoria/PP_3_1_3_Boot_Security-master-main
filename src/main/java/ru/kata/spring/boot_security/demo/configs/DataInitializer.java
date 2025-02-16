package ru.kata.spring.boot_security.demo.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.repositories.RoleRepository;
import ru.kata.spring.boot_security.demo.repositories.UserRepository;

import java.util.Set;


@Component
public class DataInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;
    @Autowired
    public DataInitializer(RoleRepository roleRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    @Transactional
    public void run(String... args) throws Exception {
        Role adminRole = roleRepository.findByRoleName("ADMIN")
                .orElseGet(() -> {
                    Role role = new Role("ADMIN");
                    return roleRepository.save(role);
                });

        Role userRole = roleRepository.findByRoleName("USER")
                .orElseGet(() -> {
                    Role role = new Role("USER");
                    return roleRepository.save(role);
                });

        User adminUser = userRepository.findByUsername("admin")
                .orElseGet(() -> {
                    User user = new User();
                    user.setUsername("admin"); // login
                    user.setAge(28);
                    user.setEmail("admin@gmail.com");
                    user.setPassword(passwordEncoder.encode("admin"));        // пароль
                    user.setRoles(Set.of(adminRole, userRole));
                    return userRepository.save(user);
                });

        User regularUser = userRepository.findByUsername("user")
                .orElseGet(() -> {
                    User user = new User();
                    user.setUsername("user"); //login
                    user.setAge(31);
                    user.setEmail("user@gmail.com");
                    user.setPassword(passwordEncoder.encode("user")); // пароль
                    user.setRoles(Set.of(userRole));
                    return userRepository.save(user);
                });
    }
}
