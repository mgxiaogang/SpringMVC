package com.liupeng.serialize;

import java.util.List;

/**
 * @author fengdao.lp
 * @date 2018/8/27
 */
public class User {
    private String userName;
    private String passWord;
    private String userInfo;
    private List<User> friends;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(String userInfo) {
        this.userInfo = userInfo;
    }

    public List<User> getFriends() {
        return friends;
    }

    public void setFriends(List<User> friends) {
        this.friends = friends;
    }
}
