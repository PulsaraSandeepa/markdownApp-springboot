package com.markdown.auth.services.impl;

import com.markdown.auth.daos.RoleDAO;
import com.markdown.auth.daos.UserDAO;
import com.markdown.auth.dtos.UserInfoDTO;
import com.markdown.auth.dtos.UserLoginDTO;
import com.markdown.auth.models.MarkDownRoleModel;
import com.markdown.auth.models.MarkDownUserModel;
import com.markdown.auth.services.TokenService;
import com.markdown.auth.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

import static com.google.common.base.Preconditions.checkNotNull;

@Service
public class UserServiceimpl implements UserService {

    private static final String UNKNOWN_USERNAME_OR_BAD_PASSWORD = "Unknown username of bad password";

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    UserDAO userDAO;

    @Autowired
    RoleDAO roleDAO;

    @Autowired
    TokenService tokenService;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public void createUser(UserInfoDTO userInfoDTO) {

        checkNotNull(userInfoDTO.getPassword());

        //transform userInfoDTO to markdownRoleModel
        /*getting info from source and looks the same info in destination and phste them. if destination is classtype
        it makes new properties*/
        MarkDownUserModel markDownUserModel = modelMapper.map(userInfoDTO, MarkDownUserModel.class);

        //hash the passwords first
        markDownUserModel.setPassword(bCryptPasswordEncoder.encode(userInfoDTO.getPassword()));

        //assign default role of users
        markDownUserModel.setRoles(
                roleDAO.findAll().stream()
                        .map(MarkDownRoleModel::getRole)
                        .filter(role -> role.contains("USER"))
                        .collect(Collectors.toList())
        );

        //generate a new token for the user
        tokenService.generateToken(markDownUserModel);

        //save markdownRoleModel
        userDAO.save(markDownUserModel);

        //update userInfoDTO after the markdownrolemodel has been saved
        userInfoDTO.setPassword("");
        modelMapper.map(markDownUserModel, userInfoDTO);
    }

    @Override
    public UserInfoDTO retrieveUserInfo(String userId) {
        Optional<MarkDownUserModel> optionalMarkDownUserModel = userDAO.findById(userId);

        if (optionalMarkDownUserModel.isPresent()) {
            return modelMapper.map(optionalMarkDownUserModel.get(), UserInfoDTO.class);
        }


        return null;
    }

    @Override
    public UserInfoDTO loginUser(UserLoginDTO userLoginDTO) {
        Optional<MarkDownUserModel> optionalMarkDownUserModel = userDAO.findByUsername(userLoginDTO.getUsername());

        if (optionalMarkDownUserModel.isPresent()) {
            MarkDownUserModel markDownUserModel = optionalMarkDownUserModel.get();
            //check if passwords match
            if (bCryptPasswordEncoder.matches(userLoginDTO.getPassword(), markDownUserModel.getPassword())) {

                return modelMapper.map(markDownUserModel, UserInfoDTO.class);
            } else {
                throw new BadCredentialsException(UNKNOWN_USERNAME_OR_BAD_PASSWORD);
            }

        } else {
            throw new BadCredentialsException(UNKNOWN_USERNAME_OR_BAD_PASSWORD);

        }
    }
}
