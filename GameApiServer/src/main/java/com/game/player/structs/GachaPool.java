package com.game.player.structs;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

/**
 * 抽卡奖池（后台配置）
 */
@Getter
@Setter
@Document(collection = "gachaPool")
public class GachaPool {

    /**
     * 奖池唯一id
     */
    @Id
    private String id;
    /**
     * 奖池名称
     */
    private String name;
    /**
     * 奖池背景图片或者视频路径列表（一个卡池可以展示多张图片或者视频）
     */
    private List<String> imageUrlList = new ArrayList<>();
    /**
     * 奖池抽奖动画路径
     */
    private String animationUrl;
    /**
     * 是否首页推荐
     */
    private boolean recommend;
    /**
     * 卡池说明
     */
    private String description;
    /**
     * 卡池开始时间
     */
    private long startTime;
    /**
     * 卡池结束时间
     */
    private long endTime;
    /**
     * 抽卡消耗candy数
     */
    private int candy;
    /**
     * 卡池卡片信息列表
     */
    private List<GachaPoolWeight> weightList = new ArrayList<>();
    /**
     * 已经抽卡卡片数量
     */
    private int drawCardCount;
    /**
     * 卡片总数=已抽取卡片数量+剩余未抽取卡片数量
     */
    @Transient
    private transient int totalCardCount;
}
