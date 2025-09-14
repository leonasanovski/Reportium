package apps.spring.reportium.service.impl;

import apps.spring.reportium.entity.Institution;
import apps.spring.reportium.entity.Person;
import apps.spring.reportium.entity.enumerations.InstitutionType;
import apps.spring.reportium.repository.InstitutionRepository;
import apps.spring.reportium.service.InstitutionService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class InstitutionServiceImplementation implements InstitutionService {
    private final InstitutionRepository institutionRepository;

    public InstitutionServiceImplementation(InstitutionRepository institutionRepository) {
        this.institutionRepository = institutionRepository;
    }

    @Override
    public List<Institution> getSuitableInstitutions(Person person) {
        List<Institution> allInstitutions = institutionRepository.findAll();
        int personAge = LocalDate.now().getYear() - person.getDateOfBirth().getYear();
        if (personAge < 5) {
            return List.of();
        }
        if (personAge <= 15) {
            return allInstitutions
                    .stream()
                    .filter(inst ->
                            inst.getType() == InstitutionType.PRIMARY_SCHOOL || inst.getType() == InstitutionType.ACADEMY)
                    .toList();
        }
        if (personAge <= 19){
            return allInstitutions
                    .stream()
                    .filter(inst ->
                            inst.getType() == InstitutionType.HIGH_SCHOOL || inst.getType() == InstitutionType.ACADEMY)
                    .toList();
        }
        return allInstitutions
                    .stream()
                    .filter(inst ->
                            inst.getType() == InstitutionType.UNIVERSITY || inst.getType() == InstitutionType.ACADEMY)
                    .toList();
    }
}
