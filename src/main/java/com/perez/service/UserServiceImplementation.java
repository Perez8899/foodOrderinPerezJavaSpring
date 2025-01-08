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

  /*  private PasswordResetTokenRepository passwordResetTokenRepository;
    private JavaMailSender javaMailSender;

    public UserServiceImplementation(
            UserRepository userRepository,
            JwtProvider jwtProvider,
            PasswordEncoder passwordEncoder,
            PasswordResetTokenRepository passwordResetTokenRepository,
            JavaMailSender javaMailSender) {

        this.userRepository=userRepository;
        this.jwtProvider=jwtProvider;
        this.passwordEncoder=passwordEncoder;
        this.passwordResetTokenRepository=passwordResetTokenRepository;
        this.javaMailSender=javaMailSender;

    }
*/
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

    /*
            @Override
            public List<User> getPenddingRestaurantOwner() {

                return userRepository.getPenddingRestaurantOwners();
            }

            @Override
            public void updatePassword(User user, String newPassword) {
                user.setPassword(passwordEncoder.encode(newPassword));
                userRepository.save(user);
            }

            @Override
            public void sendPasswordResetEmail(User user) {

                // Generate a random token (you might want to use a library for this)
                String resetToken = generateRandomToken();

                // Calculate expiry date
                Date expiryDate = calculateExpiryDate();

                // Save the token in the database
                PasswordResetToken passwordResetToken = new PasswordResetToken(resetToken,user,expiryDate);
                passwordResetTokenRepository.save(passwordResetToken);

                // Send an email containing the reset link
                sendEmail(user.getEmail(), "Password Reset", "Click the following link to reset your password: http://localhost:3000/account/reset-password?token=" + resetToken);
            }
            private void sendEmail(String to, String subject, String message) {
                SimpleMailMessage mailMessage = new SimpleMailMessage();

                mailMessage.setTo(to);
                mailMessage.setSubject(subject);
                mailMessage.setText(message);

                javaMailSender.send(mailMessage);
            }
            private String generateRandomToken() {
                return UUID.randomUUID().toString();
            }
            private Date calculateExpiryDate() {
                Calendar cal = Calendar.getInstance();
                cal.setTime(new Date());
                cal.add(Calendar.MINUTE, 10);
                return cal.getTime();
            }
        */
    @Override
    public User findUserByEmail(String email) throws UserException {

        User user=userRepository.findByEmail(email);

        if(user!=null) {

            return user;
        }

        throw new UserException("user not exist with username "+email);
    }
}
