package com.ibm.eu.services.base.query;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 描述历史流程实例的实体类。
 */
public class HistoricalWorkflow extends AbstractHistory
        implements Comparable<HistoricalWorkflow> {
    
    private boolean finished = false;
    private Date lastUpdateTime;
    

	private String wobNum;	 //WLG 查找"我的拟稿"中的保存流程
    private String stepName;	 //WLG 查找"我的拟稿"中的保存流程
    private long handID;	//WLG 搜索时，为传阅任务的handID赋值
    private String distributeComment;//批分意见
	private String approveComment;//批示意见
    private String submitTo;//主送
    private String copyTo;//抄送
    private String displayApprover;//签发人
    
	public long getHandID() {
		return handID;
	}
	public void setHandID(long handID) {
		this.handID = handID;
	}

	private List<HistoricalWorkItem> workItems = new ArrayList<HistoricalWorkItem>();
    
    public String getStepName() {
		return stepName;
	}
	public void setStepName(String stepName) {
		this.stepName = stepName;
	}
    public String getWobNum() {
		return wobNum;
	}
	public void setWobNum(String wobNum) {
		this.wobNum = wobNum;
	}
	public boolean isFinished() {
        return finished;
    }
    public void setFinished(boolean finished) {
        this.finished = finished;
    }
    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }
    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }
    
    public List<HistoricalWorkItem> getWorkItems() {
        return workItems;
    }
	
	public int compareTo(HistoricalWorkflow o) {
        if (lastUpdateTime.before(o.lastUpdateTime)) {
            return 1;
        } else if (lastUpdateTime.after(o.lastUpdateTime)) {
            return -1;
        }
        return 0;
    }
	public String getDistributeComment() {
		return distributeComment;
	}
	public void setDistributeComment(String distributeComment) {
		this.distributeComment = distributeComment;
	}
	public String getApproveComment() {
		return approveComment;
	}
	public void setApproveComment(String approveComment) {
		this.approveComment = approveComment;
	}
	public String getSubmitTo() {
		return submitTo;
	}
	public void setSubmitTo(String submitTo) {
		this.submitTo = submitTo;
	}
	public String getCopyTo() {
		return copyTo;
	}
	public void setCopyTo(String copyTo) {
		this.copyTo = copyTo;
	}
	public String getDisplayApprover() {
		return displayApprover;
	}
	public void setDisplayApprover(String displayApprover) {
		this.displayApprover = displayApprover;
	}
}
