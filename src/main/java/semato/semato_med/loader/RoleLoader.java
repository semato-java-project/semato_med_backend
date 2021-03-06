package semato.semato_med.loader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import semato.semato_med.model.Role;
import semato.semato_med.model.RoleName;
import semato.semato_med.repository.RoleRepository;

@Profile("dev|test")
@Component
@Order(1)
public class RoleLoader  implements ApplicationRunner {

    @Autowired
    private RoleRepository roleRepository;

    public void run(ApplicationArguments args) {
        if(! roleRepository.findByName(RoleName.ROLE_PATIENT).isPresent()) {
            roleRepository.save(new Role(RoleName.ROLE_PATIENT));
        }

        if(! roleRepository.findByName(RoleName.ROLE_PHYSICIAN).isPresent()) {
            roleRepository.save(new Role(RoleName.ROLE_PHYSICIAN));
        }

        if(! roleRepository.findByName(RoleName.ROLE_ADMIN).isPresent()) {
            roleRepository.save(new Role(RoleName.ROLE_ADMIN));
        }

    }

}
