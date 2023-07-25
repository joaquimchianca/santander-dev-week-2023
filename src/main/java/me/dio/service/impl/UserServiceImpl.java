package me.dio.service.impl;

import jakarta.transaction.Transactional;
import me.dio.domain.model.User;
import me.dio.domain.repository.UserRepository;
import me.dio.service.UserService;
import me.dio.service.exception.BusinessException;
import me.dio.service.exception.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.Optional.ofNullable;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository repo) {
        this.userRepository = repo;
    }

    public List<User> listAll() {
        return this.userRepository.findAll();
    }

    public User findById(Long id) {
        // if not found, throw an exception
        return this.userRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    @Transactional
    public User create(User newUser) {
        // check for null user, null account and null card
        ofNullable(newUser).orElseThrow(() -> new BusinessException("User to create must no be null."));
        ofNullable(newUser.getAccount()).orElseThrow(() -> new BusinessException("User account must not be null."));
        ofNullable(newUser.getCard()).orElseThrow(() -> new BusinessException("User card must not be null"));

        // check if account number exists
        if (this.userRepository.existsByAccountNumber(newUser.getAccount().getNumber())) {
            throw new BusinessException("This account number already exists.");
        }
        // check if card number exists
        if (this.userRepository.existsByCardNumber(newUser.getCard().getNumber())) {
            throw new BusinessException("This card number already exists.");
        }
        return this.userRepository.save(newUser);
    }

    @Transactional
    public User update(Long id, User updatedUser) {
        User dbUser = this.findById(id);
        // checks if user to update have same IDs
        if (!dbUser.getId().equals(updatedUser.getId())) {
            throw new BusinessException(("Update IDs must be the same."));
        }
        // modify only not null
        ofNullable(updatedUser.getName()).ifPresent(dbUser::setName);
        ofNullable(updatedUser.getAccount()).ifPresent(dbUser::setAccount);
        ofNullable(updatedUser.getCard()).ifPresent(dbUser::setCard);
        ofNullable(updatedUser.getFeatures()).ifPresent(dbUser::setFeatures);
        ofNullable(updatedUser.getNews()).ifPresent(dbUser::setNews);
        return this.userRepository.save(dbUser);
    }

    public void delete(Long id) {
        // uses null checks from other method
        User dbUser = this.findById(id);
        this.userRepository.delete(dbUser);
    }
}
