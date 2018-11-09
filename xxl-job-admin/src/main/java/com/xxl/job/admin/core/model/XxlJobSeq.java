package com.xxl.job.admin.core.model;

public class XxlJobSeq extends XxlJobBase<Integer> {
	private String seqName;
	private Integer currentVal;
	private Integer incrementVal;

	
	public XxlJobSeq() {
		super();
	}

	public XxlJobSeq(String seqName, Integer currentVal, Integer incrementVal) {
		super();
		this.seqName = seqName;
		this.currentVal = currentVal;
		this.incrementVal = incrementVal;
	}

	public String getSeqName() {
		return seqName;
	}

	public void setSeqName(String seqName) {
		this.seqName = seqName;
	}

	public Integer getCurrentVal() {
		return currentVal;
	}

	public void setCurrentVal(Integer currentVal) {
		this.currentVal = currentVal;
	}

	public Integer getIncrementVal() {
		return incrementVal;
	}

	public void setIncrementVal(Integer incrementVal) {
		this.incrementVal = incrementVal;
	}
	
	public Integer nextVal(){
		Integer newVal = this.currentVal + this.incrementVal;
		this.currentVal = newVal;
		return this.incrementVal;
	}

}
