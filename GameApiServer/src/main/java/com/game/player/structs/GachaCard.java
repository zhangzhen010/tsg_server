package com.game.player.structs;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

/**
 * 卡池卡片信息
 *
 * @author zhangzhen
 * @version 1.0
 * @time 2024/9/9 20:57
 */
@Getter
@Setter
@Document(collection = "gachaCard")
public class GachaCard {

    /**
     * 卡片唯一id
     */
    @Id
    private String id;
    /**
     * 卡片模板id
     */
    private String gachaCardTemplateId;
    /**
     * 卡片mint账户
     */
    private String mintPublicKey;
    /**
     * 卡片元数据账户
     */
    private String metaPublicKey;
    /**
     * 卡片名字
     */
    private String name;
    /**
     * 卡片描述
     */
    private String description;
    /**
     * 卡片资源url
     */
    private String image;
    /**
     * 抽卡消耗candy
     */
    private int cost;
    /**
     * 卡片价值美元（单位：美分）
     */
    private int usd;
    /**
     * 卡片等级用于排序(ssr=100,sr=200,r=300,l=400,nr=500)
     */
    private int level;
    /**
     * 卡片品质[SSR,SR,R,L,NR]
     */
    private String quality;
    /**
     * 所属卡池
     */
    private long gachaPoolId;
    /**
     * 卡片拥有者唯一id（只能记录初次从卡池抽取的玩家id，后面自由交易不能拿来作为判断）
     */
    private long ownerPlayerId;
    /**
     * 回收candy比例（百分比）
     */
    private int burnCandyRatio;
    /**
     * 回收同质化代币比例（百分比）
     */
    private int burnFtRatio;
    /**
     * 是否已被回收 true=已销毁
     */
    private boolean burn;
    /**
     * 附加参数（里面的等级和品质，单独在外面存放了一份）
     */
    private List<CardAttribute> attributes = new ArrayList<>();
    /**
     * 卡片数量（仅在返回客户端，作为客户端字段使用）
     */
    @Transient
    private transient int num = 1;
}
