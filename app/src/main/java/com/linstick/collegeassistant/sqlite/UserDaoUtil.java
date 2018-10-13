package com.linstick.collegeassistant.sqlite;

import com.linstick.collegeassistant.litepal.User;

import org.litepal.crud.DataSupport;

public class UserDaoUtil {

    /**
     * 查看用户基本信息
     *
     * @param userId 用户id
     * @return 返回用户基本信息
     */
    public static User findUser(int userId) {
        User user = DataSupport.select("iconUrl", "realName", "nickName", "age", "isMale", "university", "department", "major", "klazz", "description")
                .where("id = ?", userId + "").findFirst(User.class);
        return user;
    }

    /**
     * 登录验证
     *
     * @param account  用户名(可为手机号或邮箱)
     * @param password 密码
     * @return 登录成功返回用户信息，登录失败返回null
     */
    public static User loginVerify(String account, String password) {
        User user = DataSupport.where("cellNumber = ? and password = ?", account, password).findFirst(User.class);
        if (user == null) {
            user = DataSupport.where("email = ? and password = ?", account, password).findFirst(User.class);
        }
        if (user != null) {
            // 不返回密码
            user.setPassword(null);
        }
        return user;
    }

    /**
     * 重置密码
     *
     * @param userId           用户id
     * @param originalPassword 原密码
     * @param newPassword      新密码
     * @return 重置结果
     */
    public static boolean resetPassword(int userId, String originalPassword, String newPassword) {
        User user = DataSupport.where("id = ? and password = ?", userId + "", originalPassword).findFirst(User.class);
        if (user == null) {
            return false;
        }
        user.setPassword(newPassword);
        user.save();
        return true;
    }

    public static boolean modifyUserInfo(User user) {
        if (user == null) {
            return false;
        }
        user.update(user.getId());
        return true;
    }

    /**
     * 用户注册
     *
     * @param account  手机号
     * @param nickname 昵称
     * @param password 密码
     * @return
     */
    public static User registerVerify(String account, String nickname, String password) {
        User user = DataSupport.where("cellNumber = ?", account).findFirst(User.class);
        if (user != null) {
            return null;
        }
        user = new User();
        user.setCellNumber(account);
        user.setNickName(nickname);
        user.setPassword(password);
        user.save();
        return user;
    }

}
