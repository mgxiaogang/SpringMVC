package com.liupeng.sqlexec.service.chain.proxy_authen;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import com.liupeng.annotationdriven.response.ResponseVo;
import com.liupeng.sqlexec.service.chain.AbsSqlExecutorChainServiceImpl;
import com.liupeng.sqlexec.service.chain.ISqlExecutorChain;
import com.liupeng.sqlexec.util.MD5Util;
import com.liupeng.sqlexec.util.SqlUtil;
import com.liupeng.sqlexec.vo.SqlRequestVo;
import com.liupeng.sqlexec.vo.UserVo;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * 用户身份验证(密码)
 *
 * @author 刘鹏
 */
@Service
public class UserAclChainServiceImpl extends AbsSqlExecutorChainServiceImpl {

    private static final Logger logger = LoggerFactory.getLogger(UserAclChainServiceImpl.class);

    private Set<Integer> deniedAcl = new HashSet<>();

    /**
     * 定义为null，终止执行链
     */
    private ISqlExecutorChain nextsqlChain = null;

    /**
     * 本节点处理
     */
    @Override
    protected ResponseVo realDoSqlHandle(HttpServletRequest httpRequest, SqlRequestVo sqlRequest) {
        ResponseVo vo = new ResponseVo();
        UserVo user = SqlUtil.buildUserVo(httpRequest, sqlRequest);
        StringBuilder msg = new StringBuilder();
        if (null == user) {
            msg.append("无效用户.");
            SqlUtil.setResposeVo(vo, false, 1, "无效用户.");
            return vo;
        }
        if (deniedAcl.contains(user.getUid())) {
            // 拒绝访问的用户
            msg.append("用户uid: ").append(user.getUid()).append(" 是账号已被锁定的用户.");
            logger.error(msg.toString());
            SqlUtil.setResposeVo(vo, false, 1, "你的账号已被锁定");
        } else {
            // 普通用户正常认证
            msg.append("用户uid: ").append(user.getUid()).append(" 将进入用户认证模式.");
            logger.info(msg.toString());
            return userAcl(user);
        }
        return vo;
    }

    /**
     * 用户认证
     */
    private ResponseVo userAcl(UserVo user) {
        ResponseVo responseVo = new ResponseVo();
        // 从缓存中获取password
        String key = "sql-exe-user.getId()";
        String pwd = "123";
        String memcachedPasswd = "123";
        if (StringUtils.isBlank(memcachedPasswd) && StringUtils.isBlank(user.getPwd())) {
            unautherizedHandle(user, key, responseVo);
        } else {
            autherizedHandle(user, pwd, memcachedPasswd, responseVo);
        }
        return responseVo;
    }

    private ResponseVo autherizedHandle(UserVo user, String pwd, String memcachedPasswd, ResponseVo responseVo) {
        boolean authenResult = false;
        try {
            authenResult = MD5Util.validPasswd(pwd, memcachedPasswd);
            if (authenResult) {
                responseVo.setMsg("认证成功.");
            } else {
                responseVo.setMsg("尝试认证,但是密码错误,请确认是本人在操作,否则立即和系统管理员联系.");
                // 发送RTX
            }
        } catch (Exception e) {
            logger.error("异常", e);
        }
        return responseVo;
    }

    private void unautherizedHandle(UserVo user, String key, ResponseVo responseVo) {
        String newPwd = this.generatePassword(user);
        if (StringUtils.isNoneBlank(newPwd)) {
            // 设置到缓存中
            boolean generateResult = true;
            if (generateResult) {
                responseVo.setMsg("验证码已发送您RTX，该验证码将于xxx后失效");
                // 发送RTX
            } else {
                responseVo.setMsg("验证码生成失败");
            }
        } else {
            responseVo.setMsg("验证码生成失败");
        }
    }

    private String generatePassword(UserVo user) {
        String passed = Integer.toString(user.hashCode());
        String encryptedPwd = "";
        try {
            encryptedPwd = MD5Util.getEncryptedPwd(passed);
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            logger.error("异常:", e);
        }
        return encryptedPwd;
    }

    /**
     * 获取下一个执行链
     */
    @Override
    public ISqlExecutorChain getNextSqlChain() {
        return nextsqlChain;
    }

    public void setDeniedAcl(Set<Integer> deniedAcl) {
        this.deniedAcl = deniedAcl;
    }
}
