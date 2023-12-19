package plus.plusproject.etc.exception;

public class BusinessException extends RuntimeException {

    private final int status;

    public BusinessException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.status = errorCode.getStatus();
    }

    public BusinessException(ErrorCode errorCode, Throwable cause) {
        super(errorCode.getMessage(), cause);
        this.status = errorCode.getStatus();
    }

}
