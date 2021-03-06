package com.heroku.mapper;

import com.heroku.entity.UUserRole;
import com.heroku.entity.UUserRoleExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UUserRoleMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.u_user_role
     *
     * @mbggenerated Sat Aug 26 12:41:35 CST 2017
     */
    int countByExample(UUserRoleExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.u_user_role
     *
     * @mbggenerated Sat Aug 26 12:41:35 CST 2017
     */
    int deleteByExample(UUserRoleExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.u_user_role
     *
     * @mbggenerated Sat Aug 26 12:41:35 CST 2017
     */
    int insert(UUserRole record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.u_user_role
     *
     * @mbggenerated Sat Aug 26 12:41:35 CST 2017
     */
    int insertSelective(UUserRole record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.u_user_role
     *
     * @mbggenerated Sat Aug 26 12:41:35 CST 2017
     */
    List<UUserRole> selectByExample(UUserRoleExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.u_user_role
     *
     * @mbggenerated Sat Aug 26 12:41:35 CST 2017
     */
    int updateByExampleSelective(@Param("record") UUserRole record, @Param("example") UUserRoleExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.u_user_role
     *
     * @mbggenerated Sat Aug 26 12:41:35 CST 2017
     */
    int updateByExample(@Param("record") UUserRole record, @Param("example") UUserRoleExample example);
}