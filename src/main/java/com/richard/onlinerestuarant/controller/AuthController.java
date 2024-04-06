package com.richard.onlinerestuarant.controller;

import com.richard.onlinerestuarant.config.JwtProvider;
import com.richard.onlinerestuarant.model.Cart;
import com.richard.onlinerestuarant.model.USER_ROLE;
import com.richard.onlinerestuarant.model.User;
import com.richard.onlinerestuarant.repository.CartRepository;
import com.richard.onlinerestuarant.repository.UserRepository;
import com.richard.onlinerestuarant.request.LoginRequest;
import com.richard.onlinerestuarant.response.AuthResponse;
import com.richard.onlinerestuarant.service.CustomerUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtProvider jwtProvider;
    @Autowired
    private CustomerUserDetailsService customerUserDetailsService;
    @Autowired
    private CartRepository cartRepository;

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> createUserHandler(@RequestBody User user) throws Exception {

        User isEmailExist=userRepository.findByEmail(user.getEmail());
        if(isEmailExist!=null){
            throw new Exception("Email is already used with another account");
        }
        User createduser = new User();
        createduser.setEmail(user.getEmail());
        createduser.setFullName(user.getFullName());
        createduser.setRole(user.getRole());
        createduser.setPassword(passwordEncoder.encode(user.getPassword()));

        User savedUser = userRepository.save(createduser);

        Cart cart=new Cart();
        cart.setCustomer(savedUser);
        cartRepository.save(cart);

        Authentication authentication=new UsernamePasswordAuthenticationToken(user.getEmail(),user.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt=jwtProvider.generatedToken(authentication);

        AuthResponse authResponse=new AuthResponse();
        authResponse.setJwt(jwt);
        authResponse.setMessage("register success");
        authResponse.setRole(savedUser.getRole());
        return new ResponseEntity<>(authResponse, HttpStatus.CREATED);
    }

    @PostMapping("/signin")
    public ResponseEntity<AuthResponse> signin(@RequestBody LoginRequest loginRequest){

        String username=loginRequest.getEmail();
        String password=loginRequest.getPassword();

        Authentication authentication=authenticate(username,password);
        Collection<? extends GrantedAuthority> authorities=authentication.getAuthorities();
        String role = authorities.isEmpty()?null:authorities.iterator().next().getAuthority();

        String jwt=jwtProvider.generatedToken(authentication);

        AuthResponse authResponse=new AuthResponse();
        authResponse.setJwt(jwt);
        authResponse.setMessage("login success");
        authResponse.setRole(USER_ROLE.valueOf(role));
        return new ResponseEntity<>(authResponse, HttpStatus.OK);
    }

    private Authentication authenticate(String username, String password) {
        UserDetails userDetails = customerUserDetailsService.loadUserByUsername(username);

        if(userDetails==null){
            throw new BadCredentialsException("Invalid username...");
        }
        if(!passwordEncoder.matches(password,userDetails.getPassword())){
            throw new BadCredentialsException("Invalid Password...");
        }
        return new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
    }


}
