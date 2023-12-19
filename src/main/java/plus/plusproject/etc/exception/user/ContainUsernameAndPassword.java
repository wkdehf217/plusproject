package plus.plusproject.etc.exception.user;

import plus.plusproject.etc.exception.BusinessException;
import plus.plusproject.etc.exception.ErrorCode;

public class ContainUsernameAndPassword extends BusinessException {

    public ContainUsernameAndPassword() {
        super(ErrorCode.CONTAIN_USERNAME_AND_PASSWORD);
    }
}