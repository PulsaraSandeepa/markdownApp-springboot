package com.markdown.auth.initialize;


import com.markdown.auth.daos.RoleDAO;
import com.markdown.auth.daos.UserDAO;
import com.markdown.auth.models.MarkDownRoleModel;
import com.markdown.auth.models.MarkDownUserModel;
import com.markdown.auth.services.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Optional;

@Profile({"prod"})
@Component
public class initializeProdData {

    @Autowired
    UserDAO userDAO;

    @Autowired
    RoleDAO roleDAO;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    TokenService tokenService;

    @EventListener
    public void appReady(ApplicationReadyEvent event) {

        addRoles();
        addUsers();
    }

    private void addRoles() {

        final Optional<MarkDownRoleModel> optionalMarkDownRoleModelAdmin = roleDAO.findByRole("ADMIN");

        if (!optionalMarkDownRoleModelAdmin.isPresent()) {
            MarkDownRoleModel markDownRoleModelAdmin = new MarkDownRoleModel();
            markDownRoleModelAdmin.setRole("ADMIN");
            roleDAO.save(markDownRoleModelAdmin);

        }

        final Optional<MarkDownRoleModel> optionalMarkDownRoleModelUser = roleDAO.findByRole("USER");

        if (!optionalMarkDownRoleModelUser.isPresent()) {
            MarkDownRoleModel markDownRoleModelUser = new MarkDownRoleModel();
            markDownRoleModelUser.setRole("USER");
            roleDAO.save(markDownRoleModelUser);
        }
    }

    private void addUsers() {

        final Optional<MarkDownUserModel> optionalMarkDownUserModelAdmin = userDAO.findByUsername("admin");

        if (!optionalMarkDownUserModelAdmin.isPresent()) {
            MarkDownUserModel markDownUserModelAdmin = new MarkDownUserModel();
            markDownUserModelAdmin.setUsername("admin");
            markDownUserModelAdmin.setEmail("admin@gmail.com");
            markDownUserModelAdmin.setPassword(bCryptPasswordEncoder.encode("admin"));
            markDownUserModelAdmin.setRoles(Collections.singletonList("ADMIN"));
            tokenService.generateToken(markDownUserModelAdmin);
            markDownUserModelAdmin.setDisplayName("adminDisplayName");

            userDAO.save(markDownUserModelAdmin);
        }

        final Optional<MarkDownUserModel> optionalMarkDownUserModelUser = userDAO.findByUsername("user");

        if (!optionalMarkDownUserModelUser.isPresent()) {
            MarkDownUserModel markDownUserModelUser = new MarkDownUserModel();
            markDownUserModelUser.setUsername("user");
            markDownUserModelUser.setEmail("user@gmail.com");
            markDownUserModelUser.setPassword(bCryptPasswordEncoder.encode("user"));
            markDownUserModelUser.setRoles(Collections.singletonList("USER"));
            tokenService.generateToken(markDownUserModelUser);
            markDownUserModelUser.setDisplayName("userDisplayName");

            userDAO.save(markDownUserModelUser);
        }


    }


}
