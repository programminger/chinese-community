package life.zl.community.exception;

public enum  CustomizeErrorCode implements ICustomizeErrorCode {

    QUESTION_NOT_FOUND(2001,"问题不在或已被删除?"),
    TARGET_PARAM_NOT_FOUND(2002,"未选中任何问题和评论回复"),
    NO_LOGIN(2003,"用户未登录"),
    SYSTEM_ERROR(2004,"服务器繁忙，请稍后再试"),
    TYPE_PARAM_ERROR(2005,"评论错误"),
    COMMENT_NOT_FOUND(2005,"评论错误");

    private String message;
    private Integer code;

    CustomizeErrorCode(Integer code, String message) {
        this.message = message;
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public Integer getCode() {
        return code;
    }

}
