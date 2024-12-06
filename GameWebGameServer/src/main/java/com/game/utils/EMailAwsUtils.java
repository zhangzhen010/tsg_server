package com.game.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.ses.SesClient;
import software.amazon.awssdk.services.ses.model.*;

/**
 * 亚马逊邮件发送工具
 * <p>
 * 使用亚马逊SDK包发送邮件，还需要额外使用到共享凭证文件
 * <p>
 * Windows系统凭证存放地址+文件名 C:\Users\<yourUserName>\.aws\credentials Linux、macOS 或
 * Unix ~/.aws/credentials 文件配置参考
 * https://docs.aws.amazon.com/zh_cn/ses/latest/dg/create-shared-credentials-file.html
 *
 * @author zhangzhen
 * @version 1.0
 * @time 2024/11/5 18:18
 */
public class EMailAwsUtils {

    private static Logger log = LogManager.getLogger(EMailAwsUtils.class);

    /**
     * 发件人
     */
    private static final String from = "noreply@abc.io";

    /**
     * 收件人
     */
//	private static final String TO = "animomastor@gmail.com";

    // The configuration set to use for this email. If you do not want to use a
    // configuration set, comment the following variable and the
    // .withConfigurationSetName(CONFIGSET); argument below.
//	static final String CONFIGSET = "ConfigSet";

    /**
     * 邮件主题
     */
//	private static final String SUBJECT = "Amazon SES test (AWS SDK for Java)";

    /**
     * 邮件内容（HTML）
     */
//	private static final String HTMLBODY = "<h1>Amazon SES test (AWS SDK for Java)</h1>"
//			+ "<p>This email was sent with <a href='https://aws.amazon.com/ses/'>"
//			+ "Amazon SES</a> using the <a href='https://aws.amazon.com/sdk-for-java/'>" + "AWS SDK for Java</a>";

    /**
     * 邮件内容（非HTML）
     */
//	private static final String TEXTBODY = "This email was sent through Amazon SES " + "using the AWS SDK for Java.";

    /**
     * 发送亚马逊邮件(此方法过程较慢，建议异步或者单独线程调用)
     *
     * @param email   收件人邮箱地址
     * @param newCode
     */
    public static void sendMail(String email, int newCode) {
        try {
            // 创建 SES 客户端
            Region region = Region.AP_NORTHEAST_1;
            SesClient sesClient = SesClient.builder().region(region).credentialsProvider(ProfileCredentialsProvider.create()).build();
            // 邮件内容
            String subject = "CAPTCHA code";
            String bodyText = "Your email verification code is " + newCode + ", please keep the code safe and don't share it with anyone, the TSG team will never ask you for the code.";
            // 构建邮件
            Destination destination = Destination.builder().toAddresses(email).build();
            Content subjectContent = Content.builder().data(subject).build();
            Body body = Body.builder().text(Content.builder().data(bodyText).build()).build();
            Message message = Message.builder().subject(subjectContent).body(body).build();
            SendEmailRequest sendEmailRequest = SendEmailRequest.builder().source(from).destination(destination).message(message).build();
            // 发送邮件
            try {
                SendEmailResponse response = sesClient.sendEmail(sendEmailRequest);
                log.info("发送aws邮件 Message ID: " + response.messageId());
            } catch (SesException e) {
                log.error("邮件登录发送aws邮件异常：" + e.awsErrorDetails().errorMessage());
            } finally {
                // 关闭客户端
                sesClient.close();
            }
        } catch (Exception ex) {
            log.info("亚马逊邮件发送异常：" + ex.getMessage(), ex);
        }
    }

}
