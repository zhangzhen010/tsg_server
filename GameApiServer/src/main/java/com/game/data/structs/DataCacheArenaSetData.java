package com.game.data.structs;

import com.game.bean.structs.LongPair;

import java.util.List;

/**
 * @author lechunlin
 * @create 2023-04-18 10:41
 * @description "ArenaSet配置解析数据"
*/

public class DataCacheArenaSetData {
	/**
	 * 竞技场开放时间段
	 */
	private List<LongPair> seasonOpenTimeList;
	/**
	 * 胜利加分
	 */
	private int winScore;
	/**
	 * 失败扣分
	 */
	private int loseScore;
	/**
	 * 碾压条件：血量万分比大于等于
	 */
	private int rollingHpCondition;
	/**
	 * 碾压条件：回合数小雨等于
	 */
	private int rollingRoundCondition;
	/**
	 * 碾压条件：大段差小于等于
	 */
	private int rollingLevelCondition;
	/**
	 * 连胜加分 当连续胜利场次大于等于2时，添加连胜积分
	 * 连胜积分 = min【连胜上限分，连续胜利场次*连胜积分】
	 * 例如连胜2场 连胜积分 = min【40，2*2】 = 4
	 */
	private int winStreakScore;
	/**
	 * 连胜最大加分
	 */
	private int winStreakMaxScore;
	/**
	 * 连胜连败隐藏分变化
	 */
	private List<Integer> innerScoreChangeList;
	/**
	 * 强敌加分 当战败时，敌方大段ID大于玩家大段ID时，添加强敌积分
	 * 强敌积分 = min【强敌积分上限，(敌对大段ID - 我方大段ID)*强敌积分】
	 * 例如敌方天星1（大段ID = 3)，我方新星3（大段ID = 1) 强敌积分 = min【30，（3-1）* 5】 = 10
	 */
	private int strongEnemyScore;
	/**
	 * 强敌最大加分
	 */
	private int strongEnemyMaxScore;
	/**
	 * 胜利添加宝箱积分
	 */
	private int winBoxPoint;
	/**
	 * 失败添加宝箱积分
	 */
	private int loseBoxPoint;

	public List<LongPair> getSeasonOpenTimeList() {
		return seasonOpenTimeList;
	}

	public void setSeasonOpenTimeList(List<LongPair> seasonOpenTimeList) {
		this.seasonOpenTimeList = seasonOpenTimeList;
	}

	public int getWinScore() {
		return winScore;
	}

	public void setWinScore(int winScore) {
		this.winScore = winScore;
	}

	public int getLoseScore() {
		return loseScore;
	}

	public void setLoseScore(int loseScore) {
		this.loseScore = loseScore;
	}

	public int getRollingHpCondition() {
		return rollingHpCondition;
	}

	public void setRollingHpCondition(int rollingHpCondition) {
		this.rollingHpCondition = rollingHpCondition;
	}

	public int getRollingRoundCondition() {
		return rollingRoundCondition;
	}

	public void setRollingRoundCondition(int rollingRoundCondition) {
		this.rollingRoundCondition = rollingRoundCondition;
	}

	public int getRollingLevelCondition() {
		return rollingLevelCondition;
	}

	public void setRollingLevelCondition(int rollingLevelCondition) {
		this.rollingLevelCondition = rollingLevelCondition;
	}

	public int getWinStreakScore() {
		return winStreakScore;
	}

	public void setWinStreakScore(int winStreakScore) {
		this.winStreakScore = winStreakScore;
	}

	public int getWinStreakMaxScore() {
		return winStreakMaxScore;
	}

	public void setWinStreakMaxScore(int winStreakMaxScore) {
		this.winStreakMaxScore = winStreakMaxScore;
	}

	public List<Integer> getInnerScoreChangeList() {
		return innerScoreChangeList;
	}

	public void setInnerScoreChangeList(List<Integer> innerScoreChangeList) {
		this.innerScoreChangeList = innerScoreChangeList;
	}

	public int getStrongEnemyScore() {
		return strongEnemyScore;
	}

	public void setStrongEnemyScore(int strongEnemyScore) {
		this.strongEnemyScore = strongEnemyScore;
	}

	public int getStrongEnemyMaxScore() {
		return strongEnemyMaxScore;
	}

	public void setStrongEnemyMaxScore(int strongEnemyMaxScore) {
		this.strongEnemyMaxScore = strongEnemyMaxScore;
	}

	public int getWinBoxPoint() {
		return winBoxPoint;
	}

	public void setWinBoxPoint(int winBoxPoint) {
		this.winBoxPoint = winBoxPoint;
	}

	public int getLoseBoxPoint() {
		return loseBoxPoint;
	}

	public void setLoseBoxPoint(int loseBoxPoint) {
		this.loseBoxPoint = loseBoxPoint;
	}
}
