package com.game.gacha.structs;

import lombok.Getter;
import lombok.Setter;

/**
 * 请求mint卡片
 *
 * @author zhangzhen
 * @version 1.0
 * @time 2024/9/20 17:31
 */
@Getter
@Setter
public class ReqMintCardInfo {

    /**
     * 卡片模板id
     */
    private String id;
    /**
     * mint卡片数量
     */
    private int count;

}
