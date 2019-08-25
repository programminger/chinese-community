package life.zl.community.exception;

public enum  CustomizeErrorCode implements ICustomizeErrorCode {

    QUESTION_NOT_FOUND("问题不在或已被删除?");

    private String message;

    CustomizeErrorCode(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }


}
