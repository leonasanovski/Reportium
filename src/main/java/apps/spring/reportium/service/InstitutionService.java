package apps.spring.reportium.service;

import apps.spring.reportium.entity.Institution;
import apps.spring.reportium.entity.Person;

import java.util.List;

public interface InstitutionService {
    public List<Institution> getSuitableInstitutions(Person person);
}
