package com.perez.service;

import com.perez.Exception.UserException;
import com.perez.config.JwtProvider;
import com.perez.model.User;
import com.perez.reporsitory.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImplementation implements UserService{

@Autowired
    private UserRepository userRepository;
@Autowired
    private JwtProvider jwtProvider;

        @Override
        public User findUserProfileByJwt(String jwt) throws UserException {
        String email=jwtProvider.getEmailFromJwtToken(jwt);


        User user = userRepository.findByEmail(email);

        if(user==null) {
            throw new UserException("user not exist with email "+email);
        }
//		System.out.println("email user "+user.get().getEmail());
        return user;
    }

    @Override
    public User findUserByEmail(String email) throws UserException {

        User user=userRepository.findByEmail(email);

        if(user!=null) {

            return user;
        }

        throw new UserException("usuario no existe con el correo "+email);
    }
}
