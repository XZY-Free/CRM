package com.XZY_SUNSHINE.crm.settings.mapper;

import com.XZY_SUNSHINE.crm.settings.pojo.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;
@Mapper
public interface UserMapper {
    User selectUserByLoginActAndPwd(Map map);
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_user
     *
     * @mbggenerated Wed Apr 12 18:39:07 CST 2023
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_user
     *
     * @mbggenerated Wed Apr 12 18:39:07 CST 2023
     */
    int insert(User record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_user
     *
     * @mbggenerated Wed Apr 12 18:39:07 CST 2023
     */
    int insertSelective(User record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_user
     *
     * @mbggenerated Wed Apr 12 18:39:07 CST 2023
     */
    User selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_user
     *
     * @mbggenerated Wed Apr 12 18:39:07 CST 2023
     */
    int updateByPrimaryKeySelective(User record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_user
     *
     * @mbggenerated Wed Apr 12 18:39:07 CST 2023
     */
    int updateByPrimaryKey(User record);
}