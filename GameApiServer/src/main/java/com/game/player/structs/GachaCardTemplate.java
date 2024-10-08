package com.game.player.structs;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

/**
 * 卡池卡片模板信息
 *
 * @author zhangzhen
 * @version 1.0
 * @time 2024/9/9 20:57
 */
@Getter
@Setter
@Document(collection = "gachaCardTemplate")
public class GachaCardTemplate {

    /**
     * 卡片模板唯一id
     */
    @Id
    private String id;
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
     * 价值（美分）
     */
    private int usd;
    /**
     * 附加参数（里面的等级和品质，单独在外面存放了一份）
     */
    private List<CardAttribute> attributes = new ArrayList<>();

}
