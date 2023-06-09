package com.XZY_SUNSHINE.crm.workbench.Contacts.mapper;

import com.XZY_SUNSHINE.crm.workbench.Contacts.pojo.Contacts;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ContactsMapper {
    List<Contacts> selectContactsByName(String name);
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_contacts
     *
     * @mbggenerated Sat Apr 22 12:51:27 CST 2023
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_contacts
     *
     * @mbggenerated Sat Apr 22 12:51:27 CST 2023
     */
    int insert(Contacts record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_contacts
     *
     * @mbggenerated Sat Apr 22 12:51:27 CST 2023
     */
    int insertSelective(Contacts record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_contacts
     *
     * @mbggenerated Sat Apr 22 12:51:27 CST 2023
     */
    Contacts selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_contacts
     *
     * @mbggenerated Sat Apr 22 12:51:27 CST 2023
     */
    int updateByPrimaryKeySelective(Contacts record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_contacts
     *
     * @mbggenerated Sat Apr 22 12:51:27 CST 2023
     */
    int updateByPrimaryKey(Contacts record);
}