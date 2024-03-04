package bf.plainte.app;

import bf.plainte.app.service.impl.InitalDataServiceImpl;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@OpenAPIDefinition(info = @Info(/*CONFIG SWAGGER*/
        title = "PLAINTE-API REST",
        description = "SPINGDOC OF PLAINTE-API REST",
        version = "1.0",
        contact = @Contact(name = "PLAINTE-API-DEV-TEAM", email = "canisiushien@gmail.com", url = "http://www.xyz.gov.bf"),
        license = @License(name = "Licence 1.0")))
@SpringBootApplication
@RequiredArgsConstructor
public class PlainteEnLigneApplication implements CommandLineRunner {

    private final InitalDataServiceImpl service;

    public static void main(String[] args) {
        SpringApplication.run(PlainteEnLigneApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        service.saveInitAuthorities();
        service.saveInitProfil();
        service.saveInitUser();
    }
}
