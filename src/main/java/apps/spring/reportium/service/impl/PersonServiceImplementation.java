package apps.spring.reportium.service.impl;

import apps.spring.reportium.entity.*;
import apps.spring.reportium.entity.DTOs.PersonReportSummaryDTO;
import apps.spring.reportium.repository.*;
import apps.spring.reportium.service.PersonService;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

@Service
public class PersonServiceImplementation implements PersonService {
    private final PersonRepository personRepository;
    private final CriminalReportRepository criminalReportRepository;
    private final AcademicReportRepository academicReportRepository;
    private final MedicalReportRepository medicalReportRepository;
    private final EmploymentReportRepository employmentReportRepository;
    public PersonServiceImplementation(PersonRepository personRepository,
                                       CriminalReportRepository criminalReportRepository,
                                       AcademicReportRepository academicReportRepository,
                                       MedicalReportRepository medicalReportRepository,
                                       EmploymentReportRepository employmentReportRepository) {
        this.personRepository = personRepository;
        this.criminalReportRepository = criminalReportRepository;
        this.academicReportRepository = academicReportRepository;
        this.medicalReportRepository = medicalReportRepository;
        this.employmentReportRepository = employmentReportRepository;
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
