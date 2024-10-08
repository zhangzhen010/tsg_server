package com.game.player.structs;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 战斗者
 *
 * @author zhangzhen
 * @version 1.0
 * @time 2024/5/22 下午6:30
 */
@Getter
@Setter
public class Fighter {

    /**
     * 战斗者唯一id
     */
    private Long fighterId;
    /**
     * 角色名称
     */
    private String playerName = "";
    /**
     * 头像
     */
    private String head = "";
    /**
     * 配置id
     */
    private int configId;
    /**
     * 性别 0女 1男
     */
    private int gender;
    /**
     * 等级
     */
    private int lv;
    /**
     * 当前生命值
     */
    private long hp;
    /**
     * 当前能量
     */
    private int mp;
    /**
     * 整场战斗触发击晕总次数
     */
    private int jiyunTotalNum;
    /**
     * 整场战斗触发闪避总次数
     */
    private int shanbiTotalNum;
    /**
     * 整场战斗触发反击总次数
     */
    private int fanjiTotalNum;
    /**
     * 当前回合战斗触发反击次数
     */
    private int fanjiRoundNum;
    /**
     * 当前回合战斗触发被反击次数
     */
    private int beFanjiRoundNum;
    /**
     * 整场战斗触发暴击总次数
     */
    private int baojiTotalNum;
    /**
     * 整场战斗触发连击总次数
     */
    private int lianjiTotalNum;
    /**
     * 当前回合战斗触发连击次数
     */
    private int lianjiRoundNum;
    /**
     * 当前回合战斗触发被连击次数
     */
    private int beLianjiRoundNum;
    /**
     * 当前回合是否已行动过 true=已行动
     */
    private boolean action;
    /**
     * 本场战斗是否已复活，true=已复活
     */
    private boolean fuhuo;
    /**
     * 己方所属队伍
     */
    private FightTeam team;
    /**
     * 地方所属队伍
     */
    private FightTeam targetTeam;
    /**
     * 角色佩戴主动技能id，如果为0，不激活主动技能和怒气功能
     */
    private int mainSkillId;
    /**
     * 角色拥有被动技能配置id列表（宠物和精怪直接读取配置）
     */
    private List<Integer> beSkillIdList = new ArrayList<>();
    /**
     * 技能cd
     */
    private Map<Integer, Integer> cdMap = new HashMap<>();
    /**
     * 效果上一次触发回合数 key=效果配置id value=上一次触发回合数
     */
    private Map<Integer, Integer> effectUseLastRoundMap = new HashMap<>();
    /**
     * 战斗中使用效果次数（有些效果限制整场战斗只能使用1次）key=效果配置id value=效果触发次数
     */
    private Map<Integer, Integer> useEffectCountMap = new HashMap<>();
    /**
     * 被动效果，用于激活buff(激活buff的效果相同id只会存在一个)
     */
    private List<FightEffect> effectList = new ArrayList<>();
    /**
     * 战斗者战斗中挂载的buff列表
     */
    private List<FightBuff> buffList = new ArrayList<>();
    /**
     * 战斗者战斗外基础属性列表值，下标对应MyEnumAttributeType.index
     */
    private List<Long> attBaseList = new ArrayList<>();
    /**
     * 战斗者战斗中buff属性列表值，下标对应MyEnumAttributeType.index
     */
    private List<Long> attBuffList = new ArrayList<>();
    /**
     * 战斗者战斗者转换属性列表值，下标对应MyEnumAttributeType.index
     */
    private List<Long> attChangeList = new ArrayList<>();
    /**
     * 战斗者GM属性列表，下标对应MyEnumAttributeType.index(暂未实现，看需求)
     */
    private List<Long> attGmList = new ArrayList<>();

}
