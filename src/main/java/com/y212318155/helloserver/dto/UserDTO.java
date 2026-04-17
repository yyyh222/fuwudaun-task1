package com.y212318155.helloserver.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("sys_user")
public class UserDTO  {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String username;
    private Integer password; // 必须是Integer，对应int
}