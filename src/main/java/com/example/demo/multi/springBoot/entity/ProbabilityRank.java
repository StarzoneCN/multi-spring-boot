package com.example.demo.multi.springBoot.entity;

import java.io.Serializable;

import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 概率排序
 * </p>
 *
 * @author LiHongxing
 * @since 2018-08-08
 */
@TableName("probability_rank")
public class ProbabilityRank extends Model<ProbabilityRank> {

    private static final long serialVersionUID = 1L;

    @TableId("ssq_id")
	private String ssqId;
    /**
     * 号码开出次数
     */
	@TableField("times_code_opened")
	private String timesCodeOpened;
    /**
     * 按号码排序（如：号码1：排第20）
     */
	@TableField("code_sort")
	private String codeSort;
    /**
     * 按概率排序（如：概率第20：号码1）
     */
	private String ranking;
    /**
     * open的号码排序
     */
	@TableField("open_code_sort")
	private String openCodeSort;
    /**
     * 红色号码排序之和，如：6-13-21-22-26-27+12， sortSum = 6 + 13 + 21 + 22 + 26 + 27
     */
	@TableField("sort_sum")
	private Integer sortSum;
	@TableField("update_time")
	private Date updateTime;


	public String getSsqId() {
		return ssqId;
	}

	public void setSsqId(String ssqId) {
		this.ssqId = ssqId;
	}

	public String getTimesCodeOpened() {
		return timesCodeOpened;
	}

	public void setTimesCodeOpened(String timesCodeOpened) {
		this.timesCodeOpened = timesCodeOpened;
	}

	public String getCodeSort() {
		return codeSort;
	}

	public void setCodeSort(String codeSort) {
		this.codeSort = codeSort;
	}

	public String getRanking() {
		return ranking;
	}

	public void setRanking(String ranking) {
		this.ranking = ranking;
	}

	public String getOpenCodeSort() {
		return openCodeSort;
	}

	public void setOpenCodeSort(String openCodeSort) {
		this.openCodeSort = openCodeSort;
	}

	public Integer getSortSum() {
		return sortSum;
	}

	public void setSortSum(Integer sortSum) {
		this.sortSum = sortSum;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	@Override
	protected Serializable pkVal() {
		return this.ssqId;
	}

	@Override
	public String toString() {
		return "ProbabilityRank{" +
			", ssqId=" + ssqId +
			", timesCodeOpened=" + timesCodeOpened +
			", codeSort=" + codeSort +
			", ranking=" + ranking +
			", openCodeSort=" + openCodeSort +
			", sortSum=" + sortSum +
			", updateTime=" + updateTime +
			"}";
	}
}
