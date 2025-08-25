package apps.spring.reportium.service;

import apps.spring.reportium.entity.UserProfileLog;
import apps.spring.reportium.entity.enumerations.LogType;

public interface UserLogService {
    void createLog(Integer userId, LogType instruction);
}
