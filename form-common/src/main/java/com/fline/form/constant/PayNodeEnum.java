/**
 * Project Name:com.fline.yztb.vo
 * File Name:PayNodeEnum.java
 * Package Name:com.fline.yztb.constant
 * Date:2019年8月15日上午10:12:30
 * Copyright (c) 2019, www.windo-soft.com All Rights Reserved.
 *
*/

package com.fline.form.constant;
/**
 * ClassName:PayNodeEnum 
 * Function: 支付生命周期. 
 * Reason:	 TODO ADD REASON. 
 * Date:     2019年8月15日 上午10:12:30
 * @author   邵炜
 * @version  
 * @see 	 
 */
public enum PayNodeEnum {

	GET_PAYMESG(1, "获取缴款单信息"),
	
	CALLBACK_PAYMESG(2, "统一支付回调获取缴款信息"),
	
	APPECT_PAYRESULT(3, "接受缴费结果信息"),
	
	GET_REFUND(4, "获取退款的信息"),
	
	REFUND_PAYMSG(5, "退费结果信息接受"),
	
	GTE_MOREPAYMESG(6, "获取批量的缴款单信息");
	
	private int value;

    private String name;

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

	PayNodeEnum(int value, String name) {
		this.value = value;
		this.name = name;
	}
    
}

