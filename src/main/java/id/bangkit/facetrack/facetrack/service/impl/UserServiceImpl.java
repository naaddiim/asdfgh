package id.bangkit.facetrack.facetrack.service.impl;

import id.bangkit.facetrack.facetrack.config.JWTProperties;
import id.bangkit.facetrack.facetrack.dto.ChangePasswordRequest;
import id.bangkit.facetrack.facetrack.dto.ForgotPasswordRequest;
import id.bangkit.facetrack.facetrack.dto.RegisterUserResponse;
import id.bangkit.facetrack.facetrack.dto.UpdateUserRequest;
import id.bangkit.facetrack.facetrack.entity.OTP;
import id.bangkit.facetrack.facetrack.entity.User;
import id.bangkit.facetrack.facetrack.exception.EmailNotFoundException;
import id.bangkit.facetrack.facetrack.exception.EmailUnavailableException;
import id.bangkit.facetrack.facetrack.exception.UserNotFoundException;
import id.bangkit.facetrack.facetrack.repository.OtpRepository;
import id.bangkit.facetrack.facetrack.repository.UserRepository;
import id.bangkit.facetrack.facetrack.service.CustomUserDetailsService;
import id.bangkit.facetrack.facetrack.service.JWTService;
import id.bangkit.facetrack.facetrack.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final OtpRepository otpRepository;
    private final PasswordEncoder passwordEncoder;
    private final CustomUserDetailsService userDetailsService;
    private final JWTService jwtService;
    private final JWTProperties jwtProperties;
    private final JavaMailSender javaMailSender;


    @Override
    public RegisterUserResponse createUser(User newUser) {
        User found = userRepository.findByEmail(newUser.getEmail());
        if (found != null) {
            throw new EmailUnavailableException("This email cannot be used");
        }
        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
        User createdUser = userRepository.save(newUser);

        UserDetails userDetails = userDetailsService.loadUserByUsername(createdUser.getEmail());
        String accessToken = generateAccessToken(userDetails);
        return new RegisterUserResponse(createdUser.getUserId(), createdUser.getEmail(), accessToken);
    }

    @Override
    public User findUserById(int userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("cannot find user"));
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User updateUser(UpdateUserRequest request, int userId) {
        User updatedUser = userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException("cannot find user")
        );
        updatedUser.setNama(request.nama());
        updatedUser.setNoTelp(request.noTelp());
        updatedUser.setGender(request.gender());
        return userRepository.save(updatedUser);
    }

    @Override
    public User findUserByBearerToken() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByEmail(email);
    }

    @Override
    @Async
    public void sendEmail(SimpleMailMessage email) {
        javaMailSender.send(email);
    }

    @Override
    public String forgotPassword(ForgotPasswordRequest request) {
        User found = userRepository.findByEmail(request.email());
        if (found == null) {
            throw new EmailNotFoundException("Email belum terdaftar");
        }
        String otp = generateOTP(4);
        // save ke database
        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MINUTE, 15);
        Date expiredDate = cal.getTime();
        OTP newOtp = OTP.builder()
                .otp(otp)
                .email(found.getEmail())
                .expiredAt(expiredDate)
                .build();
        OTP save = otpRepository.save(newOtp);

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(found.getEmail());
        mailMessage.setSubject("[Facetrack] Please verify your OTP");
        mailMessage.setText(generateMailBody(found.getEmail(), otp));
        this.sendEmail(mailMessage);
        return found.getEmail();
    }

    private String generateMailBody(String email, String otp) {
        return
     """
     Hey %s !
          
     Percobaan untuk mereset password telah dilakukan. Untuk melengkapi proses reset password, masukan kode OTP berikut pada program Facetrack.
          
     OTP kamu : %s
          
     Jika kamu tidak melakukan percobaan untuk mereset password, maka password anda sekarang sudah tidak aman. Silahkan hubungi Jafar melalui https://github.com/jafar144 untuk membuat laporan dan pengecekan keamanan akun anda.
         
     Terima Kasih,
     The Facetrack Team
     
     """.formatted(email, otp);
    }

    @Override
    public boolean confirmOTP(ForgotPasswordRequest request) {
        OTP found = otpRepository.findFirstByEmailAndIsUsedOrderByCreatedAtDesc(request.email(), false).orElseThrow(
                () -> new EmailNotFoundException("Email tidak tepat")
        );
        log.info("found : {}", found);
        if (!found.getOtp().equals(request.otp())) {
            return false;
        }

        // check
        boolean before = new Date().before(found.getExpiredAt());
        if (!before) {
            found.setUsed(true);
            otpRepository.save(found);
            return false;
        }
        found.setUsed(true);
        otpRepository.save(found);
        return true;
    }

    @Override
    public User changePassword(ChangePasswordRequest request) {
        User found = userRepository.findByEmail(request.email());
        if (found == null) {
            throw new EmailNotFoundException("Email belum terdaftar");
        }
        String newPassword = passwordEncoder.encode(request.newPassword());
        found.setPassword(newPassword);
        return userRepository.save(found);
    }


    private String generateOTP(int numOfCharacter) {
        String otp = "";
        Random random = new Random();
        int min=1,max=9;
        for (int i = 0; i < numOfCharacter; i++) {
            int randomNumber = random.nextInt(max-min + 1) + min;
            otp += String.valueOf(randomNumber);
        }
        return otp;
    }


    private String generateAccessToken(UserDetails userDetails) {
        return jwtService.generateToken(userDetails,
                new Date(System.currentTimeMillis() + jwtProperties.getAccessTokenExpiration()),
                new HashMap<>());
    }
}