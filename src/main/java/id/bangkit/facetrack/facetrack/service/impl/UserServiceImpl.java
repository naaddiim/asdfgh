package id.bangkit.facetrack.facetrack.service.impl;

import id.bangkit.facetrack.facetrack.config.JWTProperties;
import id.bangkit.facetrack.facetrack.dto.NewUserDTO;
import id.bangkit.facetrack.facetrack.dto.UserDTO;
import id.bangkit.facetrack.facetrack.dto.request.users.ChangePasswordRequest;
import id.bangkit.facetrack.facetrack.dto.request.users.RegisterRequest;
import id.bangkit.facetrack.facetrack.dto.request.users.ForgotPasswordRequest;
import id.bangkit.facetrack.facetrack.dto.request.users.UpdateUserRequest;
import id.bangkit.facetrack.facetrack.entity.OTP;
import id.bangkit.facetrack.facetrack.entity.User;
import id.bangkit.facetrack.facetrack.exception.EmailNotFoundException;
import id.bangkit.facetrack.facetrack.exception.EmailUnavailableException;
import id.bangkit.facetrack.facetrack.exception.ExpiredOTPException;
import id.bangkit.facetrack.facetrack.exception.InvalidOTPException;
import id.bangkit.facetrack.facetrack.exception.UserNotFoundException;
import id.bangkit.facetrack.facetrack.mappers.MapTo;
import id.bangkit.facetrack.facetrack.repository.OtpRepository;
import id.bangkit.facetrack.facetrack.repository.UserRepository;
import id.bangkit.facetrack.facetrack.service.CustomUserDetailsService;
import id.bangkit.facetrack.facetrack.service.JWTService;
import id.bangkit.facetrack.facetrack.service.UserService;
import lombok.RequiredArgsConstructor;
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
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final OtpRepository otpRepository;
    private final PasswordEncoder passwordEncoder;
    private final CustomUserDetailsService userDetailsService;
    private final JWTService jwtService;
    private final JWTProperties jwtProperties;
    private final JavaMailSender javaMailSender;
    private final MapTo<User, UserDTO> userMapper;

    @Override
    public NewUserDTO createUser(RegisterRequest request) {
        User found = userRepository.findByEmail(request.email());
        if (found != null) {
            throw new EmailUnavailableException("This email cannot be used");
        }
        User newUser = User.builder()
                .email(request.email())
                .password(request.password())
                .build();
        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
        User createdUser = userRepository.save(newUser);

        UserDetails userDetails = userDetailsService.loadUserByUsername(createdUser.getEmail());
        String accessToken = generateAccessToken(userDetails);
        return new NewUserDTO(createdUser.getUserId(), createdUser.getEmail(), accessToken);
    }

    @Override
    public UserDTO findUserById(int userId) {
        return userMapper.mapTo(userRepository.findById(userId)
                .orElseThrow(
                        () -> new UserNotFoundException("cannot find user")));
    }

    @Override
    public UserDTO updateUser(UpdateUserRequest request, int userId) {
        User updatedUser = userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException("cannot find user"));
        updatedUser.setNama(request.nama());
        updatedUser.setNoTelp(request.noTelp());
        updatedUser.setGender(request.gender());
        return userMapper.mapTo(userRepository.save(updatedUser));
    }

    @Override
    public UserDTO findUserByBearerToken() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userMapper.mapTo(userRepository.findByEmail(email));
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
        otpRepository.save(newOtp);

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(found.getEmail());
        mailMessage.setSubject("[Facetrack] Please verify your OTP");
        mailMessage.setText(generateMailBody(found.getEmail(), otp));
        this.sendEmail(mailMessage);
        return found.getEmail();
    }

    private String generateMailBody(String email, String otp) {
        return """
                Hey %s !

                Percobaan untuk mereset password telah dilakukan. Untuk melengkapi proses reset password, masukan kode OTP berikut pada program Facetrack.

                OTP kamu : %s

                Jika kamu tidak melakukan percobaan untuk mereset password, maka password anda sekarang sudah tidak aman. Silahkan hubungi Jafar melalui https://github.com/jafar144 untuk membuat laporan dan pengecekan keamanan akun anda.

                Terima Kasih,
                The Facetrack Team

                """
                .formatted(email, otp);
    }

    @Override
    public void confirmOTP(ForgotPasswordRequest request) {
        OTP found = otpRepository.findFirstByEmailAndIsUsedOrderByCreatedAtDesc(request.email(), false).orElseThrow(
                () -> new EmailNotFoundException("Email tidak tepat"));
        if (!found.getOtp().equals(request.otp())) {
            throw new InvalidOTPException("OTP tidak tepat");
        }

        // check
        boolean before = new Date().before(found.getExpiredAt());
        if (!before) {
            found.setUsed(true);
            otpRepository.save(found);
            throw new ExpiredOTPException("OTP sudah tidak bisa dipakai");
        }
        found.setUsed(true);
        otpRepository.save(found);
    }

    @Override
    public UserDTO changePassword(ChangePasswordRequest request) {
        User found = userRepository.findByEmail(request.email());
        if (found == null) {
            throw new EmailNotFoundException("Email belum terdaftar");
        }
        String newPassword = passwordEncoder.encode(request.newPassword());
        found.setPassword(newPassword);
        return userMapper.mapTo(userRepository.save(found));
    }

    private String generateOTP(int numOfCharacter) {
        String otp = "";
        Random random = new Random();
        int min = 1, max = 9;
        for (int i = 0; i < numOfCharacter; i++) {
            int randomNumber = random.nextInt(max - min + 1) + min;
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