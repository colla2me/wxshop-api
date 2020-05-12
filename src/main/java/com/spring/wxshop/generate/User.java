package com.spring.wxshop.generate;

import java.util.Date;

public class User {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column USER.id
     *
     * @mbg.generated Tue May 12 19:27:17 CST 2020
     */
    private Long id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column USER.name
     *
     * @mbg.generated Tue May 12 19:27:17 CST 2020
     */
    private String name;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column USER.tel
     *
     * @mbg.generated Tue May 12 19:27:17 CST 2020
     */
    private String tel;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column USER.avatar
     *
     * @mbg.generated Tue May 12 19:27:17 CST 2020
     */
    private String avatar;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column USER.created_at
     *
     * @mbg.generated Tue May 12 19:27:17 CST 2020
     */
    private Date createdAt;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column USER.updated_at
     *
     * @mbg.generated Tue May 12 19:27:17 CST 2020
     */
    private Date updatedAt;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column USER.id
     *
     * @return the value of USER.id
     *
     * @mbg.generated Tue May 12 19:27:17 CST 2020
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column USER.id
     *
     * @param id the value for USER.id
     *
     * @mbg.generated Tue May 12 19:27:17 CST 2020
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column USER.name
     *
     * @return the value of USER.name
     *
     * @mbg.generated Tue May 12 19:27:17 CST 2020
     */
    public String getName() {
        return name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column USER.name
     *
     * @param name the value for USER.name
     *
     * @mbg.generated Tue May 12 19:27:17 CST 2020
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column USER.tel
     *
     * @return the value of USER.tel
     *
     * @mbg.generated Tue May 12 19:27:17 CST 2020
     */
    public String getTel() {
        return tel;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column USER.tel
     *
     * @param tel the value for USER.tel
     *
     * @mbg.generated Tue May 12 19:27:17 CST 2020
     */
    public void setTel(String tel) {
        this.tel = tel == null ? null : tel.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column USER.avatar
     *
     * @return the value of USER.avatar
     *
     * @mbg.generated Tue May 12 19:27:17 CST 2020
     */
    public String getAvatar() {
        return avatar;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column USER.avatar
     *
     * @param avatar the value for USER.avatar
     *
     * @mbg.generated Tue May 12 19:27:17 CST 2020
     */
    public void setAvatar(String avatar) {
        this.avatar = avatar == null ? null : avatar.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column USER.created_at
     *
     * @return the value of USER.created_at
     *
     * @mbg.generated Tue May 12 19:27:17 CST 2020
     */
    public Date getCreatedAt() {
        return createdAt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column USER.created_at
     *
     * @param createdAt the value for USER.created_at
     *
     * @mbg.generated Tue May 12 19:27:17 CST 2020
     */
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column USER.updated_at
     *
     * @return the value of USER.updated_at
     *
     * @mbg.generated Tue May 12 19:27:17 CST 2020
     */
    public Date getUpdatedAt() {
        return updatedAt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column USER.updated_at
     *
     * @param updatedAt the value for USER.updated_at
     *
     * @mbg.generated Tue May 12 19:27:17 CST 2020
     */
    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}