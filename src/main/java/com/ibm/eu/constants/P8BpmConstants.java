package com.ibm.eu.constants;

import filenet.vw.api.VWLoggingOptionType;

/**
 * FileNet P8 BPM constants.
 */
public final class P8BpmConstants {

	/**
     * Prevents instantiation.
     */
    private P8BpmConstants() {
    }
 
    /**
     * The attribute name of <code>VWSession</code> object stored in
     * HttpSession.
     */
    public static final String VWSESSION_NAME = "vwSession";
    /**
     * The parameter name of work object number in request.
     */
    public static final String REQUEST_PARAM_WOB_NUM = "wobNum";
    
    // -----------------------------------------------------------------------
    // EventLog常量
    // -----------------------------------------------------------------------
    
    public static final String DEFAULT_EVENT_LOG_NAME = "DefaultEventLog";
    
    // -----------------------------------------------------------------------
    // Work performer categories
    // -----------------------------------------------------------------------
    
    /**
     * Work performer class id of CE_Operations.
     */
    public static final int WP_CLASS_CE_OPERATION = 1;
    
    /**
     * Work performer class id of Inbox.
     */
    public static final int WP_CLASS_INBOX = 2;
    
    /**
     * Work performer class id of Delay.
     */
    public static final int WP_CLASS_DELAY = -6;


    
    // -----------------------------------------------------------------------
    // 流程的系统参数，PE原生参数，以F_开头
    // -----------------------------------------------------------------------
    
    public static final String PE_SYSTEM_FIELD_BOUND_USER = "F_BoundUser";
    /**
     * Id of user bound to the work item. 
     */
    public static final String PE_SYSTEM_FIELD_BOUND_USER_ID = "F_BoundUserId";
    /** 
     * The name of the work class. 
     */
    public static final String PE_SYSTEM_FIELD_WORK_CLASS_NAME = "F_Class";
    /**
     * This field is initialized to null every time that a step starts. It
     * allows the users participating in a step to share information about the
     * step. For example, when a user reassigns the step, he can write a comment
     * for the user receiving the step.
     */
    public static final String PE_SYSTEM_FIELD_COMMENT = "F_Comment";
    /** 
     * The time at which the work item was created. 
     */
    public static final String PE_SYSTEM_FIELD_CREATE_TIME = "F_CreateTime";
    /**
     * The time assigned to the step deadline.
     */
    public static final String PE_SYSTEM_FIELD_DEADLINE = "F_Deadline";
    /**
     * The time at which the work item entered the queue or was updated in the
     * queue.
     */
    public static final String PE_SYSTEM_FIELD_ENQUEUE_TIME = "F_EnqueueTime";
    /**
     * The type number of the event. 
     */
    public static final String PE_SYSTEM_FIELD_EVENT_TYPE = "F_EventType";
    /**
     * The work item's lock status:
     * <p>
     * <li>0=not locked</li>
     * <li>1=locked by user</li>
     * <li>2=locked by system</li>
     * </p>
     */
    public static final String PE_SYSTEM_FIELD_LOCKED = "F_Locked";
    /**
     * The time at which the work item was locked.
     */
    public static final String PE_SYSTEM_FIELD_LOCK_TIME = "F_LockTime";
    /**
     * Whether the work item is overdue:
     * <p>
     * <li>0=work item is not overdue</li>
     * <li>1=reminder has expired for this step</li>
     * <li>2=deadline has expired for this step</li>
     */
    public static final String PE_SYSTEM_FIELD_OVERDUE = "F_Overdue";
    /**
     * Transient field. It only has a value if the work item has been saved with
     * a response. Derived from F_Responses.
     */
    public static final String PE_SYSTEM_FIELD_RESPONSE = "F_Response";
    /**
     * Appended to F_TimeStamp to create a unique identifier for each event. 
     */
    public static final String PE_SYSTEM_FIELD_SEQUENCE_NUMBER = "F_SeqNumber";
    /**
     * The name of the step that is either in process or (if no step is
     * currently in process) is next to be executed for the work item.
     */
    public static final String PE_SYSTEM_FIELD_STEP_NAME = "F_StepName";
    /**
     * The status of the step. Valid values are Complete, In progress, or
     * Deleted.
     */
    public static final String PE_SYSTEM_FIELD_STEP_STATUS = "F_StepStatus";
    /**
     * The subject entered by the user when a workflow is launched. This field
     * is used in the out-of-the-box Workplace application to populate the Name
     * field on the Tasks page.
     */
    public static final String PE_SYSTEM_FIELD_SUBJECT = "F_Subject";
    /**
     * A unique ID for a work item. In a queue record only, the value changes 
     * each time the work item is updated.
     */
    public static final String PE_SYSTEM_FIELD_UNIQUE_ID = "F_UniqueId"; 
    /**
     * The internal identifier of a user. 
     */
    public static final String PE_SYSTEM_FIELD_USER_ID = "F_UserId"; 
    /**
     * A 16-byte binary field that is actually a GUID (global unique
     * identifier). It uniquely identifies a single work item.
     */
    public static final String PE_SYSTEM_FIELD_WOBNUM = "F_WobNum";
    /**
     * The F_WobNum of the work item that initiated the workflow.
     */
    public static final String PE_SYSTEM_FIELD_WORKFLOW_NUMBER = "F_WorkFlowNumber";
    /**
     * The user ID of the user that started the workflow.
     */
    public static final String PE_SYSTEM_FIELD_ORIGINATOR = "F_Originator";
    /**
     * The time the workflow was created. 
     * With the exception of the initial work item of the workflow, 
     * it is different than F_CreateTime. 
     */
    public static final String PE_SYSTEM_FIELD_START_TIME = "F_StartTime";
    /**
     * The description or instructions associated with the current step.
     */
    public static final String PE_SYSTEM_FIELD_STEPDESCRIPTION = "F_StepDescription";
    /**
     * Internal identifier of the work performer class of the queue that 
     * contains the work item.
     */
    public static final String PE_SYSTEM_FIELD_WP_CLASS_ID = "F_WPClassId";
    
    // -----------------------------------------------------------------------
    // 用户参加的流程参数
    // -----------------------------------------------------------------------
    
    //流程参与角色
    public static final String PE_USER_FIELD_ROLE_NAME = "RoleName";
    //任务管理员改派
    public final static String PE_USER_FIELD_REASSIGNBYADMIN = "reassignByAdmin";
    //任务管理员加锁
    public final static String PE_USER_FIELD_LOCKBYADMIN = "lockByAdmin";
    //任务管理员加锁
    public static final String PE_USER_FIELD_DELEGATOR = "Delegator";
    //任务管理员加锁
    public static final String PE_USER_FIELD_DELEGATORID = "DelegatorId";
    //编制单位
    public static final String PE_USER_FIELD_CREATEDUNIT = "CreatedUnit";
    //紧急程度（替换原有的紧急程度，由字符串变为整型（4：紧急；2：一般））
    public static final String URGENCY_LEVEL = "UrgencyLevel";
    //上个环节参与者
    public static final String PREVIOUS_USER = "PreviousUser"; 
    //流程状态
    public static final String PE_USER_FIELD_PROCESS_STATUS = "ProcessStatus";
    //流程结束时间
    public static final String PE_USER_FIELD_PROCESS_FINISHED_ON = "ProcessFinishedOn";
    //流程发起人
    public static final String PE_USER_FIELD_DISPLAY_ORIGINATOR = "DisplayOriginator";
    //历史意见
    public static final String PE_USER_FIELD_COMMENT = "Comment";
    //文件代码
    public static final String PE_USER_FIELD_RECORD_CODE = "RecordCode";
    //任务（流程步骤）
    public static final String PE_USER_FIELD_DISPLAY_STEP_NAME = "DisplayStepName";
    //部门代码
    public static final String PE_USER_FIELD_DEPT_CODE = "DeptCode";
    //领域
    public static final String PE_USER_FIELD_NUCLEAR_DOMAIN = "NuclearDomain";
    //密级
    public static final String PE_USER_FIELD_SECURITY_LEVEL = "SecurityLevel";
    //流程英文题名
    public static final String PE_USER_FIELD_RECORD_ENGLISH_TITLE = "RecordEnglishTitle";
    //批准日期
    public static final String PE_USER_FIELD_APPROVE_DATE = "ApproveDate";
    //批准者
    public static final String PE_USER_FIELD_APPROVER = "Approver";
    //发文单位
    public static final String PE_USER_FIELD_FROM_UNIT = "FromUnit";
    //收文日期
    public static final String PE_USER_FIELD_RECORD_RECEIVE_TIME = "RecordReceiveTime";
    //外发电厂ID列表
    public static final String PE_USER_FIELD_SEND_TO_LIST = "sendToList";
    //批分意见
    public static final String PE_USER_FIELD_DISTRIBUTE_COMMENT = "distributeComment";
    //批准意见
    public static final String PE_USER_FIELD_APPROVE_COMMENT = "approveComment";
    public static final String PE_USER_FIELD_SUBMIT_TO = "submitTo";
    public static final String PE_USER_FIELD_COPY_TO = "copyTo";
    public static final String PE_USER_FIELD_DISPLAYAPPROVER = "displayApprover";
    
	public static final String[] USER_FIELDS = { PE_USER_FIELD_PROCESS_STATUS,
			PE_USER_FIELD_PROCESS_FINISHED_ON,
			PE_USER_FIELD_DISPLAY_ORIGINATOR, PE_USER_FIELD_COMMENT,
			PE_USER_FIELD_RECORD_CODE, PE_USER_FIELD_DISPLAY_STEP_NAME,
			PE_USER_FIELD_DEPT_CODE, PE_USER_FIELD_NUCLEAR_DOMAIN,
			PE_USER_FIELD_SECURITY_LEVEL, PE_USER_FIELD_RECORD_ENGLISH_TITLE,
			PE_USER_FIELD_APPROVE_DATE, PE_USER_FIELD_APPROVER,
			PE_USER_FIELD_FROM_UNIT, PE_USER_FIELD_RECORD_RECEIVE_TIME, PE_USER_FIELD_SEND_TO_LIST,
			PE_USER_FIELD_DISTRIBUTE_COMMENT, PE_USER_FIELD_APPROVE_COMMENT ,PE_USER_FIELD_SUBMIT_TO,
			PE_USER_FIELD_COPY_TO,PE_USER_FIELD_DISPLAYAPPROVER};
    
    // -----------------------------------------------------------------------
    // Event logging indeies
    // -----------------------------------------------------------------------
    
    /**
     * F_TimeStamp+F_SeqNumber
     */
    public static final String PE_LOG_INDEX_LOG_TIME = "F_LogTime";
    /**
     * F_SeqNumber+F_TimeStamp
     */
    public static final String PE_LOG_INDEX_LOG_SEQUENCE = "F_LogSequence";
    /**
     * F_WorkFlowNumber+F_TimeStamp+F_SeqNumber
     */
    public static final String PE_LOG_INDEX_WORKFLOW_NUMBER = "F_WorkFlowNumber";

    
    // -----------------------------------------------------------------------
    // EventLog里的状态常量
    // -----------------------------------------------------------------------
    
    /**
     * Records the completion of all work items in a workflow.
     */
    public static final int LOG_TYPE_PROCESS_FINISH = VWLoggingOptionType.LOGGING_OPTION_TYPE_VW_WFTerminationMsg;
    /**
     * Records when a work item is queued.
     */
    public static final int LOG_TYPE_STEP_QUEUED = VWLoggingOptionType.LOGGING_OPTION_TYPE_VW_WPWorkObjectQueued;
    /**
     * Records when a work item is updated and dispatched to the next.
     */
    public static final int LOG_TYPE_STEP_FINISH = VWLoggingOptionType.LOGGING_OPTION_TYPE_VW_WPEndServiceNormal;
    /**
     * Records the creation of a "parent" work item. Namely when launching the workflow.
     */
    public static final int LOG_TYPE_LAUNCH_STEP_FINISH = VWLoggingOptionType.LOGGING_OPTION_TYPE_VW_WOParentCreation;
    
    /**
     * sava workflow
     */
    public static final int LOG_TYPE_LAUNCH_STEP_WPBEGINSERVICE = VWLoggingOptionType.LOGGING_OPTION_TYPE_VW_WPBeginService;
    
    /**
     * sava workflow
     */
    public static final int LOG_TYPE_LAUNCH_STEP_WPENDSERVICEABORT = VWLoggingOptionType.LOGGING_OPTION_TYPE_VW_WPEndServiceAbort;
    
    /**
     * Records the creation of a  work item is reassigned and not returned.(eventType384Filter)
     */
    public static final int LOG_TYPE_REASSIGN = VWLoggingOptionType.LOGGING_OPTION_TYPE_VW_WPEndServiceReleaseReassign;
    
    /**
     * Records the creation of a  work item is reassigned and must be returned. (eventType382Filter)
     */
    public static final int LOG_TYPE_DELEGATE = VWLoggingOptionType.LOGGING_OPTION_TYPE_VW_WPEndServiceReleaseDelegate;
    
    /**
     * Records the sub workItem is finished .(eventType386Filter)
     */
    public static final int LOG_TYPE_WOChild_TO_TERMINATE = VWLoggingOptionType.LOGGING_OPTION_TYPE_VW_WOChildTermination;
    
    /**
     * Records the reassigned work is finished and returned.(eventType386Filter)
     */
    public static final int LOG_TYPE_DELEGATE_RETURN = VWLoggingOptionType.LOGGING_OPTION_TYPE_VW_WPEndServiceReleaseReturn;
    
    /**
     * Records the reassigned work is finished and returned.(eventType386Filter)
     */
    public static final int LOG_TYPE_RELEASE = VWLoggingOptionType.LOGGING_OPTION_TYPE_VW_WPEndServiceRelease;
    /**
     * Records the reassigned work is finished and returned.(eventType386Filter)
     */
    public static final int LOG_TYPE_FORCED_TO_TERMINATE = VWLoggingOptionType.LOGGING_OPTION_TYPE_VW_WOForcedToTerminate;
    
    
    
    
    // -----------------------------------------------------------------------
    // Process names
    // -----------------------------------------------------------------------

    
    

    
    
}
