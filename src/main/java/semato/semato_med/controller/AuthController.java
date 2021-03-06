package semato.semato_med.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import semato.semato_med.exception.AppException;
import semato.semato_med.model.Patient;
import semato.semato_med.model.Role;
import semato.semato_med.model.RoleName;
import semato.semato_med.model.User;
import semato.semato_med.payload.ApiResponse;
import semato.semato_med.payload.JwtAuthenticationResponse;
import semato.semato_med.payload.LoginRequest;
import semato.semato_med.payload.SingUpRequest;
import semato.semato_med.repository.PatientRepository;
import semato.semato_med.repository.RoleRepository;
import semato.semato_med.repository.UserRepository;
import semato.semato_med.security.JwtTokenProvider;

import javax.validation.Valid;
import java.util.Collections;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;

    private final UserRepository userRepository;

    private final PatientRepository patientRepository;

    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtTokenProvider tokenProvider;

    public AuthController(AuthenticationManager authenticationManager, UserRepository userRepository, PatientRepository patientRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, JwtTokenProvider tokenProvider) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.patientRepository = patientRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenProvider = tokenProvider;
    }

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.generateToken(authentication);
        return ResponseEntity.ok(new JwtAuthenticationResponse(jwt));
    }

    @PostMapping("/singup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SingUpRequest singUpRequest) {
        if (userRepository.existsByEmail(singUpRequest.getEmail())) {
            return new ResponseEntity<>(new ApiResponse(false, "Email is already taken!"),
                    HttpStatus.BAD_REQUEST);
        }

        User user = new User(
                singUpRequest.getEmail(),
                singUpRequest.getFirstName(),
                singUpRequest.getLastName(),
                singUpRequest.getPassword(),
                singUpRequest.getPhone());

        Patient patient = new Patient(
                user,
                singUpRequest.getPesel(),
                singUpRequest.getBirthDate(),
                singUpRequest.getCity(),
                singUpRequest.getPostalCode(),
                singUpRequest.getStreet(),
                singUpRequest.getHouseNumber());


        user.setPassword(passwordEncoder.encode(user.getPassword()));

        Role userRole = roleRepository.findByName(RoleName.ROLE_PATIENT).orElseThrow(() -> new AppException("Patient Role not set."));

        user.setRoles(Collections.singleton(userRole));

        patientRepository.save(patient);

        return new ResponseEntity<>(new ApiResponse(true, "User registered successfully"), HttpStatus.CREATED);
    }
}
