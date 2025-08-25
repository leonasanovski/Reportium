package apps.spring.reportium.service.impl;

import apps.spring.reportium.entity.*;
import apps.spring.reportium.entity.dto.PersonReportSummaryDTO;
import apps.spring.reportium.entity.enumerations.Gender;
import apps.spring.reportium.entity.exceptions.PersonNotFoundException;
import apps.spring.reportium.repository.*;
import apps.spring.reportium.service.PersonService;
import apps.spring.reportium.service.UserLogService;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

@Service
public class PersonServiceImplementation implements PersonService {
    private final PersonRepository personRepository;
    private final ReportRepository reportRepository;
    private final CriminalReportRepository criminalReportRepository;
    private final AcademicReportRepository academicReportRepository;
    private final MedicalReportRepository medicalReportRepository;
    private final EmploymentReportRepository employmentReportRepository;
    private final ReportiumUserRepository reportiumUserRepository;
    private final UserProfileLogRepository userProfileLogRepository;

    public PersonServiceImplementation(PersonRepository personRepository, ReportRepository reportRepository,
                                       CriminalReportRepository criminalReportRepository,
                                       AcademicReportRepository academicReportRepository,
                                       MedicalReportRepository medicalReportRepository,
                                       EmploymentReportRepository employmentReportRepository,
                                       ReportiumUserRepository reportiumUserRepository,
                                       UserProfileLogRepository userProfileLogRepository) {
        this.personRepository = personRepository;
        this.reportRepository = reportRepository;
        this.criminalReportRepository = criminalReportRepository;
        this.academicReportRepository = academicReportRepository;
        this.medicalReportRepository = medicalReportRepository;
        this.employmentReportRepository = employmentReportRepository;
        this.reportiumUserRepository = reportiumUserRepository;
        this.userProfileLogRepository = userProfileLogRepository;
    }

    @Transactional
    @Override
    public void deletePerson(String userEmail, Long personId) {
        Person stub = personRepository.findByStubTrue().orElseGet(() -> {
            Person newStub = new Person();
            newStub.setEmbg("UNIQUE-EMBG");
            newStub.setName("Stub");
            newStub.setSurname("Collector");
            newStub.setGender(Gender.MALE);
            newStub.setDateOfBirth(LocalDate.of(1900, 10, 10));
            newStub.setAlive(false);
            newStub.setAddress("N/A");
            newStub.setContactPhone("N/A");
            newStub.setStub(true);
            return personRepository.save(newStub);
        });

        Person targetToDelete = personRepository.findById(personId).orElseThrow(() -> new PersonNotFoundException("Person with id=" + personId + " not found"));

        if (targetToDelete.getPersonId() == stub.getPersonId()) {
            throw new IllegalStateException("Archive (stub) person cannot be deleted.");
        }

        int totalReportsMoved = reportRepository.reassignReportsToStub((long) targetToDelete.getPersonId(), (long) stub.getPersonId());
        String logMessage = String.format("Admin User with email %s has deleted Person: %s %s. All his %d reports were moved to stub person.",
                userEmail, targetToDelete.getName(), targetToDelete.getSurname(),totalReportsMoved);
        reportiumUserRepository.findByEmail(userEmail).ifPresent(reportiumUser -> {
            UserProfileLog userProfileLog = new UserProfileLog();
            userProfileLog.setChangedAt(LocalDateTime.now());
            userProfileLog.setChangeDescription(logMessage);
            userProfileLog.setUserProfile(reportiumUser.getProfile());
            userProfileLogRepository.save(userProfileLog);
        });
        personRepository.delete(targetToDelete);
    }


    @Override
    public List<PersonReportSummaryDTO> personSummaryReportData() {
        List<Person> all_persons = personRepository.findAll();
        List<PersonReportSummaryDTO> personReportSummaryDTOS = new ArrayList<>();
        for (Person person : all_persons) {
            PersonReportSummaryDTO object = new PersonReportSummaryDTO();
            object.setPersonId(person.getPersonId());
            object.setName(person.getName());
            object.setSurname(person.getSurname());
            object.setAddress(person.getAddress());
            object.setBirthDate(person.getDateOfBirth());
            object.setGender(person.getGender());
            object.setIsAlive(person.isAlive());
            object.setAge(Period.between(person.getDateOfBirth(), LocalDate.now()).getYears());

            object.setHasCriminalReport(criminalReportRepository.existsByPersonId(person.getPersonId()));
            object.setHasMedicalReport(medicalReportRepository.existsByPersonId(person.getPersonId()));
            object.setHasAcademicReport(academicReportRepository.existsByPersonId(person.getPersonId()));
            object.setHasEmploymentReport(employmentReportRepository.existsByPersonId(person.getPersonId()));
            personReportSummaryDTOS.add(object);
        }
        return personReportSummaryDTOS;
    }

    @Override
    public Person findById(Integer personId) {
        return personRepository.findById(Long.valueOf(personId)).orElseThrow(() -> new IllegalArgumentException(String.format("Person with id=%d not found", personId)));
    }

    @Override
    public List<CriminalReport> getPersonsCr(Integer personId) {
        return criminalReportRepository.findAllByPersonId(personId);
    }

    @Override
    public List<AcademicReport> getPersonsAr(Integer personId) {
        return academicReportRepository.findAllByPersonId(personId);
    }

    @Override
    public List<EmploymentReport> getPersonsEr(Integer personId) {
        return employmentReportRepository.findAllByPersonId(personId);
    }

    @Override
    public List<MedicalReport> getPersonsMr(Integer personId) {
        return medicalReportRepository.findAllByPersonId(personId);
    }
}
