package com.example.demo;


import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;


@Mapper
@Repository
@DbAnnotation(DataSourceConstants.DS_KEY_SLAVE)
public interface UserMapper1 {
    Integer getUserId();
}
