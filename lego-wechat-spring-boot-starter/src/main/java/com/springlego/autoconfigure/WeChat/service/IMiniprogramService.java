package com.springlego.autoconfigure.WeChat.service;

import org.dom4j.Element;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @Description 小程序 服务类
 * @Author Michael Wong
 * @Email michael_wong@yunqihui.net
 * @Date 2019/12/13 16:05
 **/
public interface IMiniprogramService {

    /**
     * @desc: 快速创建小程序。仅处理与微信方交互，具体信息持久化由调用方负责
     * @param name 企业名（需与工商部门登记信息一致）
     * @param code 企业代码
     * @param codeType 企业代码类型（1：统一社会信用代码， 2：组织机构代码，3：营业执照注册号）
     * @param legalPersonaWechat 法人微信
     * @param legalPersonaName  法人姓名（绑定银行卡）
     * @return: boolean 是否创建成功
     * @auther: Michael Wong
     * @email:  michael_wong@yunqihui.net
     * @date:   2019/12/13 16:09
     * @update:
     */
    boolean fastRegister(@NotBlank String openId,@NotNull Integer goodId,@NotBlank String name, @NotBlank String code, @NotNull Integer codeType,@NotBlank String legalPersonaWechat,@NotNull String legalPersonaName) throws Exception;


    /**
     * @desc: 根据微信回调url获取快速创建小程序结果，并进行相应处理
     * @param element 原始信息消息体
     * @return: java.lang.Boolean
     * @auther: Michael Wong
     * @email:  michael_wong@yunqihui.net
     * @date:   2019/12/25 15:08
     * @update:
     */
    Boolean registerCheckCallback(Element element) throws Exception;

    Boolean modifyDomain(String action,String[] requestdomain,String[] wsrequestdomain,String[] uploaddomain,String[] downloaddomain) throws Exception;
}
