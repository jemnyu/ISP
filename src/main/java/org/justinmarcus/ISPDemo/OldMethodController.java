/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.justinmarcus.ISPDemo;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Justin
 */
@RestController
public class OldMethodController {
    private static final String AUTH_TOKEN = "AuthenticatedToken";
    private static final String storedPassword = BCrypt.hashpw("Password", BCrypt.gensalt());
    @PostMapping(value = "/old/authenticate")
    public ResponseEntity authenticate(@RequestParam String password)
    {
        //do login for token auth
        if(BCrypt.checkpw(password, storedPassword))
        {
            System.out.println("Successfully authenticated");
            return ResponseEntity.ok().body(AUTH_TOKEN);
        }
        else {
            System.out.println("Failed to authenticate");
            return ResponseEntity.badRequest().body("Invalid username/password");
        }
    }
    
    @PostMapping(value = "/old/user")
    public ResponseEntity getUser(@RequestParam String authenticationToken)
    {
        //get user
        if(!AUTH_TOKEN.equals(authenticationToken))
        {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return ResponseEntity.ok("Valid User");
    }
}
