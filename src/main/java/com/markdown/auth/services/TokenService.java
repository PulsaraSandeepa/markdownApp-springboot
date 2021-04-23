package com.markdown.auth.services;

import com.markdown.auth.exceptions.InvalidTokenException;
import com.markdown.auth.models.MarkDownUserModel;

public interface TokenService {

    //token validation
    void validateToken(String jwtToken) throws InvalidTokenException;

    void generateToken(MarkDownUserModel markDownUserModel);
}
