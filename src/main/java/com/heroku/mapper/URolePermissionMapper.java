package com.heroku.mapper;

import com.heroku.entity.URolePermission;
import com.heroku.entity.URolePermissionExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface URolePermissionMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.u_role_permission
     *
     * @mbggenerated Fri Nov 03 13:57:53 CST 2017
     */
    int countByExample(URolePermissionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.u_role_permission
     *
     * @mbggenerated Fri Nov 03 13:57:53 CST 2017
     */
    int deleteByExample(URolePermissionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.u_role_permission
     *
     * @mbggenerated Fri Nov 03 13:57:53 CST 2017
     */
    int insert(URolePermission record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.u_role_permission
     *
     * @mbggenerated Fri Nov 03 13:57:53 CST 2017
     */
    int insertSelective(URolePermission record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.u_role_permission
     *
     * @mbggenerated Fri Nov 03 13:57:53 CST 2017
     */
    List<URolePermission> selectByExample(URolePermissionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.u_role_permission
     *
     * @mbggenerated Fri Nov 03 13:57:53 CST 2017
     */
    int updateByExampleSelective(@Param("record") URolePermission record, @Param("example") URolePermissionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.u_role_permission
     *
     * @mbggenerated Fri Nov 03 13:57:53 CST 2017
     */
    int updateByExample(@Param("record") URolePermission record, @Param("example") URolePermissionExample example);
}