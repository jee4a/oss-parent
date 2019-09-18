package com.jee4a.oss.framework;

public class BaseModel implements java.io.Serializable{

    private static final long serialVersionUID = -4896585239547328361L;

	public String startDate;
	public String endDate;
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
}

