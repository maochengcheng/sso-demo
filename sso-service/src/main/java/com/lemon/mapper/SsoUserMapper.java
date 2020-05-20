package com.lemon.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.lemon.entity.SsoUser;
import com.lemon.entity.SsoUserExample;

public interface SsoUserMapper {
    int countByExample(SsoUserExample example);

    int deleteByExample(SsoUserExample example);

    int deleteByPrimaryKey(Long id);

    int insert(SsoUser record);

    int insertSelective(SsoUser record);

    List<SsoUser> selectByExample(SsoUserExample example);

    SsoUser selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") SsoUser record, @Param("example") SsoUserExample example);

    int updateByExample(@Param("record") SsoUser record, @Param("example") SsoUserExample example);

    int updateByPrimaryKeySelective(SsoUser record);

    int updateByPrimaryKey(SsoUser record);
}