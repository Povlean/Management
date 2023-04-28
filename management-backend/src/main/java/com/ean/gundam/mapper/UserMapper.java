package com.ean.gundam.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ean.gundam.model.domain.User;
import com.ean.gundam.model.request.LoginRequest;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.repository.query.Param;

import java.util.List;


/**
* @author Asphyxia
* @description 针对表【tb_user】的数据库操作Mapper
* @createDate 2023-04-22 11:17:06
* @Entity generator.domain.User
*/
@Mapper
public interface UserMapper extends BaseMapper<User> {
    User userLogin(@Param("username") String username,@Param("password") String password);

    List<User> getAllUser();
}




