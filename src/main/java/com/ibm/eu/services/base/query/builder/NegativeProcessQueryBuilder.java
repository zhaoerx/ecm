package com.ibm.eu.services.base.query.builder;

import java.lang.reflect.Array;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.ibm.eu.services.base.query.Query;

/**
 * 构建条件均为否定条件（“<>”）
 * 
 * 建议仅在包内使用。
 */
public class NegativeProcessQueryBuilder extends AbstractProcessQueryBuilder {

    protected final Logger logger = Logger.getLogger(getClass());
    
    private Query base;
    
    public NegativeProcessQueryBuilder() {
        this(new Query());
    }
    
    public NegativeProcessQueryBuilder(Query query) {
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
    
    @Override
    protected void buildEntry(QueryProxy query, Entry entry) {
        switch (entry.getType()) {
        case INT:
        case FLOAT:
        case TIME:
            if (entry.getValue() != null) {
                buildSingleEntry(query, entry);
            } else if (entry.getBounds() != null) {
                throw new UnsupportedOperationException();
            }
            break;
            
        case STRING:
            if (entry.getValue() != null) {
                if (isFuzzy() && !FIELD_PROCESS_NAME.equals(entry.getKey())) {
                    throw new UnsupportedOperationException();
                } else {
                    buildSingleEntry(query, entry);
                }
            }
            break;
            
        case INT_ARRAY:
        case FLOAT_ARRAY:
        case STRING_ARRAY:
            buildArrayEntry(query, entry);
            break;
            
        default:
            if (entry.getValue() != null) {
                buildSingleEntry(query, entry);
            }
            break;
        }
    }
    
    /**
     * 构造单一条件的查询
     */
    @Override
    protected void buildSingleEntry(QueryProxy query, Entry entry) {
        query.appendFilterKey(entry.getKey())
            .appendFilter(" <> :")
            .appendFilter(entry.getKey())
            .appendFilter(placeHolderSuffix);
        query.addSubstitutionVar(entry.getValue());
    }
    
    /**
     * 构造多值条目的“或”查询
     */
    @Override
    protected void buildArrayEntry(QueryProxy query, Entry entry) {
        String key = entry.getKey();
        Object value = entry.getValue();
        int length = Array.getLength(value);
        
        for (int i = 0; i < length; i++) {
            if (i == 0) {
                query.appendFilter("(");
            }
            query.appendFilterKey(key)
                .appendFilter(" <> :")
                .appendFilter(key)
                .appendFilter(placeHolderSuffix)
                .appendFilter("_")
                .appendFilter(String.valueOf(i));
            if (i == (length - 1)) {
                query.appendFilter(")");
            } else {
                query.appendFilter(" AND ");
            }
            
            Object item = Array.get(value, i);
            query.addSubstitutionVar(item);
        }
    }
}
