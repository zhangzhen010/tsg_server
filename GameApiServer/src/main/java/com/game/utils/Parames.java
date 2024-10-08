package com.game.utils;

/**
 * 参数bean
 * @author zhangzhen
 * @date 2017年8月14日
 * @version 1.0
 */
public class Parames {

	private String canshuName;
	private String canshuValue;
	public Parames(){
		
	}
	public Parames(String key, String value){
		this.canshuName = key;
		this.canshuValue = value;
	}
	public String getCanshuName() {
		return canshuName;
	}
	public void setCanshuName(String canshuName) {
		this.canshuName = canshuName;
	}
	public String getCanshuValue() {
		return canshuValue;
	}
	public void setCanshuValue(String canshuValue) {
		this.canshuValue = canshuValue;
	}
}
