package com.Auction.IPL.security;

import com.Auction.IPL.model.AuthRequest;
import com.Auction.IPL.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private UserService userService;

     //Authenticate user and generate JWT token

     @PostMapping("/register")
     public ResponseEntity<User> registerUser(@RequestBody User user) {
         return ResponseEntity.ok(userService.registerUser(user));
     }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest authRequest) {
        try{
        // Authenticate user credentials
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
        );

        // Load user details
        UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getUsername());

        // Generate JWT token
        String token = jwtUtil.generateToken(userDetails.getUsername());

        // Return token in response
        return ResponseEntity.ok(token);

       }catch (BadCredentialsException e) {
        return ResponseEntity.status(401).body("Invalid Username or Password");
       }
    }
    
}

