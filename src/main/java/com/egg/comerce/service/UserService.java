package com.egg.comerce.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.egg.comerce.exception.UnfilledFormException;
import com.egg.comerce.model.User;
import com.egg.comerce.enums.Rol;
import com.egg.comerce.repository.UserRepository;

import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Transactional
    public void createUser(String name, String surname, String email, String password, String password2)
            throws UnfilledFormException {
        InputValidation(name, surname, email, password, password2);
        User user = new User();
        String encodedPassword = passwordEncoder.encode(password);
        user.setname(name);
        user.setsurname(surname);
        user.setEmail(email);
        user.setPassword(encodedPassword);
        user.setRol(Rol.USER);
        userRepository.save(user);
    }

    @Transactional
    public void UpdateUser(Long userId, String name, String surname, String email, String password, String password2)
            throws UnfilledFormException {
        InputValidation(name, surname, email, password, password2);
        Optional<User> response = userRepository.findById(userId);
        if (response.isPresent()) {
            User user = response.get();
            user.setname(name);
            user.setsurname(surname);
            user.setEmail(email);
            user.setPassword(password);
            userRepository.save(user);
        } else {
            throw new UnfilledFormException("user doesn't exist");
        }
    }

    @Transactional
    public void DeleteUser(Long manofacturerId) throws UnfilledFormException {
        Optional<User> response = userRepository.findById(manofacturerId);
        if (response.isPresent()) {
            userRepository.delete(response.get());
        } else {
            throw new UnfilledFormException("user doesn't exist");
        }
    }

    public void InputValidation(String name, String surname, String email, String password, String password2)
            throws UnfilledFormException {
        if (name == null || name.isEmpty()) {
            throw new UnfilledFormException("Name is empty.");
        }
        if (surname == null || surname.isEmpty()) {
            throw new UnfilledFormException("Surname is empty.");
        }
        if (email == null || email.isEmpty()) {
            throw new UnfilledFormException("email is empty.");
        }
        passwordValidation(password,password2);
    }
 
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.buscarPorEmail(email);
        if (user != null) {
            List<GrantedAuthority> credentials = new ArrayList<>();
            GrantedAuthority p = new SimpleGrantedAuthority("ROLE_" + user.getRol().toString());
            credentials.add(p);
            ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
            HttpSession session = attr.getRequest().getSession(true);
            session.setAttribute("usuariosession", user);
            return new org.springframework.security.core.userdetails.User(user.getEmail(),user.getPassword(),credentials);
        } else {
            return null;
        }
    }

    private void passwordValidation(String password, String password2)
            throws UnfilledFormException {
        if (!password.isEmpty() && password != null && password.length() > 5) {
            if (!password.equals(password2)) {
                throw new UnfilledFormException("Las contraseñas ingresadas deben ser iguales");
            }
        } else {
            throw new UnfilledFormException("La contraseña no puede estar vacía, y debe tener más de 5 dígitos");
        }
    }

}
