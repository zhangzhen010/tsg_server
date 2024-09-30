package com.game.controller.structs;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 查询列表金额统计对象
 *
 * @author djq
 * @version 1.0
 * @date 2023/5/17 17:48
 */
@Data
public class TotalAmountBean {

    private BigDecimal incomeAmount;
    private BigDecimal expendAmount;
    private BigDecimal numBalance;
}