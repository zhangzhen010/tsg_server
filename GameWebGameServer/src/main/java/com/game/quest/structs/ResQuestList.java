package com.game.quest.structs;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * 返回任务列表数据
 */
@Getter
public class ResQuestList {

    private final List<ResQuestInfo> questInfoList = new ArrayList<>();

}
