package semato.semato_med;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import javax.annotation.PostConstruct;
import java.time.ZoneOffset;
import java.util.TimeZone;


@SpringBootApplication
@EntityScan(basePackageClasses = {
        SematoMedApplication.class,
        Jsr310JpaConverters.class
})
public class SematoMedApplication {

    @PostConstruct
    void init() {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }

    public static void main(String[] args) {
        SpringApplication.run(SematoMedApplication.class, args);
    }


}
