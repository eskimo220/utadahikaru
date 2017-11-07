package com.heroku.entity;

import java.util.ArrayList;
import java.util.List;

public class ASelectExample {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table public.a_select
     *
     * @mbggenerated Fri Nov 03 13:57:53 CST 2017
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table public.a_select
     *
     * @mbggenerated Fri Nov 03 13:57:53 CST 2017
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table public.a_select
     *
     * @mbggenerated Fri Nov 03 13:57:53 CST 2017
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.a_select
     *
     * @mbggenerated Fri Nov 03 13:57:53 CST 2017
     */
    public ASelectExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.a_select
     *
     * @mbggenerated Fri Nov 03 13:57:53 CST 2017
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.a_select
     *
     * @mbggenerated Fri Nov 03 13:57:53 CST 2017
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.a_select
     *
     * @mbggenerated Fri Nov 03 13:57:53 CST 2017
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.a_select
     *
     * @mbggenerated Fri Nov 03 13:57:53 CST 2017
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.a_select
     *
     * @mbggenerated Fri Nov 03 13:57:53 CST 2017
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.a_select
     *
     * @mbggenerated Fri Nov 03 13:57:53 CST 2017
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.a_select
     *
     * @mbggenerated Fri Nov 03 13:57:53 CST 2017
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.a_select
     *
     * @mbggenerated Fri Nov 03 13:57:53 CST 2017
     */
    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.a_select
     *
     * @mbggenerated Fri Nov 03 13:57:53 CST 2017
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.a_select
     *
     * @mbggenerated Fri Nov 03 13:57:53 CST 2017
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table public.a_select
     *
     * @mbggenerated Fri Nov 03 13:57:53 CST 2017
     */
    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andASelectIdIsNull() {
            addCriterion("a_select_id is null");
            return (Criteria) this;
        }

        public Criteria andASelectIdIsNotNull() {
            addCriterion("a_select_id is not null");
            return (Criteria) this;
        }

        public Criteria andASelectIdEqualTo(Integer value) {
            addCriterion("a_select_id =", value, "aSelectId");
            return (Criteria) this;
        }

        public Criteria andASelectIdNotEqualTo(Integer value) {
            addCriterion("a_select_id <>", value, "aSelectId");
            return (Criteria) this;
        }

        public Criteria andASelectIdGreaterThan(Integer value) {
            addCriterion("a_select_id >", value, "aSelectId");
            return (Criteria) this;
        }

        public Criteria andASelectIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("a_select_id >=", value, "aSelectId");
            return (Criteria) this;
        }

        public Criteria andASelectIdLessThan(Integer value) {
            addCriterion("a_select_id <", value, "aSelectId");
            return (Criteria) this;
        }

        public Criteria andASelectIdLessThanOrEqualTo(Integer value) {
            addCriterion("a_select_id <=", value, "aSelectId");
            return (Criteria) this;
        }

        public Criteria andASelectIdIn(List<Integer> values) {
            addCriterion("a_select_id in", values, "aSelectId");
            return (Criteria) this;
        }

        public Criteria andASelectIdNotIn(List<Integer> values) {
            addCriterion("a_select_id not in", values, "aSelectId");
            return (Criteria) this;
        }

        public Criteria andASelectIdBetween(Integer value1, Integer value2) {
            addCriterion("a_select_id between", value1, value2, "aSelectId");
            return (Criteria) this;
        }

        public Criteria andASelectIdNotBetween(Integer value1, Integer value2) {
            addCriterion("a_select_id not between", value1, value2, "aSelectId");
            return (Criteria) this;
        }

        public Criteria andASelectNameIsNull() {
            addCriterion("a_select_name is null");
            return (Criteria) this;
        }

        public Criteria andASelectNameIsNotNull() {
            addCriterion("a_select_name is not null");
            return (Criteria) this;
        }

        public Criteria andASelectNameEqualTo(String value) {
            addCriterion("a_select_name =", value, "aSelectName");
            return (Criteria) this;
        }

        public Criteria andASelectNameNotEqualTo(String value) {
            addCriterion("a_select_name <>", value, "aSelectName");
            return (Criteria) this;
        }

        public Criteria andASelectNameGreaterThan(String value) {
            addCriterion("a_select_name >", value, "aSelectName");
            return (Criteria) this;
        }

        public Criteria andASelectNameGreaterThanOrEqualTo(String value) {
            addCriterion("a_select_name >=", value, "aSelectName");
            return (Criteria) this;
        }

        public Criteria andASelectNameLessThan(String value) {
            addCriterion("a_select_name <", value, "aSelectName");
            return (Criteria) this;
        }

        public Criteria andASelectNameLessThanOrEqualTo(String value) {
            addCriterion("a_select_name <=", value, "aSelectName");
            return (Criteria) this;
        }

        public Criteria andASelectNameLike(String value) {
            addCriterion("a_select_name like", value, "aSelectName");
            return (Criteria) this;
        }

        public Criteria andASelectNameNotLike(String value) {
            addCriterion("a_select_name not like", value, "aSelectName");
            return (Criteria) this;
        }

        public Criteria andASelectNameIn(List<String> values) {
            addCriterion("a_select_name in", values, "aSelectName");
            return (Criteria) this;
        }

        public Criteria andASelectNameNotIn(List<String> values) {
            addCriterion("a_select_name not in", values, "aSelectName");
            return (Criteria) this;
        }

        public Criteria andASelectNameBetween(String value1, String value2) {
            addCriterion("a_select_name between", value1, value2, "aSelectName");
            return (Criteria) this;
        }

        public Criteria andASelectNameNotBetween(String value1, String value2) {
            addCriterion("a_select_name not between", value1, value2, "aSelectName");
            return (Criteria) this;
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table public.a_select
     *
     * @mbggenerated do_not_delete_during_merge Fri Nov 03 13:57:53 CST 2017
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table public.a_select
     *
     * @mbggenerated Fri Nov 03 13:57:53 CST 2017
     */
    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}