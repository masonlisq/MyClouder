package com.kfm.mapper;

import com.kfm.model.domain.User;
import com.kfm.utils.DataSourceUtils;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * Mapper
 */
public class UserMapper {
    /**
     * 用户注册方法
     * @param user user对象
     */
    public int doRegister(User user) throws Exception {
        Connection conn = DataSourceUtils.getConnection();
        PreparedStatement ps =
                conn.prepareStatement("INSERT INTO `kfm_clouder`.`sys_user`" +
                        "(`user_name`, `nick_name`, `password`, `sex`,`avatar`, `phonenumber`, `email`)" +
                        " VALUES (?, ?, ?, ?, ?, ?,?)");
        ps.setString(1,user.getUsername());
        ps.setString(2,user.getNickName());
        ps.setString(3,user.getPassword());
        ps.setString(4,user.getSex());
        ps.setString(5,user.getAvatar());
        ps.setString(6,user.getPhoneNumber());
        ps.setString(7,user.getEmail());
        return ps.executeUpdate();
    }
    public ResultSet doLogin(String username, String password) throws Exception {
        Connection conn = DataSourceUtils.getConnection();
        PreparedStatement ps = conn.prepareStatement("select * from `kfm_clouder`.`sys_user` where user_name = ? and password = ?");
        ps.setString(1,username);
        ps.setString(2,password);
        return ps.executeQuery();
    }
    public String doCheckRegister(String username) throws Exception {
        Connection conn = DataSourceUtils.getConnection();
        PreparedStatement ps = conn.prepareStatement("select * from `kfm_clouder`.`sys_user` where user_name = ?");
        ps.setString(1,username);
        ResultSet resultSet = ps.executeQuery();
        String usernameRecord = null;
        while (resultSet.next()){
             usernameRecord = resultSet.getString("user_name");
        }
        return usernameRecord;
    }
    public User queryUser(Long id) throws Exception {
        Connection conn = DataSourceUtils.getConnection();
        PreparedStatement ps = conn.prepareStatement("select * from `kfm_clouder`.`sys_user` where id = ?");
        ps.setLong(1,id);
        ResultSet rs = ps.executeQuery();
        User user = new User();
        while (rs.next()){
            user.setUsername(rs.getString("user_name"));
            user.setNickName(rs.getString("nick_name"));
            user.setSex(rs.getString("sex").equals("0")?"男":"女");
            user.setEmail(rs.getString("email"));
            user.setPhoneNumber(rs.getString("phonenumber"));
            user.setAvatar(rs.getString("avatar"));
        }
        return user;
    }
    public int updateUser(Long id, String field, String value) throws Exception {
        Connection conn = DataSourceUtils.getConnection();
        PreparedStatement ps = null;
        switch (field){
            case "nickName" -> ps =  conn.prepareStatement("update `kfm_clouder`.`sys_user` set nick_name = ? where id = ?");
            case "sex" -> ps =conn.prepareStatement("update `kfm_clouder`.`sys_user` set sex = ? where id = ?");
            case "email" -> ps =  conn.prepareStatement("update `kfm_clouder`.`sys_user` set email = ? where id = ?");
            case "phone" -> ps =  conn.prepareStatement("update `kfm_clouder`.`sys_user` set phonenumber = ? where id = ?");
        }
        assert ps != null;
        ps.setString(1,value);
        ps.setLong(2,id);
        return ps.executeUpdate();
    }
    public int updatePassword (Long id, String password)throws Exception{
        Connection conn = DataSourceUtils.getConnection();
        PreparedStatement ps = conn.prepareStatement("update `kfm_clouder`.sys_user set password = ? where id = ?");
        ps.setString(1,password);
        ps.setLong(2,id);
        return ps.executeUpdate();
    }
    public int updateAvatar(Long id,String avatarUrl)throws Exception{
        Connection conn = DataSourceUtils.getConnection();
        PreparedStatement ps = conn.prepareStatement("update `kfm_clouder`.sys_user set avatar = ? where id = ?");
        ps.setString(1,avatarUrl);
        ps.setLong(2,id);
        return ps.executeUpdate();
    }
}
