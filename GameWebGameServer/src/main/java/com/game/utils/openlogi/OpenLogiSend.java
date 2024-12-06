package com.game.utils.openlogi;

import lombok.Data;

@Data
public class OpenLogiSend {
    /**
     * 收件人
     */
    private Object recipient;
    /**
     * 物品信息
     */
    private OpenLogiItems OpenLogiItems;

}
