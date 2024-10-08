package com.game.data.bean;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * z_server_agent Bean
 */
@Document(collection = "z_server_agent")
public class B_z_server_agent_Bean {
	/**
	 * id
	 */
	@Id
	private Integer id;
	/**
	 * 配置名字
	 */
	private String agentName;
	/**
	 * sdkId
	 */
	private Integer sdkId;
	/**
	 * sdk名字
	 */
	private String sdkName;
	/**
	 * 平台id
	 */
	private Integer pfId;
	/**
	 * 平台名字
	 */
	private String pfName;
	/**
	 * AppId
	 */
	private String appId;
	/**
	 * app名字
	 */
	private String appName;
	/**
	 * sdk登录地址
	 */
	private String loginUrl;
	/**
	 * sdk充值回调地址
	 */
	private String backUrl;
	/**
	 * 参数1
	 */
	private String data1;
	/**
	 * 参数2
	 */
	private String data2;
	/**
	 * 参数3
	 */
	private String data3;
	/**
	 * 参数4
	 */
	private String data4;
	/**
	 * 参数5
	 */
	private String data5;
	/**
	 * 参数6
	 */
	private String data6;
	/**
	 * 参数7
	 */
	private String data7;
	/**
	 * 参数8
	 */
	private String data8;
	/**
	 * 参数9
	 */
	private String data9;
	/**
	 * 参数10
	 */
	private String data10;

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAgentName() {
		return this.agentName;
	}

	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}

	public Integer getSdkId() {
		return this.sdkId;
	}

	public void setSdkId(Integer sdkId) {
		this.sdkId = sdkId;
	}

	public String getSdkName() {
		return this.sdkName;
	}

	public void setSdkName(String sdkName) {
		this.sdkName = sdkName;
	}

	public Integer getPfId() {
		return this.pfId;
	}

	public void setPfId(Integer pfId) {
		this.pfId = pfId;
	}

	public String getPfName() {
		return this.pfName;
	}

	public void setPfName(String pfName) {
		this.pfName = pfName;
	}

	public String getAppId() {
		return this.appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getAppName() {
		return this.appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getLoginUrl() {
		return this.loginUrl;
	}

	public void setLoginUrl(String loginUrl) {
		this.loginUrl = loginUrl;
	}

	public String getBackUrl() {
		return this.backUrl;
	}

	public void setBackUrl(String backUrl) {
		this.backUrl = backUrl;
	}

	public String getData1() {
		return this.data1;
	}

	public void setData1(String data1) {
		this.data1 = data1;
	}

	public String getData2() {
		return this.data2;
	}

	public void setData2(String data2) {
		this.data2 = data2;
	}

	public String getData3() {
		return this.data3;
	}

	public void setData3(String data3) {
		this.data3 = data3;
	}

	public String getData4() {
		return this.data4;
	}

	public void setData4(String data4) {
		this.data4 = data4;
	}

	public String getData5() {
		return this.data5;
	}

	public void setData5(String data5) {
		this.data5 = data5;
	}

	public String getData6() {
		return this.data6;
	}

	public void setData6(String data6) {
		this.data6 = data6;
	}

	public String getData7() {
		return this.data7;
	}

	public void setData7(String data7) {
		this.data7 = data7;
	}

	public String getData8() {
		return this.data8;
	}

	public void setData8(String data8) {
		this.data8 = data8;
	}

	public String getData9() {
		return this.data9;
	}

	public void setData9(String data9) {
		this.data9 = data9;
	}

	public String getData10() {
		return this.data10;
	}

	public void setData10(String data10) {
		this.data10 = data10;
	}


}