import com.liupeng.exception.ExceptionCode;
import com.liupeng.exception.MvcException;

/**
 * Service层异常
 *
 * @author fengdao.lp
 * @date 2018/8/8
 */
public class MvcServiceException extends MvcException {
    public MvcServiceException(String message) {
        super(message);
    }

    public MvcServiceException(Throwable cause) {
        super(cause);
    }

    public MvcServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public MvcServiceException(ExceptionCode exceptionCode, Object... args) {
        super(exceptionCode, args);
    }

    public MvcServiceException(ExceptionCode exceptionCode, Throwable cause, Object... args) {
        super(exceptionCode, cause, args);
    }

    /**
     * 设置错误码
     *
     * @param exceptionCode 错误码
     * @return 返回值
     */
    @Override
    public MvcServiceException setExceptionCode(ExceptionCode exceptionCode) {
        return (MvcServiceException)super.setExceptionCode(exceptionCode);
    }
}
