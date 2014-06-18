package com.ibm.eu.services.base.query;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 描述单个历史流程任务的实体类。
 */
public class HistoricalWorkItem extends AbstractHistory
        implements Comparable<HistoricalWorkItem> {
    
    private int sequenceNumber;
    private int workOrderId;
    private int eventType;
    
    private String wobNum;
    private String stepName;
    private String boundUser;
    private long boundUserId;
    private String user;
    private long userId;
    private String comment;
    private String selectedResponse;
    
    private Date enqueueTime;
    private Date timestamp;
    private long stepDuration;
    
    private String roleName;

	private int wPClassId;
	
	private String createdUnit;
	
	private String PreviousUser;
	
	private String fileCode;
	
	private boolean reassignByAdmin;
	private String distributeComment;//批分意见
	private String approveComment;//批示意见
    private String submitTo;//主送
    private String copyTo;//抄送
    private String displayApprover;//签发人
	
	public boolean isReassignByAdmin() {
		return reassignByAdmin;
	}
	public void setReassignByAdmin(boolean reassignByAdmin) {
		this.reassignByAdmin = reassignByAdmin;
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
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
    public int getWPClassId() {
		return wPClassId;
	}
	public void setWPClassId(int wPClassId) {
		this.wPClassId = wPClassId;
	}

	private Map<String, Object> dataFields = new HashMap<String, Object>();
    
    public int getSequenceNumber() {
        return sequenceNumber;
    }
    public void setSequenceNumber(int sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
    }
    public int getWorkOrderId() {
        return workOrderId;
    }
    public void setWorkOrderId(int workOrderId) {
        this.workOrderId = workOrderId;
    }
    public int getEventType() {
        return eventType;
    }
    public void setEventType(int eventType) {
        this.eventType = eventType;
    }
    public String getWobNum() {
        return wobNum;
    }
    public void setWobNum(String wobNum) {
        this.wobNum = wobNum;
    }
    public String getStepName() {
        return stepName;
    }
    public void setStepName(String stepName) {
        this.stepName = stepName;
    }
    public String getBoundUser() {
        return boundUser;
    }
    public void setBoundUser(String boundUser) {
        this.boundUser = boundUser;
    }
    public String getUser() {
        return user;
    }
    public void setUser(String user) {
        this.user = user;
    }
    public long getBoundUserId() {
        return boundUserId;
    }
    public void setBoundUserId(long boundUserId) {
        this.boundUserId = boundUserId;
    }
    public long getUserId() {
        return userId;
    }
    public void setUserId(long userId) {
        this.userId = userId;
    }
    public String getComment() {
        return comment;
    }
    public void setComment(String comment) {
        this.comment = comment;
    }
    public String getSelectedResponse() {
        return selectedResponse;
    }
    public void setSelectedResponse(String selectedResponse) {
        this.selectedResponse = selectedResponse;
    }
    public Date getEnqueueTime() {
        return enqueueTime;
    }
    public void setEnqueueTime(Date enqueueTime) {
        this.enqueueTime = enqueueTime;
    }
    public Date getTimestamp() {
        return timestamp;
    }
    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
    public long getStepDuration() {
        return stepDuration;
    }
    public void setStepDuration(long stepDuration) {
        this.stepDuration = stepDuration;
    }
    
    public Object putFieldValue(String name, Object value) {
        return dataFields.put(name, value);
    }
    
    public Object getFieldValue(String name) {
        return dataFields.get(name);
    }
    
    public Set<String> getFieldNames() {
        return dataFields.keySet();
    }
    
    public int compareTo(HistoricalWorkItem o) {
        return o.sequenceNumber-sequenceNumber;
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
