package com.game.message;

import java.lang.reflect.Method;
import java.util.HashMap;

/**
 * MessageProtoPool
 *
 * @author zhangzhen
 */
public class MessageProtoPool {

    private static MessageProtoPool instance = new MessageProtoPool();

    public static MessageProtoPool getInstance() {
        return instance;
    }

    private HashMap<Integer, Method> protoMap = new HashMap<>();

    public void register(int id, Method messageClass) {
        protoMap.put(id, messageClass);
    }

    public Method createProto(Integer id) {
        Method protoClass = protoMap.get(id);
        if (protoClass == null) {
            return null;
        }
        return protoClass;
    }

    private MessageProtoPool() {
        try {

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}