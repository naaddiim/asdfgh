package id.bangkit.facetrack.facetrack.service;

import id.bangkit.facetrack.facetrack.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        id.bangkit.facetrack.facetrack.entity.User user = userRepository.findByEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException("not found");
        }
        return mapToUserDetails(user);

    }

    private UserDetails mapToUserDetails(id.bangkit.facetrack.facetrack.entity.User user) {
        return User.builder()
                .username(user.getEmail())
                .password(user.getPassword())
                .roles(user.getRole().name())
                .build();
    }
}
