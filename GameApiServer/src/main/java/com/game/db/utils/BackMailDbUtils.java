package com.game.db.utils;

import com.game.db.proto.DbProto.BackMailBean;
import com.game.mail.structs.BackMail;
import com.google.protobuf.ByteString;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 转换BackMail存储格式工具
 *
 * @author zhangzhen
 * @time 2024/06/01 01:00
 */
public class BackMailDbUtils {


	private static Logger log = LogManager.getLogger(BackMailDbUtils.class);

	/**
	 * 构建一个可存储的BackMail数据
	* @param data
	* @return BackMailBean
	 */
	public static BackMailBean buildBackMailBean(BackMail data) {
		try {
			BackMailBean.Builder builder = BackMailBean.newBuilder();
			builder.setBackId(data.getBackId());
			builder.setType(data.getType());
			builder.setGetType(data.getGetType());
			builder.setSender(data.getSender());
			for (int i = 0, len = data.getReceivePlayerIdList().size(); i < len; i++) {
				Long value = data.getReceivePlayerIdList().get(i);
				builder.addReceivePlayerIdList(value);
			}
			builder.setTitle(data.getTitle());
			builder.setContent(data.getContent());
				if(data.getExtInfo() != null) {
					builder.setExtInfo(ByteString.copyFrom(data.getExtInfo()));
				}
			builder.setCreateTime(data.getCreateTime());
			builder.setSendTime(data.getSendTime());
			builder.setMailState(data.getMailState());
			for (int i = 0, len = data.getGoodsList().size(); i < len; i++) {
				Integer value = data.getGoodsList().get(i);
				builder.addGoodsList(value);
			}
			return builder.build();
		} catch (Exception e) {
			log.error("构建一个可存储的BackMail数据", e);
			return null;
		}
	}

	/**
	 * 解析一个BackMailBean数据
	* @param data
	* @return BackMail
	 */
	public static BackMail parseFromBackMail(byte[] data) {
		try {
			BackMailBean bean = BackMailBean.parseFrom(data);
			BackMail backMail = new BackMail();
			backMail.setBackId(bean.getBackId());
			backMail.setType(bean.getType());
			backMail.setGetType(bean.getGetType());
			backMail.setSender(bean.getSender());
			for (int i = 0, len = bean.getReceivePlayerIdListList().size(); i < len; i++) {
				Long value = bean.getReceivePlayerIdListList().get(i);
				backMail.getReceivePlayerIdList().add(value);
			}

			backMail.setTitle(bean.getTitle());
			backMail.setContent(bean.getContent());
			backMail.setExtInfo(bean.getExtInfo().toByteArray());
			backMail.setCreateTime(bean.getCreateTime());
			backMail.setSendTime(bean.getSendTime());
			backMail.setMailState(bean.getMailState());
			for (int i = 0, len = bean.getGoodsListList().size(); i < len; i++) {
				Integer value = bean.getGoodsListList().get(i);
				backMail.getGoodsList().add(value);
			}

			return backMail;
		} catch (Exception e) {
			log.error("解析一个BackMailBean数据", e);
			return null;
		}
	}

}