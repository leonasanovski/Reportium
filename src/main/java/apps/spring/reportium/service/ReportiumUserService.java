package apps.spring.reportium.service;

import apps.spring.reportium.entity.ReportiumUser;

import java.util.List;

public interface ReportiumUserService {
    List<ReportiumUser> findAll();
    void updateRole(Integer userId, Integer roleId);
    ReportiumUser findByEmail(String email);
}
