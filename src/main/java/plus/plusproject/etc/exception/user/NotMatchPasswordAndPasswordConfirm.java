package plus.plusproject.etc.exception.user;

import plus.plusproject.etc.exception.BusinessException;
import plus.plusproject.etc.exception.ErrorCode;

public class NotMatchPasswordAndPasswordConfirm extends BusinessException {

    public NotMatchPasswordAndPasswordConfirm() {
        super(ErrorCode.NOT_MATCH_PASSWORD_AND_PASSWORDCONFIRM);
    }
}
