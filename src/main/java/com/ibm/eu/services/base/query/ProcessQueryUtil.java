package com.ibm.eu.services.base.query;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.ibm.eu.constants.P8BpmConstants;

import filenet.vw.api.VWException;
import filenet.vw.api.VWLogElement;
import filenet.vw.api.VWSession;

public class ProcessQueryUtil {
    public static final String PE_DB_LOG_VIEW_PREFIX = "";
    public static final String PE_DB_VIEW_SCHEMA = "";
    
    private static final Logger log = Logger.getLogger(ProcessQueryUtil.class);
    
    /**
     * No instances.
     */
    private ProcessQueryUtil() {}

    //=========================================================================
    // 查询条件相关公共方法
    //=========================================================================
    
//    public static void setJpaParameters(javax.persistence.Query jpaQuery, Object[]... varsArray) {
//        Object[] vars = mergeSubstitutionVars(varsArray);
//        for (int i = 0; i < vars.length; i++) {
//            jpaQuery.setParameter(i + 1, vars[i]);
//        }
//    }
    
    private static Object[] mergeSubstitutionVars(Object[]... varsArray) {
        List<Object> mergedVars = new ArrayList<Object>();
        for (Object[] vars : varsArray) {
            mergedVars.addAll(Arrays.asList(vars));
        }
        return mergedVars.toArray();
    }

    public static String getEventLogViewName(String eventLogName) {
        return new StringBuffer()
            .append(ProcessQueryUtil.PE_DB_VIEW_SCHEMA)
            .append(".")
            .append(ProcessQueryUtil.PE_DB_LOG_VIEW_PREFIX)
            .append(eventLogName)
            .toString();
    }

    /**
     * @return
     */
    public static List<String> getRawColumnNames() {
        return Arrays.asList(new String[] {
            "F_BoundUserId",
            "F_Comment",
            "F_EnqueueTime",
            "F_EventType",
            "F_Originator",
            "F_Response",
            "F_SeqNumber",
            "F_StartTime",
            "F_TimeStamp",
            "F_StepName",
            "F_Subject",
            "F_UserId",
            "F_WobNum",
            "F_Class",
            "F_WorkFlowNumber",
            "F_WorkOrderId",
            "F_WorkClassId", // Not in use
            "FormID",
            "PreviousUser",
            "CreatedUnit",
            "RecordCode",
            "UrgencyLevel",
            "distributeComment",
            "approveComment",
            "submitTo",
            "copyTo",
            "displayApprover"
        });
    }

    //=========================================================================
    // 查询结果相关公共方法
    //=========================================================================
    
    public static HistoricalWorkItem getHistoricalWorkItem(VWLogElement le, 
            VWSession vwSession, String[] dataFieldNames) throws VWException {
        HistoricalWorkItem hwi = new HistoricalWorkItem();
        
        hwi.setBoundUser(vwSession.convertIdToUserName((Integer) le.getFieldValue(P8BpmConstants.PE_SYSTEM_FIELD_BOUND_USER_ID)));
        hwi.setBoundUserId((Integer) le.getFieldValue(P8BpmConstants.PE_SYSTEM_FIELD_BOUND_USER_ID));
        if(le.hasField(P8BpmConstants.PE_USER_FIELD_COMMENT))
        hwi.setComment((String) le.getFieldValue(P8BpmConstants.PE_USER_FIELD_COMMENT));
        hwi.setEnqueueTime((Date) le.getFieldValue(P8BpmConstants.PE_SYSTEM_FIELD_ENQUEUE_TIME));
        hwi.setEventType(le.getEventType());
        hwi.setOriginator(vwSession.convertIdToUserName((Integer) le.getFieldValue(P8BpmConstants.PE_SYSTEM_FIELD_ORIGINATOR)));
        hwi.setOriginatorId((Integer) le.getFieldValue(P8BpmConstants.PE_SYSTEM_FIELD_ORIGINATOR));
        hwi.setSelectedResponse(le.getSelectedResponse());
        hwi.setSequenceNumber(le.getSequenceNumber());
        hwi.setStartTime((Date) le.getFieldValue(P8BpmConstants.PE_SYSTEM_FIELD_START_TIME));
        hwi.setTimestamp((Date) le.getTimeStamp());
        hwi.setStepDuration(hwi.getTimestamp().getTime() - hwi.getEnqueueTime().getTime());
        hwi.setStepName(le.getStepName());
        hwi.setSubject(le.getSubject());
        hwi.setWPClassId((Integer)le.getFieldValue(P8BpmConstants.PE_SYSTEM_FIELD_WP_CLASS_ID));
        hwi.setUser(le.getUserName());
        hwi.setUserId(le.getUserNamePx().getUserId());
        if(le.hasField(P8BpmConstants.PE_USER_FIELD_ROLE_NAME)) {
        	hwi.setRoleName((String) le.getFieldValue(P8BpmConstants.PE_USER_FIELD_ROLE_NAME));
        }
        hwi.setWobNum(le.getWorkObjectNumber());
        hwi.setWorkClassName(le.getWorkClassName());
        hwi.setWorkflowName(le.getWorkflowName());
        hwi.setWorkflowNumber(le.getWorkFlowNumber());
        hwi.setWorkOrderId(le.getWorkOrderId());
        for (String dataFieldName : dataFieldNames) {
        	if(le.hasField(dataFieldName))
        		hwi.putFieldValue(dataFieldName, le.getFieldValue(dataFieldName));
        }
        if(le.hasField(P8BpmConstants.PREVIOUS_USER) && le.getFieldValue(P8BpmConstants.PREVIOUS_USER) != null) {
        	hwi.setPreviousUser((String) le.getFieldValue(P8BpmConstants.PREVIOUS_USER));
        }
        if(le.hasField(P8BpmConstants.PE_USER_FIELD_CREATEDUNIT) && le.getFieldValue(P8BpmConstants.PE_USER_FIELD_CREATEDUNIT) != null) {
        	hwi.setCreatedUnit((String) le.getFieldValue(P8BpmConstants.PE_USER_FIELD_CREATEDUNIT));
        }
        if(le.hasField(P8BpmConstants.PE_USER_FIELD_REASSIGNBYADMIN)){
        	Object reassignByAdmin = le.getFieldValue(P8BpmConstants.PE_USER_FIELD_REASSIGNBYADMIN);
        	if(reassignByAdmin != null)
        	hwi.setReassignByAdmin((Boolean)reassignByAdmin );
        }
        return hwi;
    }

    public static HistoricalWorkItem getHistoricalWorkItem(Object[] rawColumns, 
            VWSession vwSession) throws VWException {
        HistoricalWorkItem hwi = new HistoricalWorkItem();
        
        //  0 "F_BoundUserId", BigDecimal
        hwi.setBoundUser(vwSession.convertIdToUserName(((BigDecimal) rawColumns[0]).intValue()));
        hwi.setBoundUserId(((BigDecimal) rawColumns[0]).intValue());
        //  1 "F_Comment", String
        hwi.setComment((String) rawColumns[1]);
        //  2 "F_EnqueueTime", BigDecimal
        hwi.setEnqueueTime(new Date(((BigDecimal) rawColumns[2]).longValue()*1000));
        //  3 "F_EventType", BigDecimal
        hwi.setEventType(((BigDecimal) rawColumns[3]).intValue());
        //  4 "F_Originator", BigDecimal
        hwi.setOriginator(vwSession.convertIdToUserName(((BigDecimal) rawColumns[4]).intValue()));
        hwi.setOriginatorId(((BigDecimal) rawColumns[4]).intValue());
        //  5 "F_Response", String
        hwi.setSelectedResponse((String) rawColumns[5]);
        //  6 "F_SeqNumber", BigDecimal
        hwi.setSequenceNumber(((BigDecimal) rawColumns[6]).intValue());
        //  7 "F_StartTime", BigDecimal
        hwi.setStartTime(new Date(((BigDecimal) rawColumns[7]).longValue()*1000));
        //  8 "F_TimeStamp", BigDecimal
        hwi.setTimestamp(new Date(((BigDecimal) rawColumns[8]).longValue()*1000));
        hwi.setStepDuration(hwi.getTimestamp().getTime() - hwi.getEnqueueTime().getTime());
        //  9 "F_StepName", String
        hwi.setStepName((String) rawColumns[9]);
        // 10 "F_Subject", String
        hwi.setSubject((String) rawColumns[10]);
        // 11 "F_UserId", BigDecimal
        hwi.setUser(vwSession.convertIdToUserName(((BigDecimal) rawColumns[11]).intValue()));
        hwi.setUserId(((BigDecimal) rawColumns[11]).intValue());
        // 12 "F_WobNum", byte[]
        hwi.setWobNum(getHexString((byte[]) rawColumns[12]));
        // 13 "F_Class", String
        hwi.setWorkClassName((String) rawColumns[13]);
        hwi.setWorkflowName((String) rawColumns[13]);
        // 14 "F_WorkFlowNumber", byte[]
        hwi.setWorkflowNumber(getHexString((byte[]) rawColumns[14]));
        // 15 "F_WorkOrderId", BigDecimal
        hwi.setWorkOrderId(((BigDecimal) rawColumns[15]).intValue());
        // 16 "F_WorkClassId", BigDecimal
        // Not in use
        // 17 "FormID", String
        hwi.setFormId((String) rawColumns[17]);
        hwi.setPreviousUser((String) rawColumns[18]);
        hwi.setCreatedUnit((String) rawColumns[19]);
        hwi.setFileCode((String) rawColumns[20]);
        hwi.setUrgencyLevel(((BigDecimal) rawColumns[21]).intValue());
        hwi.setDistributeComment((String) rawColumns[22]);
        hwi.setApproveComment((String) rawColumns[23]);
        hwi.setSubmitTo((String) rawColumns[24]);
        hwi.setCopyTo((String) rawColumns[25]);
        hwi.setDisplayApprover((String) rawColumns[26]);
        return hwi;
    }

    public static HistoricalWorkflow getHistoricalWorkflow(HistoricalWorkItem hwi) {
        HistoricalWorkflow hwf = new HistoricalWorkflow();
        
        if (hwi.getEventType() == P8BpmConstants.LOG_TYPE_PROCESS_FINISH) {
            hwf.setFinished(true);
        }
        hwf.setLastUpdateTime(hwi.getTimestamp());
        hwf.setWorkClassName(hwi.getWorkClassName());
        hwf.setWorkflowName(hwi.getWorkflowName());
        hwf.setWorkflowNumber(hwi.getWorkflowNumber());
        hwf.setWobNum(hwi.getWobNum());
        hwf.setStepName(hwi.getStepName());
        /*
        String subject = "<a target='_blank' href='processWorkflow.do?action=history&workflowNumber=" 
		+ hwi.getWorkflowNumber() + "&stepName=" + hwi.getStepName() + "&workflowName=" + hwi.getWorkflowName() 
		+ "&formId=" + hwi.getFormId()+ "'>" + hwi.getSubject() + "</a>";
        hwf.setSubject(subject);
        */
        hwf.setSubject(hwi.getSubject());
        hwf.setOriginator(hwi.getOriginator());
        hwf.setOriginatorId(hwi.getOriginatorId());
        hwf.setStartTime(hwi.getStartTime());
        hwf.setFormId(hwi.getFormId());
        hwf.setDisplayStepName(hwi.getStepName());
        hwf.setPreviousUser(hwi.getPreviousUser());
        hwf.setCreatedUnit(hwi.getCreatedUnit());
        hwf.setFileCode(hwi.getFileCode());
        hwf.setUrgencyLevel(hwi.getUrgencyLevel());
        hwf.setDistributeComment(hwi.getDistributeComment());
        hwf.setApproveComment(hwi.getApproveComment());
        hwf.setSubmitTo(hwi.getSubmitTo());
        hwf.setCopyTo(hwi.getCopyTo());
        hwf.setDisplayApprover(hwi.getDisplayApprover());
        return hwf;
    }
    
    /**
     * 将同属一个流程实例的历史任务项集合起来，返回一个完整的历史流程实例的描述。
     * 
     * @param hwis 历史任务列表，要求所有成员的WorkflowNumber属性值相同。
     * @return 完整的历史流程实例的描述
     */
    public static HistoricalWorkflow getHistoricalWorkflow(List<HistoricalWorkItem> hwis) {
        HistoricalWorkflow hwf = new HistoricalWorkflow();
        if(!hwis.isEmpty()) {
			Collections.sort(hwis);
			HistoricalWorkItem lastHwi = hwis.get(0);
			hwf = getHistoricalWorkflow(lastHwi);
			hwf.getWorkItems().addAll(hwis);
        }
        return hwf;
    }

    public static String getHexString(byte[] b) {
        String result = "";
        for (int i = 0; i < b.length; i++) {
            result += Integer.toString((b[i] & 0xff) + 0x100, 16).substring(1);
        }
        return result.toUpperCase();
    }
}
