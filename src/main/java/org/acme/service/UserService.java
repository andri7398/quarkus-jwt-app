package org.acme.service;

import jakarta.enterprise.context.ApplicationScoped;
import org.acme.model.User;
import org.mindrot.jbcrypt.BCrypt;

@ApplicationScoped
public class UserService {
    public boolean register(User user) {
        if (User.find("username", user.getUsername()).firstResult() != null) return false;
        user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));
        user.persist();
        return true;
    }

    public User authenticate(String username, String password) {
        User user = User.find("username", username).firstResult();
        if (user != null && BCrypt.checkpw(password, user.getPassword())) {
            return user;
        }
        return null;
    }
}
