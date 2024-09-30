package com.game.controller.structs;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 返回前端数据对象
 *
 * @author zhangzhen
 * @version 1.0
 * @time 2024/8/13 16:10
 */
@Getter
@Setter
public class ResponseBean<T> implements Serializable {

    private int code;

    private String message;

    private int rcount;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private TotalAmountBean totalAmount;

    /**
     * 该注解是不参与序列化，为null的时候不传递给前台
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;

    public ResponseBean() {

    }

    public ResponseBean(int code, String message, TotalAmountBean totalAmount, T data) {
        setCode(code);
        setMessage(message);
        setTotalAmount(totalAmount);
        setData(data);
    }

    public ResponseBean(int code, String message, T data) {
        setCode(code);
        setMessage(message);
        setData(data);
    }

    public ResponseBean(int code, String message) {
        setCode(code);
        setMessage(message);
    }

    public ResponseBean(ResponseCodeEnum code) {
        setCode(code.getCode());
        setMessage(code.getMessage());
    }

    public ResponseBean(ResponseCodeEnum code, T data) {
        setCode(code.getCode());
        setMessage(code.getMessage());
        setData(data);
    }

    public ResponseBean(ResponseCodeEnum code, TotalAmountBean totalAmount, T data) {
        setCode(code.getCode());
        setMessage(code.getMessage());
        setTotalAmount(totalAmount);
        setData(data);
    }

    public ResponseBean(ResponseCodeEnum code, int size, T data) {
        setCode(code.getCode());
        setMessage(code.getMessage());
        setRcount(size);
        setData(data);
    }

    /**
     * 操作成功
     *
     * @return
     */
    public static <T> ResponseBean<T> success() {
        return new ResponseBean<T>(ResponseCodeEnum.SUCCESS);
    }

    /**
     * 带提示信息的成功
     *
     * @return
     */
    public static <T> ResponseBean<T> success(String message) {
        return new ResponseBean<T>(ResponseCodeEnum.SUCCESS.getCode(), message);
    }

    /**
     * 操作成功（带返回数据和统计）
     *
     * @param data 需要返回的数据
     * @return
     */
    public static <T> ResponseBean<T> success(TotalAmountBean totalAmount, T data) {
        return new ResponseBean<>(ResponseCodeEnum.SUCCESS, totalAmount, data);
    }

    /**
     * 操作成功（带返回数据和统计）
     *
     * @param data 需要返回的数据
     * @return
     */
    public static <T> ResponseBean<T> success(Integer size, T data) {
        return new ResponseBean<>(ResponseCodeEnum.SUCCESS, size, data);
    }

    /**
     * 操作成功（带返回数据）
     *
     * @param data 需要返回的数据
     * @return
     */
    public static <T> ResponseBean<T> success(T data) {
        return new ResponseBean(ResponseCodeEnum.SUCCESS, data);
    }

    /**
     * 添加成功
     *
     * @return
     */
    public static <T> ResponseBean<T> addSuccess() {
        return new ResponseBean<>(ResponseCodeEnum.SUCCESS_ADD);
    }

    /**
     * 添加成功（带返回数据）
     *
     * @param data 需要返回的数据
     * @return
     */
    public static <T> ResponseBean<T> addSuccess(T data) {
        return new ResponseBean<>(ResponseCodeEnum.SUCCESS_ADD, data);
    }

    /**
     * 更新成功
     *
     * @return
     */
    public static <T> ResponseBean<T> updateSuccess() {
        return new ResponseBean<>(ResponseCodeEnum.SUCCESS_UPDATE);
    }

    /**
     * 更新成功（带返回数据）
     *
     * @param data 需要返回的数据
     * @return
     */
    public static <T> ResponseBean<T> updateSuccess(T data) {
        return new ResponseBean<>(ResponseCodeEnum.SUCCESS_UPDATE, data);
    }

    /**
     * 删除成功
     *
     * @return
     */
    public static <T> ResponseBean<T> deleteSuccess() {
        return new ResponseBean<>(ResponseCodeEnum.SUCCESS_DELETE);
    }

    public static <T> ResponseBean<T> deleteSuccess(T data) {
        return new ResponseBean<>(ResponseCodeEnum.SUCCESS_DELETE, data);
    }

    /**
     * 失败
     *
     * @return
     */
    public static <T> ResponseBean<T> fail() {
        return new ResponseBean<>(ResponseCodeEnum.Fail);
    }

    /**
     * 失败
     *
     * @return
     */
    public static <T> ResponseBean<T> fail(String message) {
        return new ResponseBean<>(ResponseCodeEnum.Fail.getCode(), message);
    }

    /**
     * 失败
     *
     * @param code
     * @return
     */
    public static <T> ResponseBean<T> fail(ResponseCodeEnum code) {
        return new ResponseBean<>(code.getCode(), code.getMessage());
    }

    /**
     * 失败
     *
     * @param code
     * @param message
     * @return
     */
    public static <T> ResponseBean<T> fail(ResponseCodeEnum code, String message) {
        return new ResponseBean<>(code.getCode(), message);
    }

    /**
     * 失败
     *
     * @param code
     * @param message
     * @return
     */
    public static <T> ResponseBean<T> fail(int code, String message) {
        return new ResponseBean<>(code, message);
    }

    /**
     * 添加失败
     *
     * @return
     */
    public static <T> ResponseBean<T> addFail() {
        return new ResponseBean<T>(ResponseCodeEnum.Fail_ADD);
    }

    /**
     * 更新失败
     *
     * @return
     */
    public static <T> ResponseBean<T> updateFail() {
        return new ResponseBean<>(ResponseCodeEnum.Fail_UPDATE);
    }

    /**
     * 删除失败
     *
     * @return
     */
    public static <T> ResponseBean<T> deleteFail() {
        return new ResponseBean<>(ResponseCodeEnum.Fail_DELETE);
    }

}
