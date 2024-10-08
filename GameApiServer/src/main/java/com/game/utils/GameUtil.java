package com.game.utils;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.game.bean.proto.BeanProto;
import com.game.data.bean.B_attribute_Bean;
import com.game.data.bean.B_des_Bean;
import com.game.data.manager.DataManager;
import com.game.data.myenum.MyEnumAttributeType;
import com.game.player.structs.Player;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 游戏常用工具(待补充)
 *
 * @author zhangzhen
 */
@Component
@Log4j2
//装载的时候会自动类名首字母小写
@DependsOn("dataManager")
public class GameUtil {

    private @Resource DataManager dataManager;
    /**
     * 服务器和客户端之间的code
     */
    public static final String SERVER_CLIENT_CODE = "lPMK9w7XDk8Kvw70worangewqTCs3fDgSJrAwv";
    /**
     * 服务器之间的code
     */
    public static final String MD5CODE = "bigorange:lin:smallorange89;";
    /**
     * jwt secret
     */
    public static final String JWT_SECRET = "lPMKw7D8Kvw100orangewqCs3DgJrAwv";
    /**
     * 万分比
     */
    public static final double wfb = 10000D;
    /**
     * 万分比int值
     */
    public static final int w = 10000;
    /**
     * 百分比
     */
    public static final float bfb = 100f;
    /**
     * 百分比int值
     */
    public static final int b = 100;
    /**
     * 无效的玩家事件id，收到这个id不需要处理后续逻辑
     */
    public static final long INVALID_PLAYER_EVENT_TIME = -1;
    /**
     * 属性配置
     */
    private static HashMap<Integer, B_attribute_Bean> attBeanMap;

    @PostConstruct
    public void init() {
        attBeanMap = dataManager.c_attribute_Container.getMap();
    }

    public static int[] get2ByteInShort(int value) {
        short l = (short) value;
        short r = (short) value;
        l >>= 8;
        r = (short) (r << 8);
        r >>= 8;
        return new int[]{l, r};
    }

    public static int put2ByteInShort(int left, int right) {
        int v = left;
        v <<= 8;
        right = right & 0xff;
        return (int) (v | right);
    }

    public static int[] get2ShortInInt(int value) {
        int l = value;
        int r = value;
        l >>= 16;
        r = r << 16;
        r >>= 16;
        return new int[]{l, r};
    }

    public static int put2ShortInInt(int left, int right) {
        int v = left;
        v <<= 16;
        right = right & 0xffff;
        return v | right;
    }

    public static int[] get2IntInLong(long value) {
        long l = value;
        long r = value;
        l >>= 32;
        r <<= 32;
        r >>= 32;
        return new int[]{(int) l, (int) r};
    }

    public static long put2IntInLong(long left, long right) {
        long v = left;
        v <<= 32;
        right = right & 0xffffffff;
        return v | right;
    }

    /**
     * 将emoji表情替换成空（替换emoji表情）
     *
     * @param source
     * @return 过滤后的字符串
     */
    public static String emoji(String source) {
        return source.replaceAll("[^\\u0000-\\uFFFF]", "");
    }

    /**
     * 验证名字替换名字中的部分符号
     *
     * @param name
     * @return
     */
    public static String checkName(String name) {
        String newName = name.replaceAll("[ _`~!@#$%^&*()+=|':;',\\[\\]{}<>@.<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？|\\n|\\r|\\t]", "").trim();
        return emoji(newName);
    }

    /**
     * 验证名字替换名字中的部分符号
     *
     * @param string
     * @return
     */
    public static String checkString(String string) {
        String newString = string.replaceAll("[{}<>@]", "").trim();
        return emoji(newString);
    }

    /**
     * 检测字符串是否包含中文
     *
     * @param source
     * @return
     */
    public static boolean checkChinese(String source) {
        Pattern pattern = Pattern.compile("[\u4e00-\u9fa5]");
        Matcher matcher = pattern.matcher(source);
        if (matcher.find()) {
            return true;
        }
        return false;
    }

    /**
     * 根据参数替换字符串
     *
     * @param s
     * @param paramList
     * @return
     */
    public static String replaceString(String s, List<String> paramList) {
        try {
            if (paramList != null && paramList.size() > 0) {
                for (int i = 0, len = paramList.size(); i < len; i++) {
                    s = s.replaceFirst("%s", paramList.get(i));
                }
            }
            return s;
        } catch (Exception e) {
            log.error("根据参数替换字符串", e);
            return s;
        }
    }

    /**
     * 编码排行积分（按需求调用）
     *
     * @param value
     * @return
     */
    public static double encodeRankScoreByTime(long value) {
        try {
            if (value >= 0) {
                return ((value * 10000000000L) + (10000000000L - (System.currentTimeMillis() / 1000)));
            } else {// 负数特殊处理
                value = Math.abs(value);
                return (((value * 10000000000L) + (System.currentTimeMillis() / 1000))) * -1;
            }
        } catch (Exception e) {
            log.error("编码排行积分（按需求调用）", e);
            return 0;
        }
    }

    /**
     * 解码排行积分（按需求调用）
     * 获取参与排行的分数
     *
     * @param value
     * @return
     */
    public static long decodeRankScoreByTime(long value) {
        try {
            return (value / 10000000000L);
        } catch (Exception e) {
            log.error("解码排行积分（按需求调用）", e);
            return 0;
        }
    }

    /**
     * 解码排行时间（按需求调用）
     * 获取参与排行的时间戳（秒）
     *
     * @param value
     * @return
     */
    public static int decodeRankTime(long value) {
        try {
            return (int) (10000000000l - (value % 10000000000l));
        } catch (Exception e) {
            log.error("解码排行时间（按需求调用））", e);
            return 0;
        }
    }

    /**
     * 编码排行积分（排行值升序）
     * 此方法暂不支持负数进行排行
     *
     * @param value
     * @return
     */
    public static double encodeRankScoreBySx(long value) {
        try {
            if (value >= 0) {
                return Integer.MAX_VALUE - value;
            }
            return value;
        } catch (Exception e) {
            log.error("编码排行积分（排行值升序）", e);
            return 0;
        }
    }

    /**
     * 解码排行积分（排行值升序）
     * 此方法暂不支持负数进行排行
     *
     * @param value
     * @return
     */
    public static long decodeRankScoreBySx(long value) {
        try {
            if (value >= 0) {
                return Integer.MAX_VALUE - value;
            }
            return value;
        } catch (Exception e) {
            log.error("解码排行积分（排行值升序）", e);
            return 0;
        }
    }

//	public static void main(String[] args) {
//		int s1 = get2ByteInShort(8888)[0];
//		int s2 = get2ByteInShort(8888)[1];
//		System.out.println(s1);
//		System.out.println(s2);
//		System.out.println(put2ByteInShort(s1, s2));
//
//		int sa1 = (int) get2ShortInInt(18888888)[0];
//		int sa2 = (int) get2ShortInInt(18888888)[1];
//		System.out.println(sa1);
//		System.out.println(sa2);
//		System.out.println(put2ShortInInt(sa1, sa2));

//		System.out.println(replaceString("恭喜玩家%s打造出极品装备%s！", new String[] {"张三","裂空刀"}));
//	}

    /**
     * obj转换成list<Integer[]>
     *
     * @param obj
     * @return
     * @throws Exception
     */
    public static List<Integer[]> objToListArr(Object obj) throws Exception {
        List<Integer[]> list = new ArrayList<Integer[]>();
        if (obj instanceof ArrayList<?>) {
            for (Object o : (List<?>) obj) {
                list.add(Integer[].class.cast(o));
            }
        }
        return list;
    }

    /**
     * obj转换成list<Integer>
     *
     * @param obj
     * @return
     * @throws Exception
     */
    public static List<Integer> objToList(Object obj) throws Exception {
        List<Integer> list = new ArrayList<Integer>();
        if (obj instanceof ArrayList<?>) {
            for (Object o : (List<?>) obj) {
                list.add(Integer.class.cast(o));
            }
        }
        return list;
    }

    /**
     * obj转换成list<Integer>
     *
     * @param obj
     * @return
     * @throws Exception
     */
    public static Map<Integer, Integer> objToMap(Object obj) throws Exception {
        Map<Integer, Integer> map = new LinkedHashMap<Integer, Integer>();
        Class<?> clazz = obj.getClass();
        for (Field field : clazz.getDeclaredFields()) {
            field.setAccessible(true);
            Integer fieldName = Integer.parseInt(field.getName());
            Object value = field.get(obj);
            if (value == null) {
                value = "";
            }
            map.put(fieldName, (Integer) value);
        }
        return map;
    }

    /**
     * 模仿java效率更高，找出数字列表中指定数字的个数
     *
     * @param c
     * @param o
     * @return
     */
    public static int frequency(List<Integer> c, Integer o) {
        int result = 0;
        for (int i = 0, len = c.size(); i < len; i++) {
            Integer integer = c.get(i);
            if (integer.intValue() == o.intValue()) {
                result++;
            }
        }
        return result;
    }

    /**
     * 读取一个boolean
     *
     * @return
     */
    public static boolean readBoolean(byte[] data, int readPos) {
        return readByte(data, readPos) != 0;
    }

    /**
     * 读一个字节
     *
     * @return
     */
    public static int readByte(byte[] data, int readPos) {
        return data[readPos++];
    }

    /**
     * 读一个无符号字节
     *
     * @return
     */
    public static int readUnsignedByte(byte[] data, int readPos) {
        return data[readPos++] & 0xff;
    }

    /**
     * 读一个短整数
     *
     * @param data
     * @param readPos
     * @return
     */
    public static int readShort(byte[] data, int readPos) {
        return (short) (readNumber(data, readPos, 2) & 0xffff);
    }

    /**
     * 读一个字符
     *
     * @param data
     * @param readPos
     * @return
     */
    public static char readChar(byte[] data, int readPos) {
        return (char) (readNumber(data, readPos, 2) & 0xffff);
    }

    /**
     * 读一个无符号短整数
     *
     * @param data
     * @param readPos
     * @return
     */
    public static int readUnsignedShort(byte[] data, int readPos) {
        return (int) (readNumber(data, readPos, 2) & 0xffff);
    }

    /**
     * 读一个整数
     *
     * @param data
     * @param readPos
     * @return
     */
    public static int readInt(byte[] data, int readPos) {
        return (int) (readNumber(data, readPos, 4) & 0xffffffff);
    }

    /**
     * 读一个长整数
     *
     * @param data
     * @param readPos
     * @return
     */
    public static long readLong(byte[] data, int readPos) {
        return readNumber(data, readPos, 8);
    }

    /**
     * 从缓冲区读一个数字到数字缓冲区(大端)
     *
     * @param data    二进制数组
     * @param readPos 读取位置
     * @param len     读取长度
     * @return
     */
    private static long readNumber(byte[] data, int readPos, int len) {
        long value = 0;
        int start = readPos;
        for (int i = start + len - 1; i >= start; i--) {
            value |= (long) (data[start++] & 0xff) << (i << 3);
        }
        return value;
    }

    /**
     * 读取一个boolean
     *
     * @return
     */
    public static boolean readBooleanLE(byte[] data, int readPos) {
        return readByteLE(data, readPos) != 0;
    }

    /**
     * 读一个字节
     *
     * @return
     */
    public static int readByteLE(byte[] data, int readPos) {
        return data[readPos++];
    }

    /**
     * 读一个无符号字节
     *
     * @return
     */
    public static int readUnsignedByteLE(byte[] data, int readPos) {
        return data[readPos++] & 0xff;
    }

    /**
     * 读一个短整数
     *
     * @param data
     * @param readPos
     * @return
     */
    public static int readShortLE(byte[] data, int readPos) {
        return (short) (readNumberLE(data, readPos, 2) & 0xffff);
    }

    /**
     * 读一个字符
     *
     * @param data
     * @param readPos
     * @return
     */
    public static char readCharLE(byte[] data, int readPos) {
        return (char) (readNumberLE(data, readPos, 2) & 0xffff);
    }

    /**
     * 读一个无符号短整数
     *
     * @param data
     * @param readPos
     * @return
     */
    public static int readUnsignedShortLE(byte[] data, int readPos) {
        return (int) (readNumberLE(data, readPos, 2) & 0xffff);
    }

    /**
     * 读一个整数
     *
     * @param data
     * @param readPos
     * @return
     */
    public static int readIntLE(byte[] data, int readPos) {
        return (int) (readNumberLE(data, readPos, 4) & 0xffffffff);
    }

    /**
     * 读一个长整数
     *
     * @param data
     * @param readPos
     * @return
     */
    public static long readLongLE(byte[] data, int readPos) {
        return readNumberLE(data, readPos, 8);
    }

    /**
     * 从缓冲区读一个数字到数字缓冲区(小端)
     *
     * @param data    二进制数组
     * @param readPos 读取位置
     * @param len     读取长度
     * @return
     */
    private static long readNumberLE(byte[] data, int readPos, int len) {
        long value = 0;
        int start = readPos;
        for (int i = 0; i < len; i++) {
            value |= (long) (data[start++] & 0xff) << (i << 3);
        }
        return value;
    }

    /**
     * 解析配置内容成为list，方便程序使用
     *
     * @param content
     * @return
     */
    public static List<Integer> parseConfigByList(String content) {
        try {
            if (StringUtil.isEmptyOrNull(content)) {
                return new ArrayList<>();
            }
            List<Integer> list = new ArrayList<>();
            String[] split = content.split(";");
            for (int i = 0; i < split.length; i++) {
                String string = split[i];
                if (string.isEmpty()) {
                    continue;
                }
                String[] split2 = string.split(",");
                for (int j = 0; j < split2.length; j++) {
                    list.add(Integer.parseInt(split2[j]));
                }
            }
            return list;
        } catch (Exception e) {
            log.error("解析配置内容成为list，方便程序使用", e);
            return Collections.emptyList();
        }
    }

    /**
     * 解析配置内容成为JsonArray，方便程序使用
     *
     * @param content
     * @return
     */
    public static JSONArray parseConfigByJSONArray(String content) {
        try {
            if (content.isEmpty()) {
                return new JSONArray();
            }
            JSONArray array = new JSONArray();
            String[] split = content.split(";");
            for (int i = 0; i < split.length; i++) {
                String string = split[i];
                if (string.isEmpty()) {
                    continue;
                }
                String[] split2 = string.split(",");
                JSONArray arr = new JSONArray();
                for (int j = 0; j < split2.length; j++) {
                    try {
                        arr.add(Integer.parseInt(split2[j]));
                    } catch (Exception e) {
                        arr.add(split2[j]);
                    }
                }
                array.add(arr);
            }
            return array;
        } catch (Exception e) {
            log.error("解析配置内容成为JsonArray，方便程序使用", e);
            return new JSONArray();
        }
    }

    /**
     * 解析配置内容成为map，方便程序使用
     *
     * @param content
     * @return
     */
    public static Map<Integer, Integer> parseConfigByMap(String content) {
        try {
            if (content.isEmpty()) {
                return new HashMap<>();
            }
            Map<Integer, Integer> map = new HashMap<>();
            String[] split = content.split(",");
            for (int i = 0; i < split.length; i++) {
                String string = split[i];
                if (string.isEmpty()) {
                    continue;
                }
                String[] split2 = string.split("[:]");
                map.put(Integer.parseInt(split2[0]), Integer.parseInt(split2[1]));
            }
            return map;
        } catch (Exception e) {
            log.error("解析配置内容成为map，方便程序使用");
            return Collections.emptyMap();
        }
    }

    /**
     * 解析配置内容成为set，方便程序使用
     *
     * @param content
     * @return
     */
    public static Set<Integer> parseConfigBySet(String content) {
        try {
            if (content.isEmpty()) {
                return new HashSet<>();
            }
            Set<Integer> set = new HashSet<>();
            String[] split = content.split(",");
            for (int i = 0; i < split.length; i++) {
                String string = split[i];
                if (string.isEmpty()) {
                    continue;
                }
                String[] split2 = string.split("[:]");
                for (int j = 0; j < split2.length; j++) {
                    set.add(Integer.parseInt(split2[j]));
                }
            }
            return set;
        } catch (Exception e) {
            log.error("解析配置内容成为set，方便程序使用");
            return Collections.emptySet();
        }
    }


    /**
     * 生成房间令牌
     *
     * @param playerId     玩家唯一id
     * @param serverId     玩家逻辑服id
     * @param roomServerId 玩家进入房间服id
     * @param roomId       房间唯一id 0表示进入大厅
     * @return 房间令牌
     */
    public static String roomToken(long playerId, int serverId, int roomServerId, long roomId) {
        try {
            long currentTime = System.currentTimeMillis();
            JSONObject tokenJsonObject = new JSONObject();
            tokenJsonObject.put("playerId", playerId);
            tokenJsonObject.put("playerServerId", serverId);
            tokenJsonObject.put("kcpServerId", roomServerId);
            tokenJsonObject.put("roomId", roomId);
            tokenJsonObject.put("currentTime", currentTime);
            StringBuilder tokenSb = new StringBuilder();
            tokenSb.append(playerId);
            tokenSb.append(",");
            tokenSb.append(serverId);
            tokenSb.append(",");
            tokenSb.append(roomServerId);
            tokenSb.append(",");
            tokenSb.append(roomId);
            tokenSb.append(currentTime);
            tokenSb.append(GameUtil.MD5CODE);
            tokenJsonObject.put("token", CodeUtils.Md5(tokenSb.toString()));
            return new String(CodeUtils.encodeOrDecode(tokenJsonObject.toJSONString().getBytes()), StandardCharsets.UTF_8);
        } catch (Exception e) {
            log.error("异常：", e);
            return "";
        }
    }

    /**
     * 是否是机器人
     *
     * @param playerId
     * @return
     */
    public static boolean isRobot(long playerId) {
        // 因为机器人注册的playerId大于int最大值
        return playerId < 10000 || playerId > Integer.MAX_VALUE;
    }

    /**
     * 构建玩家信息
     *
     * @param player
     * @return
     */
    public static BeanProto.PlayerInfo buildPlayerInfo(Player player) {
        try {
            BeanProto.PlayerInfo.Builder builder = player.getProtoBuf().getPlayerInfoBuilder();
            builder.setPlayerId(player.getPlayerId());
            builder.setPlayerName(player.getPlayerName());
            builder.setHead(player.getHead());
            builder.setSkinId(player.getSkinId());
            builder.setGender(player.getGender());
            builder.setLv(player.getLv());
            boolean isRobot = isRobot(player.getPlayerId());
            builder.setNpc(isRobot);
            return builder.build();
        } catch (Exception e) {
            log.error("构建玩家信息异常：", e);
            return null;
        }
    }

    /**
     * 颜色转16进制
     *
     * @param color
     * @return
     */
    public static String colorToHex(Color color) {
        String redHex = Integer.toHexString(color.getRed());
        String greenHex = Integer.toHexString(color.getGreen());
        String blueHex = Integer.toHexString(color.getBlue());
        // 确保每个分量都是两位的十六进制数
        redHex = redHex.length() == 1 ? "0" + redHex : redHex;
        greenHex = greenHex.length() == 1 ? "0" + greenHex : greenHex;
        blueHex = blueHex.length() == 1 ? "0" + blueHex : blueHex;
        return "#" + redHex + greenHex + blueHex;
    }

    /**
     * 获取语言翻译
     *
     * @param bean
     * @param language
     * @return
     */
    public static String getLanguage(B_des_Bean bean, String language) {
        try {
            if(bean == null){
                return "Config Null";
            }
            if (language.contains("en")) {
                return bean.getEn_us();
            }
            return bean.getZh_cn();
        } catch (Exception e) {
            log.error("获取语言异常：", e);
            return "";
        }
    }

    /**
     * 判断是否是数字（支持整数）
     *
     * @param s 传入的字符串
     * @return true 是数字，false 不是数字
     */
    public static boolean isIntegerOrLang(String s) {
        return s.matches("[0-9]+");
    }

    /**
     * 判断是否是数字（支持123.45 -123.45 123 .45 1.23e-4）
     *
     * @param s 传入的字符串
     * @return true 是数字，false 不是数字
     */
    public static boolean isNumber(String s) {
        // 正则表达式支持带小数的数字，包括负数和科学计数法
        String regex = "^[-+]?[0-9]*\\.?[0-9]+([eE][-+]?[0-9]+)?$";
        return s.matches(regex);
    }

    /**
     * 计算战力
     *
     * @param attList 属性列表[属性类型，属性值，属性类型，属性值，属性类型...]
     * @return
     */
    public static long countForceByKeyValue(List<Long> attList) {
        try {
            // 攻防速血基础战力
            double baseForce = 0;
            // 获取攻击战力
            Long atkValue = ListUtils.get(attList, MyEnumAttributeType.ATK.getId().longValue());
            if (atkValue != null) {
                Long atkBfbValue = ListUtils.get(attList, MyEnumAttributeType.ATK_BFB.getId().longValue());
                if(atkBfbValue != null){
                    baseForce += atkValue / GameUtil.wfb * (GameUtil.wfb + atkBfbValue) * attBeanMap.get(MyEnumAttributeType.ATK.getId()).getCombatPower();
                }else{
                    baseForce += atkValue * attBeanMap.get(MyEnumAttributeType.ATK.getId()).getCombatPower();
                }
            }
            // 计算防御战力
            Long defValue = ListUtils.get(attList, MyEnumAttributeType.DEF.getId().longValue());
            if (defValue != null) {
                Long defBfbValue = ListUtils.get(attList, MyEnumAttributeType.DEF_BFB.getId().longValue());
                if(defBfbValue != null){
                    baseForce += defValue / GameUtil.wfb * (GameUtil.wfb + defBfbValue) * attBeanMap.get(MyEnumAttributeType.DEF.getId()).getCombatPower();
                }else{
                    baseForce += defValue * attBeanMap.get(MyEnumAttributeType.DEF.getId()).getCombatPower();
                }
            }
            // 计算速度战斗力
            Long spdValue = ListUtils.get(attList, MyEnumAttributeType.SPD.getId().longValue());
            if (spdValue != null) {
                Long spdBfbValue = ListUtils.get(attList, MyEnumAttributeType.SPD_BFB.getId().longValue());
                if(spdBfbValue != null){
                    baseForce += spdValue / GameUtil.wfb * (GameUtil.wfb + spdBfbValue) * attBeanMap.get(MyEnumAttributeType.SPD.getId()).getCombatPower();
                }else{
                    baseForce += spdValue * attBeanMap.get(MyEnumAttributeType.SPD.getId()).getCombatPower();
                }
            }
            // 计算血量战力
            Long hpValue = ListUtils.get(attList, MyEnumAttributeType.HP.getId().longValue());
            if (hpValue != null) {
                Long hpBfbValue = ListUtils.get(attList, MyEnumAttributeType.HP_BFB.getId().longValue());
                if(hpBfbValue != null){
                    baseForce += hpValue / GameUtil.wfb * (GameUtil.wfb + hpBfbValue) * attBeanMap.get(MyEnumAttributeType.HP.getId()).getCombatPower();
                }else{
                    baseForce += hpValue * attBeanMap.get(MyEnumAttributeType.HP.getId()).getCombatPower();
                }
            }
            // 其他战力
            double otherForce = 0;
            // 计算其他战斗力
            for (int i = 0; i < attList.size(); i += 2) {
                Integer attType = attList.get(i).intValue();
                // 攻防速血排除
                if (MyEnumAttributeType.SPD.getId() <= attType && attType <= MyEnumAttributeType.DEF.getId()) {
                    continue;
                }
                // 不参与属性计算
                if (attBeanMap.get(attType).getCombatPower() <= 0) {
                    continue;
                }
                Long attValue = attList.get(i + 1);
                otherForce += attValue / GameUtil.wfb / GameUtil.wfb * attBeanMap.get(attType).getCombatPower() * baseForce;
            }
            double ceil = Math.ceil(baseForce + otherForce);
            return (long) ceil;
        } catch (Exception e) {
            log.error("计算战力异常：", e);
            return 0;
        }
    }

    /**
     * 计算战力
     *
     * @param attList 属性列表[属性值，属性值，属性值，属性值，属性值...](属性下标使用MyEnumAttributeType的index)
     * @return
     */
    public static long countForceByIndexValue(List<Long> attList) {
        try {
            // 攻防速血基础战力
            double baseForce = 0;
            // 获取攻击战力
            baseForce += attList.get(MyEnumAttributeType.ATK.getIndex()) / GameUtil.wfb * (GameUtil.wfb + attList.get(MyEnumAttributeType.ATK_BFB.getIndex())) * attBeanMap.get(MyEnumAttributeType.ATK.getId()).getCombatPower();
            // 计算防御战力
            baseForce += attList.get(MyEnumAttributeType.DEF.getIndex()) / GameUtil.wfb * (GameUtil.wfb + attList.get(MyEnumAttributeType.DEF_BFB.getIndex())) * attBeanMap.get(MyEnumAttributeType.DEF.getId()).getCombatPower();
            // 计算速度战斗力
            baseForce += attList.get(MyEnumAttributeType.SPD.getIndex()) / GameUtil.wfb * (GameUtil.wfb + attList.get(MyEnumAttributeType.SPD_BFB.getIndex())) * attBeanMap.get(MyEnumAttributeType.SPD.getId()).getCombatPower();
            // 计算血量战力
            baseForce += attList.get(MyEnumAttributeType.HP.getIndex()) / GameUtil.wfb * (GameUtil.wfb + attList.get(MyEnumAttributeType.HP_BFB.getIndex())) * attBeanMap.get(MyEnumAttributeType.HP.getId()).getCombatPower();
            // 其他战力
            double otherForce = 0;
            // 计算其他战斗力
            for (int i = 0; i < MyEnumAttributeType.getList().size(); i++) {
                Integer attType = MyEnumAttributeType.getList().get(i).getId();
                // 攻防速血排除
                if (MyEnumAttributeType.SPD.getId() <= attType && attType <= MyEnumAttributeType.DEF.getId()) {
                    continue;
                }
                // 不参与属性计算
                if (attBeanMap.get(attType).getCombatPower() <= 0) {
                    continue;
                }
                Long attValue = attList.get(i);
                if(attValue == 0){
                    continue;
                }
                otherForce += attValue / GameUtil.wfb / GameUtil.wfb * attBeanMap.get(attType).getCombatPower() * baseForce;
            }
            double ceil = Math.ceil(baseForce + otherForce);
            return (long) ceil;
        } catch (Exception e) {
            log.error("计算战力异常：", e);
            return 0;
        }
    }

}
