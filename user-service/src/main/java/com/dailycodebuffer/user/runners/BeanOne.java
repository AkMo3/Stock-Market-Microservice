package com.dailycodebuffer.user.runners;

import com.dailycodebuffer.user.entity.User;
import com.dailycodebuffer.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(value=1)
@Slf4j
public class BeanOne implements CommandLineRunner {

    @Autowired private UserRepository userRepository;

    @Override
    public void run(String... args) {
        log.info("Starting sample insertion of users in database");

        userRepository.save(new User("firstname1", "lastname1", "email1@gmail.com"));
        userRepository.save(new User("firstname2", "lastname2", "email2@gmail.com"));
        userRepository.save(new User("firstname3", "lastname3", "email3@gmail.com"));
        userRepository.save(new User("firstname4", "lastname4", "email4@gmail.com"));
        userRepository.save(new User("firstname5", "lastname5", "email5@gmail.com"));

        log.info("number of employees: {}", userRepository.count());
    }
}
