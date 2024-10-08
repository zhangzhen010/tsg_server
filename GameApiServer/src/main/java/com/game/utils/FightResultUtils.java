package com.game.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 战斗结果处理工具
 * 
 * @author zhangzhen
 * @time 2023年1月3日
 */
public class FightResultUtils {

	private static Logger log = LogManager.getLogger(FightResultUtils.class);

	/**
	 * 根据战斗数据获取战斗胜负
	 * 
	 * @param data
	 * @return
	 */
	public static boolean fightResult(byte[] data) {
		try {
			// code 0成功；1计算错误
			int code = GameUtil.readIntLE(data, 0);
			// 闯关判定条件，winCamp为0，allDead为1，其他都是闯关失败
			int winCamp = GameUtil.readIntLE(data, 4);
			// 0：双方都未全部死亡，表示战斗时间结束；1：战斗双方有一方全部死了，表示正常战斗结束
			int allDead = GameUtil.readByteLE(data, 8);
			// 所用帧数(暂时没用)
			int frame = GameUtil.readIntLE(data, 9);
			int win = (code == 0 && winCamp == 0 && allDead == 1) ? 1 : 0;
			log.info("解析战斗结果code=" + code + " win=" + winCamp + " allDead=" + allDead + " frame=" + frame);
			if (win == 1) {
				return true;
			}
			return false;
		} catch (Exception e) {
			log.error("根据战斗数据获取战斗胜负", e);
			return false;
		}
	}

}
