package com.liupeng.sqlexec.util;

import javax.servlet.http.HttpServletRequest;

import com.liupeng.annotationdriven.response.ResponseVo;
import com.liupeng.sqlexec.enums.DMLTypeEnum;
import com.liupeng.sqlexec.vo.SqlRequestVo;
import com.liupeng.sqlexec.vo.UserVo;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * SQL执行器帮助类
 *
 * @author fengdao.lp
 * @date 2018/6/7
 */
public class SqlUtil {
    private static final Logger logger = LoggerFactory.getLogger(SqlUtil.class);

    /**
     * SQL格式校验
     */
    public static ResponseVo checkSql(String sql) {
        ResponseVo vo = new ResponseVo();
        if (StringUtils.isEmpty(sql)) {
            vo.setSuccess(false);
            vo.setErrorCode(1);
            vo.setMsg("sql执行异常,原因:参数为空。");
            return vo;
        }

        if (!sql.toLowerCase().contains("insert") && !sql.toLowerCase().contains("where")) {
            vo.setSuccess(false);
            vo.setErrorCode(1);
            vo.setMsg("sql执行异常,原因:非insert语句必须添加 where！");
            return vo;
        }

        if (!sql.toLowerCase().contains("insert") && !sql.toLowerCase().contains("limit")) {
            vo.setSuccess(false);
            vo.setErrorCode(1);
            vo.setMsg("sql执行异常,原因:非insert语句必须添加 limit！");
            return vo;
        }
        return vo;
    }

    /**
     * 获取第一个参数
     */
    public static HttpServletRequest getHttpServletRequestArg(Object[] args) {
        HttpServletRequest request = null;
        if (null == args || 0 >= args.length) {
            logger.error("HttpServletRequest 参数为空");
            return request;
        }
        if (args[0] instanceof HttpServletRequest) {
            request = (HttpServletRequest)args[0];
        }
        return request;
    }

    /**
     * 获取第二个参数
     */
    public static SqlRequestVo getSqlRequestVoArg(Object[] args) {
        SqlRequestVo request = null;
        if (null == args || 1 >= args.length) {
            logger.error("SqlRequestVo 参数为空");
            return request;
        }
        if (args[1] instanceof SqlRequestVo) {
            request = (SqlRequestVo)args[1];
        }
        return request;
    }

    /**
     * 设置返回VO
     */
    public static void setResposeVo(ResponseVo vo, boolean success, int errorCode, String msg) {
        if (null != vo) {
            vo.setSuccess(success);
            vo.setErrorCode(errorCode);
            vo.setMsg(msg);
        }
    }

    /**
     * 组装用户
     */
    public static UserVo buildUserVo(HttpServletRequest httpReq, SqlRequestVo sqlReq) {
        UserVo vo = null;
        if (null != sqlReq && null != httpReq) {
            if (sqlReq.getUid() > 0) {
                vo = new UserVo();
                vo.setIp(getRealIPAddr(httpReq));
                vo.setPwd(sqlReq.getPassword());
                vo.setUid(sqlReq.getUid());
            }
        }
        return vo;
    }

    /**
     * 获取IP地址
     */
    public static String getRealIPAddr(HttpServletRequest request) {
        String ip = "";
        if (null != request) {
            ip = request.getHeader("x-forwarded-for");
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip) || "null".equalsIgnoreCase(ip)) {
                ip = request.getHeader("Proxy-Client-IP");
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip) || "null".equalsIgnoreCase(ip)) {
                ip = request.getHeader("WL-Proxy-Client-IP");
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip) || "null".equalsIgnoreCase(ip)) {
                ip = request.getRemoteAddr();
            }
        }
        return ip;
    }

    /**
     * 根据SQL判断类型：增删改查 <br>
     */
    public static DMLTypeEnum checkDMLType(String sql) {
        if (sql.trim().toLowerCase().startsWith("insert")) {
            return DMLTypeEnum.INSERT;
        }
        if (sql.trim().toLowerCase().startsWith("delete")) {
            return DMLTypeEnum.DELETE;
        }
        if (sql.trim().toLowerCase().startsWith("update")) {
            return DMLTypeEnum.UPDATE;
        }
        if (sql.trim().toLowerCase().startsWith("select")) {
            return DMLTypeEnum.SELECT;
        }
        return DMLTypeEnum.UNKNOW;
    }
}
