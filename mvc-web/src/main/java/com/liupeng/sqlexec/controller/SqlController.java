package com.liupeng.sqlexec.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.liupeng.annotationdriven.response.ResponseVo;
import com.liupeng.sqlexec.service.sqlexe.ISqlExecutorService;
import com.liupeng.sqlexec.util.SqlUtil;
import com.liupeng.sqlexec.vo.SqlRequestVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * SQL执行器
 *
 * @author fengdao.lp
 * @date 2018/6/7
 */
@Controller
public class SqlController {
    private static final Logger LOGGER = LoggerFactory.getLogger(SqlController.class);

    @Resource(name = "sqlExecutorServiceImplProxy")
    private ISqlExecutorService sqlExecutorService;

    @RequestMapping(value = "sql/exe", method = RequestMethod.POST)
    @ResponseBody
    public ResponseVo execute(HttpServletRequest request, SqlRequestVo sql) {
        ResponseVo vo = new ResponseVo();
        LOGGER.info("开始执行,sql: {}", sql);
        if (null == sql) {
            vo.setSuccess(false);
            vo.setErrorCode(1);
            vo.setMsg("sql执行异常,原因:参数为空。");
            return vo;
        }
        // SQL语句校验
        vo = SqlUtil.checkSql(sql.getSqlStatement().trim());
        if (!vo.isSuccess()) {
            return vo;
        }
        try {
            vo = sqlExecutorService.exeSql(request, sql);
        } catch (Exception e) {
            LOGGER.error("sql执行异常", e);
            vo.setSuccess(false);
            vo.setErrorCode(1);
            vo.setMsg("sql执行异常,原因:" + e.getMessage());
        }
        return vo;
    }
}
