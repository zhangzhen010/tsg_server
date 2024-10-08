package com.game.player.structs;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * 战斗队伍
 *
 * @author zhangzhen
 * @version 1.0
 * @time 2024/5/31 10:22
 */
@Getter
@Setter
public class FightTeam {

    /**
     * 角色战斗者
     */
    private Fighter role;
    /**
     * 宠物战斗者唯一id列表
     */
    private List<Fighter> petList = new ArrayList<>();
    /**
     * 精怪战斗者唯一id列表
     */
    private List<Fighter> monsterList = new ArrayList<>();

}
