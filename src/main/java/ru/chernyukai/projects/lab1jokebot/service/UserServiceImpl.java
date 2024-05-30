package ru.chernyukai.projects.lab1jokebot.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.chernyukai.projects.lab1jokebot.exceptions.UsernameAlreadyExistsException;
import ru.chernyukai.projects.lab1jokebot.model.User;
import ru.chernyukai.projects.lab1jokebot.model.UserAuthority;
import ru.chernyukai.projects.lab1jokebot.model.UserRole;
import ru.chernyukai.projects.lab1jokebot.repository.UserRepository;
import ru.chernyukai.projects.lab1jokebot.repository.UserRolesRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService, UserDetailsService {
    @Autowired
    private final UserRolesRepository userRolesRepository;

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final PasswordEncoder passwordEncoder;





    @Override
    @Transactional
    public void registration(String username, String password) throws UsernameAlreadyExistsException {
        if (userRepository.findByUsername(username).isEmpty()) {
            User user = userRepository.save(
                    new User()
                            .setId(null)
                            .setUsername(username)
                            .setPassword(passwordEncoder.encode(password))
                            .setLocked(false)
                            .setExpired(false)
                            .setEnabled(true)

            );
            userRolesRepository.save(new UserRole(null, UserAuthority.DEFAULT_USER, user));
        }
        else {
            throw new UsernameAlreadyExistsException();
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));
    }



    @Override
    public Page<User> getAllUsers (int page){

        List<User> users = userRepository.getUsersBy();

        //Вывести страницу
        int start = Math.min(page * 10, users.size());
        int end = Math.min((page + 1) * 10, users.size());
        List<User> usersOnPage = users.subList(start, end);
        Pageable pageable = PageRequest.of(page, 10);
        return new PageImpl<>(usersOnPage, pageable, users.size());
    }

    @Override
    public Optional<User> getUserById (Long id){
        return userRepository.getUserById(id);
    }

    @Override
    @Transactional
    public Optional<User> editUserAuthority(Long id, boolean isModerator){
        Optional<User> userOptional = userRepository.getUserById(id);

        if (userOptional.isEmpty()){
            return Optional.empty();
        }

        User user = userOptional.get();

        boolean hasModerator = false;
        List<UserRole> userRoles = user.getUserRoles();
        for (UserRole role : userRoles){
            if (role.getUserAuthority().equals(UserAuthority.MODERATOR)){
                hasModerator = true;
                break;
            }
        }

        if (isModerator && !hasModerator){
            userRolesRepository.save(new UserRole(
                    null,
                    UserAuthority.MODERATOR,
                    user
            ));
        }
        else if (!isModerator && hasModerator) {
            userRolesRepository.deleteModerById(user.getId());
        }

        return Optional.of(userRepository.getUserById(user.getId()).get());


    }



}
