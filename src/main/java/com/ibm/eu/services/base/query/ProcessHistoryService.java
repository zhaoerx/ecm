package com.ibm.eu.services.base.query;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.ibm.eu.constants.P8BpmConstants;
import com.ibm.eu.services.base.query.builder.ProcessQueryBuilder;

import filenet.vw.api.VWException;
import filenet.vw.api.VWLog;
import filenet.vw.api.VWLogElement;
import filenet.vw.api.VWLogQuery;
import filenet.vw.api.VWParticipant;
import filenet.vw.api.VWQueue;
import filenet.vw.api.VWSession;

/**
 * 单流程实例的历史查询服务
 */
public class ProcessHistoryService {
    
    protected static final String ERROR_CODE = "ERR1511";
    
    protected final Logger logger = Logger.getLogger(getClass());
    private boolean isNonN1ECMClericalStaff = false;
    private String userId = "";
    
    /**
     * 根据流程号
     * @param vwSession
     * @param workflowNumber
     * @return
     * @throws VWException 
     * @throws SystemException 
     */
    public Set<String> fetchWorkflowParticipants(VWSession vwSession, 
            String workflowNumber) throws VWException  {
    	Set<String> participants = new HashSet<String>();
    	HistoricalWorkflow hwf = fetchWorkflow(vwSession, P8BpmConstants.DEFAULT_EVENT_LOG_NAME, workflowNumber);
    	for(HistoricalWorkItem item: hwf.getWorkItems()) {
    		if(StringUtils.isNotEmpty(item.getUser()))
    		participants.add(item.getUser());
    	}
    	return participants;
    }
    
    /**
     * 获取流程是否结束。
     * 
     * @param workflowNumber 流程实例编号
     * @return 流程历史
     * @throws VWException
     */
    public boolean isProcessFinished(VWSession vwSession, 
            String workflowNumber)
            throws VWException {
        ProcessQueryBuilder builder = new ProcessQueryBuilder();
        builder.put(P8BpmConstants.PE_SYSTEM_FIELD_WORKFLOW_NUMBER, workflowNumber);
        builder.put(P8BpmConstants.PE_SYSTEM_FIELD_EVENT_TYPE, P8BpmConstants.LOG_TYPE_PROCESS_FINISH);
        Query query = builder.buildQuery();
        
        VWLog eventLog = vwSession.fetchEventLog(P8BpmConstants.DEFAULT_EVENT_LOG_NAME);
        //eventLog.setBufferSize(pageSize);
        VWLogQuery logQuery = 
            eventLog.startQuery(P8BpmConstants.PE_LOG_INDEX_WORKFLOW_NUMBER, 
                    null, null, VWQueue.QUERY_NO_OPTIONS, 
                    query.getFilter(), query.getSubstitutionVars());
        if(logQuery.hasNext()) {
            return true;
        }
        return false;
    }

    /**
     * 获取历史流程。
     * 
     * @param eventLogName 目标EventLog名称
     * @param workflowNumber 流程实例编号
     * @param dataFieldNames 结果集里要包含的自定义字段名称，可有多个
     * @return 流程历史
     * @throws VWException
     */
    public HistoricalWorkflow fetchWorkflow(VWSession vwSession, 
            String eventLogName, String workflowNumber, String... dataFieldNames)
            throws VWException {
        ProcessQueryBuilder builder = new ProcessQueryBuilder();
        builder.put(P8BpmConstants.PE_SYSTEM_FIELD_WORKFLOW_NUMBER, workflowNumber);
        builder.put(P8BpmConstants.PE_SYSTEM_FIELD_EVENT_TYPE, new int[] {
                P8BpmConstants.LOG_TYPE_LAUNCH_STEP_FINISH,
                P8BpmConstants.LOG_TYPE_STEP_QUEUED,
                P8BpmConstants.LOG_TYPE_STEP_FINISH,
                P8BpmConstants.LOG_TYPE_REASSIGN,
                P8BpmConstants.LOG_TYPE_DELEGATE,
                P8BpmConstants.LOG_TYPE_DELEGATE_RETURN,
                P8BpmConstants.LOG_TYPE_FORCED_TO_TERMINATE,
                P8BpmConstants.LOG_TYPE_PROCESS_FINISH });
        Query query = builder.buildQuery();
        
        List<HistoricalWorkItem> hwis = new ArrayList<HistoricalWorkItem>();
        
        VWLog eventLog = vwSession.fetchEventLog(eventLogName);
        //eventLog.setBufferSize(pageSize);
        VWLogQuery logQuery = 
            eventLog.startQuery(P8BpmConstants.PE_LOG_INDEX_WORKFLOW_NUMBER, 
                    null, null, VWQueue.QUERY_NO_OPTIONS, 
                    query.getFilter(), query.getSubstitutionVars());
        while (logQuery.hasNext()) {
            VWLogElement le = logQuery.next();
            boolean filterHistory = false;
            int boundUserId = -1;
            if(StringUtils.isNotBlank(userId)) {
            	boundUserId = vwSession.convertUserNameToId(userId);
            }
            if(this.isNonN1ECMClericalStaff() && //本部系统非N1ECM文书登录
            		(((Integer)le.getFieldValue(P8BpmConstants.PE_SYSTEM_FIELD_BOUND_USER_ID) != 0
            			|| (Integer)le.getFieldValue(P8BpmConstants.PE_SYSTEM_FIELD_USER_ID) != boundUserId) // 启动流程
            			&& ((Integer)le.getFieldValue(P8BpmConstants.PE_USER_FIELD_DELEGATORID) !=  boundUserId
            			|| le.getEventType() == P8BpmConstants.LOG_TYPE_STEP_QUEUED))) { //非N1ECM的委办
            	filterHistory = true;
            }
            if(!filterHistory) {
            	hwis.add(ProcessQueryUtil.getHistoricalWorkItem(le, vwSession, dataFieldNames));
            }
        }
        
        HistoricalWorkflow hwf = ProcessQueryUtil.getHistoricalWorkflow(hwis); 
        filterEvents(hwf);
        
        return hwf;
    }
    
    /**
     * 打开单个历史流程任务项目。
     * 
     * @param eventLogName 目标EventLog名称
     * @param sequenceNumber 流程任务顺序号
     * @param dataFieldNames 结果集里要包含的自定义字段名称，可有多个
     * @return 历史流程任务，null则为没找到
     * @throws VWException
     */
    public HistoricalWorkItem fetchWorkItem(VWSession vwSession, 
            String eventLogName, int sequenceNumber, String... dataFieldNames) 
            throws VWException {
        HistoricalWorkItem hwi = null;
        
        ProcessQueryBuilder builder = new ProcessQueryBuilder();
        builder.put(P8BpmConstants.PE_SYSTEM_FIELD_EVENT_TYPE, new int[] {
                P8BpmConstants.LOG_TYPE_LAUNCH_STEP_FINISH,
                P8BpmConstants.LOG_TYPE_STEP_FINISH,
                P8BpmConstants.LOG_TYPE_REASSIGN,
                P8BpmConstants.LOG_TYPE_DELEGATE,
                P8BpmConstants.LOG_TYPE_DELEGATE_RETURN,
                P8BpmConstants.LOG_TYPE_FORCED_TO_TERMINATE,
                P8BpmConstants.LOG_TYPE_PROCESS_FINISH });
        builder.put(P8BpmConstants.PE_SYSTEM_FIELD_SEQUENCE_NUMBER, sequenceNumber);
        Query query = builder.buildQuery();
            VWLog eventLog = vwSession.fetchEventLog(eventLogName);
            VWLogQuery logQuery = 
                eventLog.startQuery(null, null, null, VWQueue.QUERY_NO_OPTIONS, 
                        query.getFilter(), query.getSubstitutionVars());
            // Single result
            if (logQuery.hasNext()) {
                VWLogElement le = logQuery.next();
                hwi = ProcessQueryUtil.getHistoricalWorkItem(le, vwSession, dataFieldNames);
            }

        return hwi;
    }
    
    
    /**
     * 根据文件代码获取历史流程。
     * 
     * @param eventLogName 目标EventLog名称
     * @param recordCode 文件编码
     * @param dataFieldNames 结果集里要包含的自定义字段名称，可有多个
     * @return 流程历史
     * @throws VWException
     */
    public HistoricalWorkflow fetchWorkflowByRecordCode(VWSession vwSession, 
            String eventLogName, String recordCode, String... dataFieldNames)
            throws VWException {
        ProcessQueryBuilder builder = new ProcessQueryBuilder();
        builder.put(P8BpmConstants.PE_USER_FIELD_RECORD_CODE, recordCode);
        builder.put(P8BpmConstants.PE_SYSTEM_FIELD_EVENT_TYPE, new int[] {
                P8BpmConstants.LOG_TYPE_LAUNCH_STEP_FINISH,
                P8BpmConstants.LOG_TYPE_STEP_QUEUED,
                P8BpmConstants.LOG_TYPE_STEP_FINISH,
                P8BpmConstants.LOG_TYPE_REASSIGN,
                P8BpmConstants.LOG_TYPE_DELEGATE,
                P8BpmConstants.LOG_TYPE_DELEGATE_RETURN,
                P8BpmConstants.LOG_TYPE_FORCED_TO_TERMINATE,
                P8BpmConstants.LOG_TYPE_PROCESS_FINISH });
        Query query = builder.buildQuery();
        
        List<HistoricalWorkItem> hwis = new ArrayList<HistoricalWorkItem>();
        
        VWLog eventLog = vwSession.fetchEventLog(eventLogName);
        //eventLog.setBufferSize(pageSize);
        VWLogQuery logQuery = 
            eventLog.startQuery(P8BpmConstants.PE_LOG_INDEX_WORKFLOW_NUMBER, 
                    null, null, VWQueue.QUERY_NO_OPTIONS, 
                    query.getFilter(), query.getSubstitutionVars());
        while (logQuery.hasNext()) {
            VWLogElement le = logQuery.next();
            hwis.add(ProcessQueryUtil.getHistoricalWorkItem(le, vwSession, dataFieldNames));
        }
        
        HistoricalWorkflow hwf = ProcessQueryUtil.getHistoricalWorkflow(hwis); 
        filterEvents(hwf);
        
        return hwf;
    }
    
    /**
     * 在历史流程中过滤Event类型为<code>P8BpmConstants.LOG_TYPE_STEP_QUEUED</code>
     * 的历史任务，对于未完成的流程仅保留最新的一个，对于已完成的流程则全部移除。
     * 
     * @param hwis 要过滤的历史流程，认为其中的历史任务列表已经按时间降序排列。
     */
	private void filterEvents(HistoricalWorkflow hwf) {
		List<HistoricalWorkItem> hwis = hwf.getWorkItems();
		boolean isFinished = hwf.isFinished();
		boolean isFirstItem = true;
		int currentWorkOrderId = -1;
		List<String> finishedWob = new ArrayList<String>();
		List<HistoricalWorkItem> currentStepQueuedItems = new ArrayList<HistoricalWorkItem>();

		for (Iterator<HistoricalWorkItem> iter = hwis.iterator(); iter
				.hasNext();) {
			HistoricalWorkItem hwi = iter.next();
			// filter Component step events and delay queue events(multiple participates)
			if (hwi.getWPClassId() == P8BpmConstants.WP_CLASS_CE_OPERATION || hwi.getWPClassId() == P8BpmConstants.WP_CLASS_DELAY) {
				iter.remove();
				continue;
			}
			if(isFinished) {
				// 流程已结束，去掉所有Queued的Event并调整最后一步处理人
				if(hwi.getEventType() == P8BpmConstants.LOG_TYPE_STEP_QUEUED) {
					iter.remove();
					continue;
				} else if (hwi.getEventType() == P8BpmConstants.LOG_TYPE_PROCESS_FINISH) {
						// adjust last step's user Id
						if (hwi.getStepName() == null) {
							adjustLastStep(hwi);
						}
					}
			} else {
				//流程未结束，去掉已完成步骤的Queued Event并调整会签步骤时的显示顺序
				if (isFirstItem) {
					isFirstItem = false;
					currentWorkOrderId = hwi.getWorkOrderId();
				}
				
				
				if ((hwi.getEventType() == P8BpmConstants.LOG_TYPE_STEP_FINISH || 
						hwi.getEventType() == P8BpmConstants.LOG_TYPE_REASSIGN || 
						hwi.getEventType() == P8BpmConstants.LOG_TYPE_DELEGATE || 
						hwi.getEventType() == P8BpmConstants.LOG_TYPE_FORCED_TO_TERMINATE || 
						hwi.getEventType() == P8BpmConstants.LOG_TYPE_DELEGATE_RETURN
						)
						&& (hwi.getWorkOrderId() == currentWorkOrderId
								)){
					finishedWob.add(hwi.getWobNum());
				}
			}
		}
		// 加回移掉的当前步骤的Queued Item，以保证当前步骤是会签时未完成的任务显示在前
		if(!currentStepQueuedItems.isEmpty()) {
			hwis.addAll(0, currentStepQueuedItems);
		}
	}
	
    /**
     * 为Event类型为<code>P8BpmConstants.LOG_TYPE_STEP_QUEUED</code>的历史任务修
     * 正属性。因为任务还没有被处理，这里的：
     * <ul>
     * <li>处理者应为接受任务者。</li>
     * </ul>
     */
    private void adjustCurrentStep(HistoricalWorkItem hwi) {
    	if(hwi.getWPClassId() == P8BpmConstants.WP_CLASS_INBOX) {
    		hwi.setUser(hwi.getBoundUser());
    		hwi.setUserId(hwi.getBoundUserId());
    	}
    	
    	hwi.setEnqueueTime(hwi.getTimestamp());
    	hwi.setComment("");
    }
    
    /**
     * 为Event类型为<code>P8BpmConstants.LOG_TYPE_PROCESS_FINISH</code>的历史任务修
     * 正属性。因为任务由系统用户处理，这里的：
     * <ul>
     * <li>处理者应为空。</li>
     * </ul>
     */
    private void adjustLastStep(HistoricalWorkItem hwi) {
        hwi.setUser("");
        hwi.setUserId(VWParticipant.PARTICIPANT_TYPE_UNDEFINED);
        hwi.setComment("");
    }

	public boolean isNonN1ECMClericalStaff() {
		return isNonN1ECMClericalStaff;
	}

	public void setNonN1ECMClericalStaff(boolean isNonN1ECMClericalStaff) {
		this.isNonN1ECMClericalStaff = isNonN1ECMClericalStaff;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
    
}
