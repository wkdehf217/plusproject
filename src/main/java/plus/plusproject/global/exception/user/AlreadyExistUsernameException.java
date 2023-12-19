package plus.plusproject.global.exception.user;

import plus.plusproject.global.exception.BusinessException;
import plus.plusproject.global.exception.ErrorCode;

public class AlreadyExistUsernameException extends BusinessException {

    public AlreadyExistUsernameException() {
        super(ErrorCode.ALREADY_EXIST_USERNAME_EXCEPTION);
    }
}