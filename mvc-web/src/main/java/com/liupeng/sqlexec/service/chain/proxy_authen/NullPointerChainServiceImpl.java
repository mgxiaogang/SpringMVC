package com.liupeng.sqlexec.service.chain.proxy_authen;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.liupeng.annotationdriven.response.ResponseVo;
import com.liupeng.sqlexec.service.chain.AbsSqlExecutorChainServiceImpl;
import com.liupeng.sqlexec.service.chain.ISqlExecutorChain;
import com.liupeng.sqlexec.util.SqlUtil;
import com.liupeng.sqlexec.vo.SqlRequestVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * 空指针校验
 *
 * @author 刘鹏
 */
@Service
public class NullPointerChainServiceImpl extends AbsSqlExecutorChainServiceImpl {

    private static final Logger logger = LoggerFactory.getLogger(NullPointerChainServiceImpl.class);

    @Resource(name = "userAclChainServiceImpl")
    private ISqlExecutorChain nextsqlChain;

    @Override
    protected ResponseVo realDoSqlHandle(HttpServletRequest httpRequest, SqlRequestVo sqlRequest) {
        ResponseVo vo = new ResponseVo();
        StringBuilder sb = new StringBuilder();
        if (null == httpRequest) {
            sb.append("参数httpRequest为空");
            SqlUtil.setResposeVo(vo, false, 1, sb.toString());
            logger.error(sb.toString());
        }
        if (vo.isSuccess() && null == sqlRequest) {
            sb.append("参数sqlRequest为空");
            SqlUtil.setResposeVo(vo, false, 1, sb.toString());
            logger.error(sb.toString());
        }
        return vo;
    }

    @Override
    public ISqlExecutorChain getNextSqlChain() {
        return nextsqlChain;
    }
}
