package com.kfm.service;

import com.kfm.constant.Constant;
import com.kfm.mapper.UserMapper;
import com.kfm.model.domain.User;
import com.kfm.utils.MD5Utils;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

public class UserService {
    private UserMapper mapper = new UserMapper();

    /**
     * 用户注册方法
     *
     * @param username 用户名
     * @param password 用户密码
     * @param nickName 昵称
     * @param sex      性别
     * @param avatar   头像
     * @param phone    电话
     * @param email    邮箱地址
     * @return 注册是否成功
     */
    public int doRegister(String username, String password, String nickName, String sex, String avatar, String email, String phone) {
        // 对所有非空用户信息判空
        if (username.isEmpty() || nickName.isEmpty() || password.isEmpty() || sex.isEmpty() || email.isEmpty()
                || username.isBlank() || nickName.isBlank() | password.isBlank() || sex.isBlank() || email.isBlank()) {
            return Constant.RETURN_VALUE_ONE;
        }

        try {
            String usernameRecord = mapper.doCheckRegister(username);
            if (usernameRecord != null) {
                return Constant.RETURN_VALUE_ONE;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


        // 密码MD5加密
        String passwordPlus = MD5Utils.getMD5(password);
        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordPlus);
        user.setNickName(nickName);
        user.setSex(sex);
        user.setAvatar(avatar);
        user.setEmail(email);
        user.setPhoneNumber(phone);
        // 打印信息
        System.out.println(user);
        int i = Constant.RETURN_VALUE_ONE;
        try {
            i = mapper.doRegister(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return i;
    }

    /**
     * 用户登录的方法
     *
     * @param username 用户名
     * @param password 密码
     * @return 脱敏后的用户对象
     */
    public User doLogin(String username, String password) {
        if (username == null || username.isEmpty() || username == null ||password.isEmpty() ||
                username.isBlank() || password.isBlank()) {
            return null;
        }
        User user = new User();
        // 加密MD5
        String passwordPlus = MD5Utils.getMD5(password);
        try {
            ResultSet rs = mapper.doLogin(username, passwordPlus);
            // 用户信息脱敏
            while (rs.next()) {
                user.setId(rs.getLong("id"));
                user.setUsername(rs.getString("user_name"));
                user.setNickName(rs.getString("nick_name"));
                user.setAvatar(rs.getString("avatar"));
                user.setEmail(rs.getString("email"));
                user.setPhoneNumber(rs.getString("phonenumber"));
                return user;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }

    public User queryUser(Long id) {
        try {
            return mapper.queryUser(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String updateUser(User user) {
        if (user.getNickName() == null || user.getNickName().isEmpty()) {
            return "昵称不能为空！";
        } else if (user.getSex() == null || user.getSex().isEmpty()) {
            return "性别不能为空！";
        } else if (user.getEmail() == null || user.getEmail().isEmpty()) {
            return "邮箱不能为空！";
        } else if (user.getPhoneNumber() == null || user.getPhoneNumber().isEmpty()) {
            return "电话不能为空！";
        }
        Map<String, String> userMap = new HashMap<>();
        userMap.put("nickName", user.getNickName());
        userMap.put("sex",user.getSex().equals("男") ?"0":"1");
        userMap.put("email", user.getEmail());
        userMap.put("phone", user.getPhoneNumber());
        // 轮流修改每个字段
        userMap.forEach((k, v) -> {
            try {
                mapper.updateUser(user.getId(), k, v);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        return "success";
    }

    /**
     * 更新密码的方法
     *
     * @param id         用户id
     * @param password   用户密码
     * @param rePassword 用户再次输入密码
     * @return 提示信息
     */
    public String updatePassword(Long id, String password, String rePassword) {
        if (password == null || rePassword == null) {
            return "您的输入为空！";
        } else if (!password.equals(rePassword)) {
            return "两次密码输入不一致！";
        } else if (password.length() < 6) {
            return "密码不少于六位！";
        }
        String passwordPlus = MD5Utils.getMD5(password);
        try {
            mapper.updatePassword(id, passwordPlus);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "success";
    }

    public String updateAvatar(Long id, String avatarUrl) {
        if (avatarUrl == null) {
            return "头像路径为空！";
        }
        try {
            mapper.updateAvatar(id, avatarUrl);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "success";
    }
}
