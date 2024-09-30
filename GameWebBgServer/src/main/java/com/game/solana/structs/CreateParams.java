package com.game.solana.structs;

import lombok.Getter;
import lombok.Setter;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;

/**
 * 合约定义mint参数
 *
 * @author zhangzhen
 * @version 1.0
 * @time 2024/9/11 14:16
 */
@Getter
@Setter
public class CreateParams {

    /**
     * 名称
     */
    private String name;
    /**
     * 符号
     */
    private String symbol;
    /**
     * 数据体
     */
    private String uri;
    /**
     * 时间戳
     */
    private long timestamp;

    public CreateParams(String name, String symbol, String uri, long timestamp) {
        this.name = name;
        this.symbol = symbol;
        this.uri = uri;
        this.timestamp = timestamp;
    }

    public byte[] toBytes() {
        ByteBuffer buffer = ByteBuffer.allocate(4 + calculateStringLength(name) + 4 + calculateStringLength(symbol) + 4 + calculateStringLength(uri) + 8);
        // 设置为小端字节序
        buffer.order(ByteOrder.LITTLE_ENDIAN);

        // name长度
        buffer.putInt(name.getBytes(StandardCharsets.UTF_8).length);
        // name
        buffer.put(name.getBytes(StandardCharsets.UTF_8));

        // symbol长度
        buffer.putInt(symbol.getBytes(StandardCharsets.UTF_8).length);
        // symbol
        buffer.put(symbol.getBytes(StandardCharsets.UTF_8));

        // uri长度
        buffer.putInt(uri.getBytes(StandardCharsets.UTF_8).length);
        // uri
        buffer.put(uri.getBytes(StandardCharsets.UTF_8));

        // 时间戳(秒)
        buffer.putLong(timestamp);

        return buffer.array();
    }

    public static CreateParams fromBytes(byte[] bytes) {
        ByteBuffer buffer = ByteBuffer.wrap(bytes);
        buffer.order(ByteOrder.LITTLE_ENDIAN);  // 设置为小端字节序

        // Read the length of the name field
        int nameLength = buffer.getInt();
        // Read the name field
        byte[] nameBytes = new byte[nameLength];
        buffer.get(nameBytes);
        String name = new String(nameBytes, StandardCharsets.UTF_8);

        // Read the length of the symbol field
        int symbolLength = buffer.getInt();
        // Read the symbol field
        byte[] symbolBytes = new byte[symbolLength];
        buffer.get(symbolBytes);
        String symbol = new String(symbolBytes, StandardCharsets.UTF_8);

        // Read the length of the uri field
        int uriLength = buffer.getInt();
        // Read the uri field
        byte[] uriBytes = new byte[uriLength];
        buffer.get(uriBytes);
        String uri = new String(uriBytes, StandardCharsets.UTF_8);

        // Read the timestamp field
        long timestamp = buffer.getLong();

        return new CreateParams(name, symbol, uri, timestamp);
    }

    private int calculateStringLength(String str) {
        return str.getBytes(StandardCharsets.UTF_8).length;
    }

}
