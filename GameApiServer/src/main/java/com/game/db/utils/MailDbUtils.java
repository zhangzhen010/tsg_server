package com.game.db.utils;

import com.game.db.proto.DbProto.MailBean;
import com.game.mail.structs.Mail;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 转换Mail存储格式工具
 *
 * @author zhangzhen
 * @time 2024/06/01 01:00
 */
public class MailDbUtils {


	private static Logger log = LogManager.getLogger(MailDbUtils.class);

	/**
	 * 构建一个可存储的Mail数据
	* @param data
	* @return MailBean
	 */
	public static MailBean buildMailBean(Mail data) {
		try {
			MailBean.Builder builder = MailBean.newBuilder();
			builder.setMailId(data.getMailId());
			builder.setConfigId(data.getConfigId());
			builder.setBackId(data.getBackId());
			builder.setType(data.getType());
			builder.setGetType(data.getGetType());
			builder.setPlayerId(data.getPlayerId());
			builder.setTitle(data.getTitle());
			builder.setSender(data.getSender());
			builder.setContent(data.getContent());
			builder.setMailRead(data.getMailRead());
			builder.setMailGet(data.getMailGet());
			builder.setCreateTime(data.getCreateTime());
			builder.setDelTime(data.getDelTime());
			for (int i = 0, len = data.getParamList().size(); i < len; i++) {
				String value = data.getParamList().get(i);
				builder.addParamList(value);
			}
			for (int i = 0, len = data.getGoodsList().size(); i < len; i++) {
				Integer value = data.getGoodsList().get(i);
				builder.addGoodsList(value);
			}
			return builder.build();
		} catch (Exception e) {
			log.error("构建一个可存储的Mail数据", e);
			return null;
		}
	}

	/**
	 * 解析一个MailBean数据
	* @param data
	* @return Mail
	 */
	public static Mail parseFromMail(byte[] data) {
		try {
			MailBean bean = MailBean.parseFrom(data);
			Mail mail = new Mail();
			mail.setMailId(bean.getMailId());
			mail.setConfigId(bean.getConfigId());
			mail.setBackId(bean.getBackId());
			mail.setType(bean.getType());
			mail.setGetType(bean.getGetType());
			mail.setPlayerId(bean.getPlayerId());
			mail.setTitle(bean.getTitle());
			mail.setSender(bean.getSender());
			mail.setContent(bean.getContent());
			mail.setMailRead(bean.getMailRead());
			mail.setMailGet(bean.getMailGet());
			mail.setCreateTime(bean.getCreateTime());
			mail.setDelTime(bean.getDelTime());
			for (int i = 0, len = bean.getParamListList().size(); i < len; i++) {
				String value = bean.getParamListList().get(i);
				mail.getParamList().add(value);
			}

			for (int i = 0, len = bean.getGoodsListList().size(); i < len; i++) {
				Integer value = bean.getGoodsListList().get(i);
				mail.getGoodsList().add(value);
			}

			return mail;
		} catch (Exception e) {
			log.error("解析一个MailBean数据", e);
			return null;
		}
	}

}