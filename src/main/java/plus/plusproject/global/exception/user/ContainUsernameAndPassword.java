package plus.plusproject.global.exception.user;

import plus.plusproject.global.exception.BusinessException;
import plus.plusproject.global.exception.ErrorCode;

public class ContainUsernameAndPassword extends BusinessException {

    public ContainUsernameAndPassword() {
        super(ErrorCode.CONTAIN_USERNAME_AND_PASSWORD);
    }
}