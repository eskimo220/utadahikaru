package com.heroku.mapper;

import com.heroku.entity.ASelect;
import com.heroku.entity.ASelectExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ASelectMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.a_select
     *
     * @mbggenerated Fri Nov 03 13:57:53 CST 2017
     */
    int countByExample(ASelectExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.a_select
     *
     * @mbggenerated Fri Nov 03 13:57:53 CST 2017
     */
    int deleteByExample(ASelectExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.a_select
     *
     * @mbggenerated Fri Nov 03 13:57:53 CST 2017
     */
    int deleteByPrimaryKey(Integer aSelectId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.a_select
     *
     * @mbggenerated Fri Nov 03 13:57:53 CST 2017
     */
    int insert(ASelect record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.a_select
     *
     * @mbggenerated Fri Nov 03 13:57:53 CST 2017
     */
    int insertSelective(ASelect record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.a_select
     *
     * @mbggenerated Fri Nov 03 13:57:53 CST 2017
     */
    List<ASelect> selectByExample(ASelectExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.a_select
     *
     * @mbggenerated Fri Nov 03 13:57:53 CST 2017
     */
    ASelect selectByPrimaryKey(Integer aSelectId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.a_select
     *
     * @mbggenerated Fri Nov 03 13:57:53 CST 2017
     */
    int updateByExampleSelective(@Param("record") ASelect record, @Param("example") ASelectExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.a_select
     *
     * @mbggenerated Fri Nov 03 13:57:53 CST 2017
     */
    int updateByExample(@Param("record") ASelect record, @Param("example") ASelectExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.a_select
     *
     * @mbggenerated Fri Nov 03 13:57:53 CST 2017
     */
    int updateByPrimaryKeySelective(ASelect record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.a_select
     *
     * @mbggenerated Fri Nov 03 13:57:53 CST 2017
     */
    int updateByPrimaryKey(ASelect record);
}