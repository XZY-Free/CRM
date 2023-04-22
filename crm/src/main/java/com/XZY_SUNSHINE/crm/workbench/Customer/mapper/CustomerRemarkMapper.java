package com.XZY_SUNSHINE.crm.workbench.Customer.mapper;

import com.XZY_SUNSHINE.crm.workbench.Customer.pojo.CustomerRemark;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CustomerRemarkMapper {
    int insertByList(List<CustomerRemark> customerRemarkList);
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_customer_remark
     *
     * @mbggenerated Sat Apr 22 12:50:16 CST 2023
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_customer_remark
     *
     * @mbggenerated Sat Apr 22 12:50:16 CST 2023
     */
    int insert(CustomerRemark record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_customer_remark
     *
     * @mbggenerated Sat Apr 22 12:50:16 CST 2023
     */
    int insertSelective(CustomerRemark record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_customer_remark
     *
     * @mbggenerated Sat Apr 22 12:50:16 CST 2023
     */
    CustomerRemark selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_customer_remark
     *
     * @mbggenerated Sat Apr 22 12:50:16 CST 2023
     */
    int updateByPrimaryKeySelective(CustomerRemark record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_customer_remark
     *
     * @mbggenerated Sat Apr 22 12:50:16 CST 2023
     */
    int updateByPrimaryKey(CustomerRemark record);
}