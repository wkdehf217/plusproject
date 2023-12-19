package plus.plusproject.etc.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {
    // 회원
    NOT_MATCH_PASSWORD_AND_PASSWORDCONFIRM(400, "입력하신 비밀번호와 비밀번호 확인이 다릅니다."),
    CONTAIN_USERNAME_AND_PASSWORD(400, "입력하신 비밀번호가 아이디에 포함됩니다."),
    ALREADY_EXIST_USERNAME_EXCEPTION(409, "이미 존재하는 이름입니다.");

    private final int status;

    private final String message;

    ErrorCode(int status, String message) {
        this.status = status;
        this.message = message;
    }
}
