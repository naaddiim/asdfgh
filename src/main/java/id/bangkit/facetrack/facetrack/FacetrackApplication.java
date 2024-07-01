package id.bangkit.facetrack.facetrack;

import id.bangkit.facetrack.facetrack.entity.Gender;
import id.bangkit.facetrack.facetrack.entity.Problem;
import id.bangkit.facetrack.facetrack.entity.Role;
import id.bangkit.facetrack.facetrack.entity.User;
import id.bangkit.facetrack.facetrack.repository.ProblemRepository;
import id.bangkit.facetrack.facetrack.repository.UserRepository;
import id.bangkit.facetrack.facetrack.service.FileStorageService;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class FacetrackApplication implements CommandLineRunner {
	@Autowired
	PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	@Resource
	FileStorageService storageService;

	@Autowired
	ProblemRepository problemRepository;

	@Autowired
	UserRepository userRepository;


	public static void main(String[] args) {
		SpringApplication.run(FacetrackApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		storageService.deleteAll();
		storageService.init();
		List<User> users = new ArrayList<>();
		users.add(User.builder().nama("nadim").email("nadimwkwk@gmail.com").gender(Gender.MALE).noTelp("081252347779").password(passwordEncoder.encode("Nadim456*-")).role(Role.USER).build());
		users.add(User.builder().nama("jafar").email("jafar@gmail.com").gender(Gender.FEMALE).noTelp("0811XXXXX").password(passwordEncoder.encode("Nadim456*-")).role(Role.USER).build());
		userRepository.saveAll(users);
		List<Problem> problems = new ArrayList<>();
		problems.add(Problem.builder().nama("Jerawat").deskripsi("Deskripsi 1").saran("Saran 1").build());
		problems.add(Problem.builder().nama("Komedo").deskripsi("Deskripsi 2").saran("Saran 2").build());
		problems.add(Problem.builder().nama("Flek Hitam").deskripsi("Deskripsi 3").saran("Saran 3").build());
		problems.add(Problem.builder().nama("Eksim").deskripsi("Deskripsi 4").saran("Saran 4").build());
		problems.add(Problem.builder().nama("Psoriasis").deskripsi("Deskripsi 5").saran("Saran 5").build());
		problemRepository.saveAll(problems);
	}
}
