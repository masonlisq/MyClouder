package com.kfm.model.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private Long id;
    private String username;
    private String nickName;
    private String password;
    private int status;
    private String email;
    private String phoneNumber;
    private String sex;
    private String avatar;
    private String userType;
    private String createBy;
    private Date createTime;
    private String updateBy;
    private Date updateTime;
    private int delFlag;
}
