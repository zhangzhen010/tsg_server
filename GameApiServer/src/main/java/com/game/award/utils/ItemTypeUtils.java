package com.game.award.utils;

import com.game.data.bean.B_item_Bean;
import com.game.data.manager.DataManager;
import jakarta.annotation.Resource;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

/**
 * 物品类型
 *
 * @author zhangzhen
 * @version 1.0
 * @time 2024/3/1 16:17
 */
@Component
@Log4j2
public class ItemTypeUtils {

    private @Resource DataManager dataManager;

    /**
     * 根据物品配置id获取常量类型
     *
     * @param itemConfigId 物品配置id
     * @return 物品类型常量（MyDefineItemType）
     */
    public Integer getItemType(Integer itemConfigId) {
        try {
            B_item_Bean itemBean = dataManager.c_item_Container.getMap().get(itemConfigId);
            return itemBean.getType();
        } catch (Exception e) {
            log.error("根据物品配置id获取常量类型异常：itemConfigId=" + itemConfigId, e);
            return null;
        }
    }

}
