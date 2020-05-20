package com.lemon.utils;

public class CommonResult {

    /** 业务状态码 */
    private int code;
    /** 业务信息 */
    private String message;
    /** 数据 */
    private Object data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    /**
     * 常用构造方法
     * @param resultStatus 结果枚举
     * @param data 数据
     */
    public CommonResult(ResultStatus resultStatus, Object data) {
        this.code = resultStatus.getCode();
        this.message = resultStatus.getMessage();
        this.data = data;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("CommonResult{");
        sb.append("code=").append(code);
        sb.append(", message='").append(message).append('\'');
        sb.append(", data=").append(data);
        sb.append('}');
        return sb.toString();
    }
}
