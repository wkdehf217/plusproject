package plus.plusproject.global.exception.user;

import plus.plusproject.global.exception.BusinessException;
import plus.plusproject.global.exception.ErrorCode;

public class NotMatchPasswordAndPasswordConfirm extends BusinessException {

    public NotMatchPasswordAndPasswordConfirm() {
        super(ErrorCode.NOT_MATCH_PASSWORD_AND_PASSWORDCONFIRM);
    }
}
