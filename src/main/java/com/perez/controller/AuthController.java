package com.perez.controller;

import com.perez.config.JwtProvider;
import com.perez.model.Cart;
import com.perez.model.USER_ROLE;
import com.perez.model.User;
import com.perez.reporsitory.CartRepository;
import com.perez.reporsitory.UserRepository;
import com.perez.request.LoginRequest;
import com.perez.response.AuthResponse;
import com.perez.service.CustomerUserDetailsService;
import com.perez.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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

   private UserService userService;


   @PostMapping("/signup")  //REGISTER
   public ResponseEntity<AuthResponse> createUserHandler(@RequestBody User user) throws Exception {

      String email = user.getEmail();
      String password = user.getPassword();
      String fullName = user.getFullName();
      USER_ROLE role=user.getRole();

      User isEmailExist = userRepository.findByEmail(email);

      if (isEmailExist!=null) {    //if the email exists we validate

         throw new Exception("El correo electrónico ya se utiliza con otra cuenta");
      }

      // new user data
      User createdUser = new User();
      createdUser.setEmail(email);
      createdUser.setFullName(fullName);
      createdUser.setPassword(passwordEncoder.encode(password)); //password will be encrypted
      createdUser.setRole(role);

      User savedUser = userRepository.save(createdUser);

      Cart cart = new Cart();
      cart.setCustomer(savedUser);
      Cart savedCart = cartRepository.save(cart);

      List<GrantedAuthority> authorities=new ArrayList<>();

      authorities.add(new SimpleGrantedAuthority(role.toString()));


      Authentication authentication = new UsernamePasswordAuthenticationToken(email, password,authorities);
      SecurityContextHolder.getContext().setAuthentication(authentication);

      String token = jwtProvider.generateToken(authentication);

      AuthResponse authResponse = new AuthResponse();
      authResponse.setJwt(token);
      authResponse.setMessage("Registro Exitoso");
      authResponse.setRole(savedUser.getRole());

      return new ResponseEntity<>(authResponse, HttpStatus.OK);
   }

   @PostMapping("/signin") //LOGIN
   public ResponseEntity<AuthResponse> signin(@RequestBody LoginRequest loginRequest) {

      String username = loginRequest.getEmail();
      String password = loginRequest.getPassword();

      //System.out.println(username + " ----- " + password);

      try {
         Authentication authentication = authenticate(username, password);
         SecurityContextHolder.getContext().setAuthentication(authentication);

         //the token is generated
         String token = jwtProvider.generateToken(authentication);
         AuthResponse authResponse = new AuthResponse();

         authResponse.setMessage("Inicio de sesión exitoso");
         authResponse.setJwt(token);
         Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

         String roleName = authorities.isEmpty() ? null : authorities.iterator().next().getAuthority();

         authResponse.setRole(USER_ROLE.valueOf(roleName));

         return ResponseEntity.ok(authResponse);
      } catch (BadCredentialsException ex) {
         //personalized message
         AuthResponse errorResponse = new AuthResponse();
         errorResponse.setMessage(ex.getMessage());
         return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
      }
   }

   private Authentication authenticate(String username, String password) {
      UserDetails userDetails;
      try {
         userDetails = customerUserDetailsService.loadUserByUsername(username);
      } catch (UsernameNotFoundException ex) {
         // Generic message for security
         throw new BadCredentialsException("Credenciales inválidas. Verifica tu correo o contraseña.");
      }

      //Matches authenticated users, when passwords are encrypted. PASSWRD (unencrypted).
      if (!passwordEncoder.matches(password, userDetails.getPassword())) { //encrypted password obtained from the database
         System.out.println("sign in userDetails - password not match " + userDetails);
         //Generic message for security
         throw new BadCredentialsException("Credenciales inválidas. Verifica tu correo o contraseña.");
      }
      //if user and password match
      return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
   }

}

