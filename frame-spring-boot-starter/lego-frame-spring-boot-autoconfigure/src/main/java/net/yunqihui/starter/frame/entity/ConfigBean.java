package net.yunqihui.starter.frame.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class ConfigBean implements Serializable{

	private static final long serialVersionUID = 1L;

	private String apiRSAPriKey ;     // 解密私钥
//	private String apiRSAPubKey;      // 加密公钥
	private String apiRSASignPubKey;   // 验签公钥
//	private String apiRSASignPriKey;   // 签名私钥
	private String apiDESKey ;    // 返回数据的解密密钥
	private String appId ;
	private String linkAndroid ;
	private String linkIos ;
	private String smsPassword ;
	private String smsExpire ;  // 短信验证码失效时间
	private String qqkey ;
	private String qqvalue ;
	private String rsaAlipayPublic ;
	private String rsaPrivate ;
	private String seller ;
	private String smsServiceURL ;
	private String smsUserId ;
	private String smsAccount ;
	private String umengAppkey ;
	private String weibokey ;
	private String weibovalue ;
	private String weixinkey ;
	private String weixinvalue ;
	private String tokenTimeout ;


	//支付宝支付
	private String alipayPartner;
	private String alipayKey;
	private String alipaySelller;
	private String alipayNotifyUrl;
	private String alipayRefundUrl;
	private String alipayAppId;
	private String alipayPrivateKey;
	private String alipayPublicKey;
	private String alipayCallBackUrl;
	private String alipayRetunUrl;

	//京东支付
	private String jdPrivateKey;
	private String jdPublicKey;
	private String jdPayUrl;
	private String jdRefundUrl;
	private String jdSuccessCallbackUrl;
	private String jdFailCallbackUrl;
	private String jdNotifyUrl;
	private String jdNum;
	private String jdDesKey;
	private String jdMd5Key;

	//微信支付
	private String wxpayPartner;
	private String wxpayAppId;
	private String wxpayAppSecret;
	private String wxpayPartnerKey;
	private String wxpayP12File;
	private String wxpayNotifyUrl;


	private String smsType;
	private String aliAccessKeyId;
	private String aliAccessKeySecret;
	private String aliSmsProduct;
	private String aliSignName;

	//阿里云实名认证
	private String authenticationHost ;
	private String authenticationAppCode ;


	// 警报邮箱发件方信息
	private String alarmEmailAccout ; // 发件箱登录用户名
	private String alarmEmailAddress ;  // 发件箱邮箱
	private String alarmEmailPwd ;  // 发件箱密码
	private String alarmEmailSubject ; // 警报邮箱主题

	// 推送配置
	private String jpushEnvironment ;   // 推送环境  1开发环境 2生产环境
	private String jpushAppKey ;
	private String jpushAppMaster ;
	private String jpushSecondAppKey ;  // 第二个版本的key
	private String jpushSecondAppMaster ; // 第二个版本的master
	private String jpushThirdAppKey; //第三个版本key
	private String jpushThirdAppMaster; //第三个版本master

	//上传文件相关
	private String imgHardAddress ;
	private String postImgAddress ;

	private String wxAppID;//公众号id
	private String wxAppSecret;//秘钥


}
