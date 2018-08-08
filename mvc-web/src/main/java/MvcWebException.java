import com.liupeng.exception.ExceptionCode;
import com.liupeng.exception.MvcException;

/**
 * WEB层异常
 *
 * @author fengdao.lp
 * @date 2018/8/8
 */
public class MvcWebException extends MvcException {
    public MvcWebException(String message) {
        super(message);
    }

    public MvcWebException(Throwable cause) {
        super(cause);
    }

    public MvcWebException(String message, Throwable cause) {
        super(message, cause);
    }

    public MvcWebException(ExceptionCode exceptionCode, Object... args) {
        super(exceptionCode, args);
    }

    public MvcWebException(ExceptionCode exceptionCode, Throwable cause, Object... args) {
        super(exceptionCode, cause, args);
    }

    /**
     * 设置错误码
     *
     * @param exceptionCode 错误码
     * @return 返回值
     */
    @Override
    public MvcWebException setExceptionCode(ExceptionCode exceptionCode) {
        return (MvcWebException)super.setExceptionCode(exceptionCode);
    }
}
