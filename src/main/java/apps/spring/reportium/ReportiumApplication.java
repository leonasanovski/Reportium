package apps.spring.reportium;

import apps.spring.reportium.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.concurrent.atomic.AtomicInteger;

@SpringBootApplication
public class ReportiumApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReportiumApplication.class, args);
    }

    //    Test
//    @Bean
//    public CommandLineRunner testRepositories(CriminalReportRepository test_repo) {
//        return args -> {
//            System.out.println("All criminal reports:");
//            test_repo.fetchAllWithReportAndPerson().forEach(cr -> {
//                int id = cr.getReportId();
//                String name = cr.getReport().getPerson().getName();
//                String surname = cr.getReport().getPerson().getSurname();
//                String location = cr.getLocation();
//                String crime = cr.getCrimeType().getLabel();
//
//                System.out.println(String.format("%d: Person: %s %s | Crime: %s | Location: %s",
//                        id, name, surname, crime, location));
//            });
//        };
//    }
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }
}
