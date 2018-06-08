package com.liupeng.sqlexec.service.chain.real_chain;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.liupeng.annotationdriven.response.ResponseVo;
import com.liupeng.sqlexec.service.chain.AbsSqlExecutorChainServiceImpl;
import com.liupeng.sqlexec.service.chain.ISqlExecutorChain;
import com.liupeng.sqlexec.service.datasource.IDataSourceFactory;
import com.liupeng.sqlexec.service.datasource.IDataSourceSwitch;
import com.liupeng.sqlexec.util.SqlUtil;
import com.liupeng.sqlexec.vo.SqlRequestVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * 数据源校验和切换
 *
 * @author 刘鹏
 */
@Service
public class DataSourceCheckChainServiceImpl extends AbsSqlExecutorChainServiceImpl {

    private static final Logger logger = LoggerFactory.getLogger(DataSourceCheckChainServiceImpl.class);

    @Resource
    private IDataSourceFactory dataSourceFactory;

    @Resource(name = "sqlStatementExecutorChainServiceImpl")
    private ISqlExecutorChain nextsqlChain;

    /**
     * 执行数据源校验和切换
     */
    @Override
    protected ResponseVo realDoSqlHandle(HttpServletRequest httpRequest, SqlRequestVo sqlRequest) {
        ResponseVo vo = new ResponseVo();
        StringBuilder sb = new StringBuilder();
        int uid = 0;
        String dbName = "";
        if (null != sqlRequest) {
            dbName = sqlRequest.getDbName();
            uid = sqlRequest.getUid();
        }
        IDataSourceSwitch dataSourceSwith = dataSourceFactory.getDateSourceSwitch(dbName);
        if (null == dataSourceSwith) {
            sb.append("不支持该数据库 :").append(sqlRequest.getDbName());
            SqlUtil.setResposeVo(vo, false, 1, sb.toString());
            logger.error(sb.toString());
            return vo;
        }
        //采购和库存增加负责人校验
        if (uid <= 0) {
            sb.append("操作人UID必须大于0:").append(sqlRequest.getDbName());
            SqlUtil.setResposeVo(vo, false, 1, sb.toString());
            logger.error(sb.toString());
            return vo;
        }

        boolean checkManagerResult = dataSourceSwith.checkManagerValid(uid);
        if (!checkManagerResult) {
            sb.append("您【").append(uid).append("】没有权限操作DB【").append(sqlRequest.getDbName()).append("】请和系统负责人联系。");
            SqlUtil.setResposeVo(vo, false, 1, sb.toString());
            logger.error(sb.toString());
            return vo;
        }

        if (vo.isSuccess()) {
            dataSourceSwith.switchDataSource();
        }
        return vo;
    }

    /**
     * Description: <br>
     *
     * @return <br>
     * @author haoyabin<br>
     * @taskId <br>
     */
    @Override
    public ISqlExecutorChain getNextSqlChain() {
        return nextsqlChain;
    }
}
