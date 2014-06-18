package com.ibm.eu.services.base.query.builder;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.ibm.eu.constants.P8BpmConstants;
import com.ibm.eu.services.base.query.Query;

import filenet.vw.api.VWException;
import filenet.vw.api.VWSession;

/**
 * 通用构建器。
 * 
 * 建议仅在包内使用。
 */
public class ProcessQueryBuilder extends AbstractProcessQueryBuilder {

    protected final Logger logger = Logger.getLogger(getClass());
    
    private Query base;
    
    public ProcessQueryBuilder() {
        this(new Query());
    }
    
    @Deprecated
    public ProcessQueryBuilder(Query query) {
        this.base = query;
    }

    public Query buildQuery() {
        QueryProxy query = new QueryProxy(base); // TODO remove this base.
        int i = 0;
        
        for (Entry entry : queryConditions.values()) {
            // Append "AND" before new entry except for the 1st entry or base 
            // query already has content.
            if (i != 0 || !StringUtils.isEmpty(query.getFilter())) {
                query.appendFilter(" AND ");
            }
            i++;
            
            buildEntry(query, entry);
        }

        return query;
    }
    /**
     * WLG 我的拟稿
     */
    public Query buildORQuery() {
        QueryProxy query = new QueryProxy(base); // TODO remove this base.
        int i = 0;
        
        for (Entry entry : queryConditions.values()) {
            // Append "AND" before new entry except for the 1st entry or base 
            // query already has content.
            if (i != 0 || !StringUtils.isEmpty(query.getFilter())) {
                query.appendFilter(" OR ");
            }
            i++;
            
            buildEntry(query, entry);
        }

        return query;
    }
    
    @Deprecated
    public Query buildWithUserAndEventTypeFilters(String boundUserName, 
            String optUserName, VWSession vwSession) 
            throws VWException  {
        int boundUserId = 0;
        int optUserId = 0;
            if (!StringUtils.isEmpty(boundUserName)) {
                boundUserId = vwSession.convertUserNameToId(boundUserName);
            }
            optUserId = vwSession.convertUserNameToId(optUserName);
        return buildWithUserAndEventTypeFilters(boundUserId, optUserId);
    }
    
    @Deprecated
    public Query buildWithUserAndEventTypeFilters(int boundUserId, int optUserId) {
        QueryProxy query = (QueryProxy) buildQuery();
        
        if (!"".equals(query.getFilter())) {
            query.appendFilter(" AND ");
        }
        query.appendFilter("((");

        // Bound User
        if (boundUserId != 0) {
            query.appendFilterKey(P8BpmConstants.PE_SYSTEM_FIELD_BOUND_USER_ID)
                .appendFilter(" = :")
                .appendFilter(P8BpmConstants.PE_SYSTEM_FIELD_BOUND_USER_ID)
                .appendFilter(" OR ");
            query.addSubstitutionVar(boundUserId);
        }
        
        // Operation User
        query.appendFilterKey(P8BpmConstants.PE_SYSTEM_FIELD_USER_ID)
            .appendFilter(" = :")
            .appendFilter(P8BpmConstants.PE_SYSTEM_FIELD_USER_ID)
            .appendFilter(" AND ");
        query.addSubstitutionVar(optUserId);
        
        // Event Type = step complete
        query.appendFilterKey(P8BpmConstants.PE_SYSTEM_FIELD_EVENT_TYPE)
            .appendFilter(" = :")
            .appendFilter(P8BpmConstants.PE_SYSTEM_FIELD_EVENT_TYPE)
            .appendFilter("1) OR ");
        query.addSubstitutionVar(P8BpmConstants.LOG_TYPE_STEP_FINISH);
        
        // Event Type = workflow complete
        query.appendFilterKey(P8BpmConstants.PE_SYSTEM_FIELD_EVENT_TYPE)
            .appendFilter(" = :")
            .appendFilter(P8BpmConstants.PE_SYSTEM_FIELD_EVENT_TYPE)
            .appendFilter("2)");
        query.addSubstitutionVar(P8BpmConstants.LOG_TYPE_PROCESS_FINISH);
        
        return query;
    }
}
