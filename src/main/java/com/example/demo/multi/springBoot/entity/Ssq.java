package com.example.demo.multi.springBoot.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.activerecord.Model;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author hongxing
 * @since 2018-04-16
 */
public class Ssq extends Model<Ssq> {

    private static final long serialVersionUID = 1L;

	private String id;
	private Integer r0;
	private Integer r1;
	private Integer r2;
	private Integer r3;
	private Integer r4;
	private Integer r5;
	private Integer b0;
	private String str;


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getR0() {
		return r0;
	}

	public void setR0(Integer r0) {
		this.r0 = r0;
	}

	public Integer getR1() {
		return r1;
	}

	public void setR1(Integer r1) {
		this.r1 = r1;
	}

	public Integer getR2() {
		return r2;
	}

	public void setR2(Integer r2) {
		this.r2 = r2;
	}

	public Integer getR3() {
		return r3;
	}

	public void setR3(Integer r3) {
		this.r3 = r3;
	}

	public Integer getR4() {
		return r4;
	}

	public void setR4(Integer r4) {
		this.r4 = r4;
	}

	public Integer getR5() {
		return r5;
	}

	public void setR5(Integer r5) {
		this.r5 = r5;
	}

	public Integer getB0() {
		return b0;
	}

	public void setB0(Integer b0) {
		this.b0 = b0;
	}

	public String getStr() {
		return str;
	}

	public void setStr(String str) {
		this.str = str;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "Ssq{" +
			", id=" + id +
			", r0=" + r0 +
			", r1=" + r1 +
			", r2=" + r2 +
			", r3=" + r3 +
			", r4=" + r4 +
			", r5=" + r5 +
			", b0=" + b0 +
			", str=" + str +
			"}";
	}
}
