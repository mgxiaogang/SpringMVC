package com.liupeng.sqlexec.vo;

/**
 * 用户信息
 *
 * @author 刘鹏
 */
public class UserVo {
    private int uid;

    private String ip;

    private String pwd;

    /**
     * get uid
     *
     * @return Returns the uid.<br>
     */
    public int getUid() {
        return uid;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((ip == null) ? 0 : ip.hashCode());
        result = prime * result + uid;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) { return true; }
        if (obj == null) { return false; }
        if (getClass() != obj.getClass()) { return false; }
        UserVo other = (UserVo)obj;
        if (uid != other.uid) { return false; }
        return true;
    }

    /**
     * set uid
     *
     * @param uid The uid to set. <br>
     */
    public void setUid(int uid) {
        this.uid = uid;
    }

    /**
     * get ip
     *
     * @return Returns the ip.<br>
     */
    public String getIp() {
        return ip;
    }

    /**
     * set ip
     *
     * @param ip The ip to set. <br>
     */
    public void setIp(String ip) {
        this.ip = ip;
    }

    /**
     * get pwd
     *
     * @return Returns the pwd.<br>
     */
    public String getPwd() {
        return pwd;
    }

    /**
     * set pwd
     *
     * @param pwd The pwd to set. <br>
     */
    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
}
