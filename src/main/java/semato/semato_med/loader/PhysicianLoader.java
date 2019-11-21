package semato.semato_med.loader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import semato.semato_med.exception.AppException;
import semato.semato_med.model.*;
import semato.semato_med.repository.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

@Component
@Order(2)
public class PhysicianLoader implements ApplicationRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PhysicianRepository physicianRepository;

    @Autowired
    private SpecialityRepository specialityRepository;

    @Autowired
    private ClinicRepository clinicRepository;

    @Autowired
    private WorkScheduleRepository workScheduleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public static final String EMAIL = "strange@example.com";
    public static final String PASSWORD = "qwerty";

    @Override
    public void run(ApplicationArguments args) {

        if(! userRepository.findByEmail(EMAIL).isPresent()) {

            User user = new User();
            user.setFirstName("Stephen");
            user.setLastName("Strange");
            user.setEmail(EMAIL);
            user.setPhone("123-123-123");
            user.setPassword(passwordEncoder.encode(PASSWORD));
            Role userRole = roleRepository.findByName(RoleName.ROLE_PHYSICIAN).orElseThrow(() -> new AppException("Phisician Role not set."));
            user.setRoles(Collections.singleton(userRole));

            Physician physician = new Physician();
            physician.setMedicalDegrees("doktor");
            physician.setNote("Stephen Strange (Benedict Cumberbatch) jest aroganckim i ambitnym neurochirurgiem.");
            physician.setTitle("Dr");
            physician.setImage_url("https://upload.wikimedia.org/wikipedia/en/0/0a/Benedict_Cumberbatch_as_Doctor_Strange.jpg");

            Speciality speciality = specialityRepository.findByName("Neurochirurgia").get();
            Set<Speciality> specialitySet = new HashSet<>();

            specialitySet.add(speciality);
            physician.setSpecialitySet(specialitySet);

            physician.setUser(user);
            physicianRepository.save(physician);

            WorkSchedule workScheduleEntry = new WorkSchedule();
            workScheduleEntry.setPhysician(physician);
            workScheduleEntry.setClinic(clinicRepository.findByEmail(ClinicLoader.EMAIL).get());
            workScheduleEntry.setDateTimeStart(LocalDateTime.of(LocalDate.now().plusDays(1), LocalTime.NOON.minusHours(4)));
            workScheduleEntry.setDateTimeEnd(LocalDateTime.of(LocalDate.now().plusDays(1), LocalTime.NOON.plusHours(4)));

            workScheduleRepository.save(workScheduleEntry);

        }
    }
}
