package apps.spring.reportium.service;

import apps.spring.reportium.entity.DTOs.ReportFilterDTO;

public interface FilterSessionService {
    void save(ReportFilterDTO filterDTO);

}
