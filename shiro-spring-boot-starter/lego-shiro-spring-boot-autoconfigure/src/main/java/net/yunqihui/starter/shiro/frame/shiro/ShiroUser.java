package net.yunqihui.starter.shiro.frame.shiro;

import net.yunqihui.starter.user.entity.User;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.io.Serializable;

/**
 * @Description Shiro的内部User对象
 * @Author Michael Wong
 * @Email michael_wong@yunqihui.net
 * @Date 2019/5/24 10:26
 **/
public class ShiroUser implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     * 编号
     */
    private Long id;
    /**
     * 姓名
     */
    private String name;
    /**
     * 用户类型
     */
    private Integer userType;
    /**
     * 账号
     */
    private String account;

    /**
     * 邮箱
     */
    private String email;
    /**
     * 0.女1.男
     */
    private String sex;
    private String mobile;



    public ShiroUser() {

    }

    // todo 补充db的user后启用
    public ShiroUser(User user) {
        this.id = user.getId();
        this.account = user.getAccount();
        this.name = user.getName();
        this.email = user.getEmail();
        this.userType = user.getUserType();
        this.sex=user.getSex();
        this.mobile=user.getMobile();
    }

    /**
     * 本函数输出将作为默认的<shiro:principal/>输出.
     */
    @Override
    public String toString() {
        return this.account;
    }

    /**
     * 重载equals,只计算account;
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        ShiroUser other = (ShiroUser) obj;
        if (account == null) {
            if (other.account != null) {
                return false;
            }
        } else if (!account.equals(other.account)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(getAccount())
                .toHashCode();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }


    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
