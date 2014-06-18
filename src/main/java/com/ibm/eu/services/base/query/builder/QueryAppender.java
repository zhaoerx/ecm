package com.ibm.eu.services.base.query.builder;

import org.apache.commons.lang.StringUtils;

import com.ibm.eu.services.base.query.Query;

/**
 * 用于扩充一个既有的Query对象。
 * 
 * 建议仅在包内使用。
 */
public class QueryAppender extends AbstractProcessQueryBuilder {

    private Query base;
    
    public QueryAppender(Query query) {
        this.base = query == null ? new Query() : query;
    }

    public Query buildQuery() {
        QueryProxy query = new QueryProxy(base);
        int i = 0;
        
        for (Entry entry : queryConditions.values()) {
            
            if (entry.getValue() == null && entry.getBounds() == null) {
                throw new IllegalArgumentException("Value of query condition (key: '" 
                        + entry.getKey() + "') cannot be null.");
            }
            
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
}
