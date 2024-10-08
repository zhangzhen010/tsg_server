package com.game.datagroup.structs;

/**
 * 零散数据类型
 * 
 * @author zhangzhen
 * @time 2023年4月27日
 */
public class DataGroupType {

	/**
	 * 竞技场数据
	 */
	public static final Integer ARENA = 1;
	/**
	 * Solana NFT collection mint keypair 当前合约的mintCollection账户私钥
	 */
	public static final Integer SOLANA_NFT_COLLECTION_MINT_KEYPAIR = 2;
	/**
	 * Solana NFT mint 当前mint队列数据
	 */
	public static final Integer SOLANA_NFT_MINT_QUEUE = 3;
	/**
	 * Solana NFT mint 当前transfer队列数据
	 */
	public static final Integer SOLANA_NFT_TRANSFER_QUEUE = 4;
	/**
	 * Solana NFT mint 当前销毁队列数据
	 */
	public static final Integer SOLANA_NFT_BURN_QUEUE = 5;
	/**
	 * 卡池数据
	 */
	public static final Integer GACHA_POOL = 6;
	/**
	 * 排行榜数据
	 */
	public static final Integer RANK = 100;
	/**
	 * 活动数据（此前缀+活动id）
	 */
	public static final Integer ACTIVITY = 10000;

}
