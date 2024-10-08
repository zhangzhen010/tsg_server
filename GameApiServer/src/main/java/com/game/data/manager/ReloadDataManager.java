package com.game.data.manager;

import com.game.data.container.C_z_server_ip_black_Container;
import com.game.data.container.C_z_server_ip_white_Container;
import com.game.data.structs.IContainer;
import com.game.manager.structs.IManager;
import jakarta.annotation.Resource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;

/**
 * 重新加载配置管理类
 *
 * @author zhangzhen
 * @version 1.0
 * @time 2023/12/13 17:32
 */
@Component
public class ReloadDataManager {

    /**
     * 日志
     */
    private static final Logger log = LogManager.getLogger("REDATA");
    /**
     * 不可以重加载表
     */
    private static final HashSet<String> unreloads = new HashSet<String>();

    static {
        unreloads.add("C_cdk_Container");
    }

    /**
     * 配置管理类
     */
    private @Resource DataManager dataManager;
    /**
     * 管理类列表
     */
    private final List<IManager> managerList = new ArrayList<>();

    /**
     * 添加需要关联的管理器
     *
     * @param manager
     */
    public void addManager(IManager manager) {
        this.managerList.add(manager);
    }

    /**
     * 重新加载配置表
     *
     * @param tableNames
     * @return
     */
    public boolean reLoad(String tableNames) {
        log.info("重新加载配置表(" + tableNames + ")开始");
        try {
            if (tableNames == null || tableNames.isEmpty()) {
                log.info("重新加载配置表(" + tableNames + ")失败,因为表名为空");
                return false;
            }
            // 解析加载表名
            String[] tableName = tableNames.split(",");
            HashMap<String, Integer> tables = new HashMap<String, Integer>();
            for (int i = 0; i < tableName.length; i++) {
                String name = tableName[i];
                String className = "C_" + name + "_Container";
                if (unreloads.contains(className)) {
                    log.info("重新加载配置表(" + name + ")失败,因为此表不可以重新加载");
                    return false;
                }
                tables.put(className, 0);
            }
            // 重新加载后容器集合
            List<IContainer> others = new ArrayList<>();
            // 所有容器集合
            List<IContainer> containerList = dataManager.getContainerList();
            for (int i = 0, len = containerList.size(); i < len; i++) {
                IContainer container = containerList.get(i);
                if (tables.containsKey(container.getClass().getSimpleName())) {
                    // 重新加载
                    long time = System.currentTimeMillis();
                    container.load();
                    log.info("加载配置表" + container.getClass().getSimpleName() + "耗时：" + (System.currentTimeMillis() - time));
                    // 两个ip配置表可以重新加载为空
                    if (container.isEmpty() && (!(container instanceof C_z_server_ip_white_Container)) && (!(container instanceof C_z_server_ip_black_Container))) {
                        log.error("重新加载配置表异常，配置表数据为空：" + container.getClass().getName());
                        continue;
                    }
                    others.add(container);
                    tables.put(container.getClass().getSimpleName(), 1);
                }
            }
            // 是否加载完成
            boolean finish = true;
            // 未加载表名字
            StringBuilder unloadNames = new StringBuilder();
            // 判断是否有表未加载成功
            for (Entry<String, Integer> entry : tables.entrySet()) {
                if (entry.getValue() == 0) {
                    finish = false;
                    unloadNames.append(",").append(entry.getKey());
                }
            }
            if (!finish) {
                unloadNames.deleteCharAt(0);
                log.info("重新加载配置表(" + unloadNames + ")失败");
                return false;
            }
            // 替换之前容器
            for (int i = 0, len = others.size(); i < len; i++) {
                IContainer container = others.get(i);
//                if (container instanceof C_activity_Container) {
//
//                }
                // 触发加载配置后关联逻辑
                for (int j = 0; j < managerList.size(); j++) {
                    IManager manager = managerList.get(j);
                    manager.updateConfig(container);
                }
            }
            log.info("重新加载配置表(" + tableNames + ")成功，本次热更配置数量：" + others.size());
            return true;
        } catch (Exception e) {
            log.error("重新加载配置表(" + tableNames + ")失败:", e);
            return false;
        }
    }
}
