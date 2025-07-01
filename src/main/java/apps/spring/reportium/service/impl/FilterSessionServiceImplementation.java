package apps.spring.reportium.service.impl;

import apps.spring.reportium.entity.DTOs.ReportFilterDTO;
import apps.spring.reportium.entity.FilterSession;
import apps.spring.reportium.repository.FilterSessionRepository;
import apps.spring.reportium.service.FilterSessionService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class FilterSessionServiceImplementation implements FilterSessionService {
    private final FilterSessionRepository filterSessionRepository;
    private final AuthenticateServiceImplementation authenticateServiceImplementation;
    public FilterSessionServiceImplementation(FilterSessionRepository filterSessionRepository, AuthenticateServiceImplementation authenticateServiceImplementation) {
        this.filterSessionRepository = filterSessionRepository;
        this.authenticateServiceImplementation = authenticateServiceImplementation;
    }

    @Override
    public void save(ReportFilterDTO filterDTO) {
        FilterSession obj_to_save = new FilterSession();
        obj_to_save.setFilterDescription(filterDTO.toString());
        obj_to_save.setReportiumUser(authenticateServiceImplementation.getCurrentUser());
        obj_to_save.setSearchedAt(LocalDateTime.now());
        filterSessionRepository.save(obj_to_save);
    }
}
