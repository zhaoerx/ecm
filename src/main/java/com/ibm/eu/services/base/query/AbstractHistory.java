package com.ibm.eu.services.base.query;

import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * 历史流程实例和历史流程任务的基类。
 */
public abstract class AbstractHistory {
    private String workflowNumber;
    private String workflowName;
    private String workClassName;
    private String originator;
    private long originatorId;
    private String subject;
    private Date startTime;
    private String displayStepName;
	private String createdUnit;
	private String fileCode;
	private int urgencyLevel;
	private String distributeComment;//批分意见
	private String approveComment;//批示意见
    private String submitTo;//主送
    private String copyTo;//抄送
    private String displayApprover;//签发人
	
	public int getUrgencyLevel() {
		return urgencyLevel;
	}
	public void setUrgencyLevel(int urgencyLevel) {
		this.urgencyLevel = urgencyLevel;
	}
	public String getFileCode() {
		return fileCode;
	}
	public void setFileCode(String fileCode) {
		this.fileCode = fileCode;
	}
	public String getCreatedUnit() {
		return createdUnit;
	}
	public void setCreatedUnit(String createdUnit) {
		this.createdUnit = createdUnit;
	}
	public String getPreviousUser() {
		return PreviousUser;
	}
	public void setPreviousUser(String previousUser) {
		PreviousUser = previousUser;
	}

	private String PreviousUser;
    
    

	/**
     * PE DataField in base process: FormID
     */
    private String formId;

    public String getWorkflowNumber() {
        return workflowNumber;
    }
    public void setWorkflowNumber(String workflowNumber) {
        this.workflowNumber = workflowNumber;
    }
    public String getWorkflowName() {
        return workflowName;
    }
    public void setWorkflowName(String workflowName) {
        this.workflowName = workflowName;
    }
    public String getWorkClassName() {
        return workClassName;
    }
    public void setWorkClassName(String workClassName) {
        this.workClassName = workClassName;
    }
    public String getOriginator() {
        return originator;
    }
    public void setOriginator(String originator) {
        this.originator = originator;
    }
    public long getOriginatorId() {
        return originatorId;
    }
    public void setOriginatorId(long originatorId) {
        this.originatorId = originatorId;
    }
    public String getSubject() {
        return subject;
    }
    public void setSubject(String subject) {
        this.subject = subject;
    }
    public Date getStartTime() {
        return startTime;
    }
    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }
    public String getFormId() {
        return formId;
    }
    public void setFormId(String formId) {
        this.formId = formId;
    }
    
    public String getDisplayStepName() {
		return displayStepName;
	}
	public void setDisplayStepName(String displayStepName) {
		this.displayStepName = displayStepName;
	}
    
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
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
