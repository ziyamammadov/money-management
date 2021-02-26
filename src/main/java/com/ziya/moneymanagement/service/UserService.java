package com.ziya.moneymanagement.service;

import com.ziya.moneymanagement.entity.User;
import com.ziya.moneymanagement.exception.TransactionNotFoundException;
import com.ziya.moneymanagement.exception.UserNotFoundException;
import com.ziya.moneymanagement.repository.UserRepository;
import com.ziya.moneymanagement.security.SecurityUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserService implements CrudService<User>{
    private final UserRepository repository;
//    private final EmailSender emailSender;

    public UserService(UserRepository repository) {
        this.repository = repository;
//        this.emailSender = emailSender;
    }

    @Transactional(readOnly = true)
    public Optional<User> getUserWithAuthorities() {
        return SecurityUtils.getCurrentUsername().flatMap(repository::findOneWithAuthoritiesByUsername);
    }

    @Cacheable(value = "allUsers", unless = "#result == null")
    public List<User> getAll() {
        return repository.findAll();
    }

    @Cacheable(value = "allUsers", unless = "#result == null", key = "#id")
    public User getOne(Long id) {
        return repository.findById(id).orElseThrow(UserNotFoundException::new);
    }

    @CachePut(value = "allUsers", key = "#user.id")
    public User save(User user) {
        StringBuilder text = new StringBuilder("You successfully registered. Please click the link to activate your profile \n");
        text.append("http://localhost:8080/user/activate/").append(user.getEmail());
        String subject = "Account Activation";
        //emailSender.sendPlainText(user.getEmail(),subject, String.valueOf(text));
        return repository.save(user);
    }

    @CacheEvict(value = "allUsers", key = "#result.id")
    public User update(User user) {
        Long id = user.getId();
        return repository.findById(id)
                .map(tran -> repository.save(user))
                .orElseThrow(TransactionNotFoundException::new);
    }

    @CacheEvict(value = "allUsers", key = "#id")
    public User deleteById(Long id) {
        return repository.findById(id)
                .map(user -> {
                    repository.deleteById(id);
                    return user;
                })
                .orElseThrow(UserNotFoundException::new);
    }

    public User activate(String email) {
        return repository.activateAccount(email).orElseThrow(()->new IllegalArgumentException("Could not activate"));
    }
}
