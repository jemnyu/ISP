/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.justinmarcus.ISPDemo;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Justin
 */
@RestController
public class NewMethodController {
    private static final String AUTH_TOKEN = "NewAuthenticatedToken";
    private static final String storedPassword = BCrypt.hashpw("NewPassword", BCrypt.gensalt());
    @PostMapping(value = "/new/user")
    public ResponseEntity getUser(@RequestParam(required = false) String authenticationToken, @RequestParam(required = false) String password)
    {
        boolean returnAuth = false;
        //in practical implementations, this would also check against expiration, and the true auth token would be an object
        if(authenticationToken == null /*|| authenticationToken.isExpired()*/)
        {
            System.out.println("Validating with password");
            //the auth token is null, authenticate with password and add generated authentication token to response
            if(BCrypt.checkpw(password, storedPassword))
            {
                authenticationToken = AUTH_TOKEN;
                returnAuth = true;
            }
        }
        else {
            System.out.println("Validating with auth token");
        }
        //get user
        if(!AUTH_TOKEN.equals(authenticationToken))
        {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        StringBuilder body = new StringBuilder("New Valid User");
        if(returnAuth) body.append(";").append(authenticationToken);
        return ResponseEntity.ok(body.toString());
    }
}
