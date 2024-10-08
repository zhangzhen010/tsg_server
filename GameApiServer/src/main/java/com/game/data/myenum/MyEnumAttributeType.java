package com.game.data.myenum;

import lombok.Getter;

import java.util.*;

/**
 * MyEnumAttributeType
 *
 * @author zhangzhen
 * @version 1.0
 * @time 2024/06/01 00:00
 */
@Getter
public enum MyEnumAttributeType {
	/**
	 * 速度固定值
	 */
	SPD(1, 0),
	/**
	 * 生命固定值
	 */
	HP(2, 1),
	/**
	 * 攻击固定值
	 */
	ATK(3, 2),
	/**
	 * 防御固定值
	 */
	DEF(4, 3),
	/**
	 * 吸血百分比
	 */
	XIXUE(5, 4),
	/**
	 * 反击百分比
	 */
	FANJI(6, 5),
	/**
	 * 连击百分比
	 */
	LIANJI(7, 6),
	/**
	 * 闪避百分比
	 */
	SHANBI(8, 7),
	/**
	 * 暴击百分比
	 */
	BAOJI(9, 8),
	/**
	 * 击晕百分比
	 */
	JIYUN(10, 9),
	/**
	 * 速度百分比
	 */
	SPD_BFB(101, 10),
	/**
	 * 生命百分比
	 */
	HP_BFB(102, 11),
	/**
	 * 生命百分比
	 */
	ATK_BFB(103, 12),
	/**
	 * 防御百分比
	 */
	DEF_BFB(104, 13),
	/**
	 * 抗吸血百分比
	 */
	KANGXIXUE_BFB(201, 14),
	/**
	 * 抗反击
	 */
	KANGFANJI_BFB(202, 15),
	/**
	 * 抗连击
	 */
	KANGLIANJI_BFB(203, 16),
	/**
	 * 抗闪避
	 */
	KANGSHANBI_BFB(204, 17),
	/**
	 * 抗暴击
	 */
	KANGBAOJI_BFB(205, 18),
	/**
	 * 抗击晕
	 */
	KANGJIYUN_BFB(206, 19),
	/**
	 * 暴伤提升
	 */
	BAOSHANGTISHENG_BFB(301, 20),
	/**
	 * 暴伤降低
	 */
	BAOSHANGJIANGDI_BFB(302, 21),
	/**
	 * 最终增伤
	 */
	ZUIZHONGZENGSHANG_BFB(303, 22),
	/**
	 * 最终减伤
	 */
	ZUIZHONGJIANSHANG_BFB(304, 23),
	/**
	 * 强化治疗
	 */
	QIANGHUAZHILIAO_BFB(305, 24),
	/**
	 * 弱化治疗
	 */
	RUOHUAZHILIAO_BFB(306, 25),
	/**
	 * 强化宠物
	 */
	QIANGHUACHONGWU_BFB(307, 26),
	/**
	 * 弱化宠物
	 */
	RUOHUACHONGWU_BFB(308, 27),
	/**
	 * 强化宠物治疗
	 */
	QIANGHUACHONGWUZHILIAO_BFB(309, 28),
	/**
	 * 弱化宠物治疗
	 */
	RUOHUACHONGWUZHILIAO_BFB(310, 29),
	/**
	 * 强化怒气技能伤害
	 */
	QIANGHUANUQIJINENGSHANGHAI_BFB(311, 30),
	/**
	 * 弱化怒气技能伤害
	 */
	RUOHUANUQIJINENGSHANGHAI_BFB(312, 31),
	/**
	 * 战斗属性提升
	 */
	ZHANDOUSHUXINGTISHENG_BFB(313, 32),
	/**
	 * 战斗属性提升
	 */
	ZHANDOUKANGXINGTISHENG_BFB(314, 33),
	/**
	 * 无视战斗属性
	 */
	WUSHIZHANDOUSHUXING_BFB(315, 34),
	/**
	 * 无视战斗抗性
	 */
	WUSHIZHANDOUKANGXING_BFB(316, 35),
	/**
	 * 增加反击伤害
	 */
	ZENGJIAFANJISHANGHAI_BFB(317, 36),
	/**
	 * 减低反击伤害
	 */
	JIANDIFANJISHANGHAI_BFB(318, 37),
	/**
	 * 怒气
	 */
	MP(401, 38),
	/**
	 * 目标最大生命值
	 */
	MUBIAOZUIDASHENGMINGZHI_BFB(501, 39),
	/**
	 * 目标已损生命值
	 */
	MUBIAOYISUNSHISHENGMINGZHI_BFB(502, 40),
	/**
	 * 最终伤害加成
	 */
	ZUIZHONGSHANGHAI_BFB(503, 41),
	/**
	 * 目标当前生命值
	 */
	MUBIAODANGQIANSHENGMINGZHI_BFB(504, 42),
	/**
	 * 增加宠物伤害
	 */
	ZENGJIACHONGWUSHANGHAI_BFB(505, 43),

	;

	private final Integer id;

	private final Integer index;

	private static final Map<Integer, MyEnumAttributeType> map = new HashMap<>();

	private static final List<MyEnumAttributeType> list = new ArrayList<>();

	MyEnumAttributeType(Integer id, Integer index) {
		this.id = id;
		this.index = index;
	}

	public static MyEnumAttributeType getMyEnumAttributeType(Integer id) {
		if (map.isEmpty()) {
			synchronized (map) {
				if (map.isEmpty()) {
					MyEnumAttributeType[] values = MyEnumAttributeType.values();
					for (MyEnumAttributeType value : values) {
						map.put(value.getId(), value);
					}
				}
			}
		}
		return map.get(id);
	}

	public static List<MyEnumAttributeType> getList() {
		if (list.isEmpty()) {
			synchronized (list) {
				if (list.isEmpty()) {
					list.addAll(Arrays.asList(MyEnumAttributeType.values()));
				}
			}
		}
		return list;
	}

}