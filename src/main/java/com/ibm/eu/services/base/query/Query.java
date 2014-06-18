package com.ibm.eu.services.base.query;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.builder.ToStringBuilder;

public class Query {
    public static final String JPA_TABLE_ALIAS = "myitem";
    
    protected StringBuffer filter = new StringBuffer();
    protected StringBuffer jpaFilter = new StringBuffer();
    // Use linked hash map to keep the same sequence with filters. 
    protected List<Object> substitutionVarList = new ArrayList<Object>();
    
    private Map<NestedQueryType, Query> nestedQueries = new HashMap<NestedQueryType, Query>();
    
    public Query() {
    }

    /**
     * Duplicate from existing instance.
     * 
     * @param query
     */
    public Query(Query query) {
        if (query != null) {
            this.filter.append(query.filter);
            this.jpaFilter.append(query.jpaFilter);
            this.substitutionVarList.addAll(query.substitutionVarList);
        }
    }
    
    //=========================================================================
    // 用于PE API查询的公共方法
    //=========================================================================
    
    public String getFilter() {
        return filter.toString();
    }
    
    public Object[] getSubstitutionVars() {
        return substitutionVarList.toArray();
    }
    
    //=========================================================================
    // 用于PE对应的数据库视图查询的公共方法
    //=========================================================================
    
    public String getJpaFilter() {
        return jpaFilter.toString();
    }
    
    public String getJpaFilter(String prefix) {
        return jpaFilter.toString().replaceAll(JPA_TABLE_ALIAS + ".", prefix);
    }
    
    public Object[] getJpaSubstitutionVars() {
        List<Object> jpaVars = new ArrayList<Object>();
        for (Object var : substitutionVarList) {
            if (var instanceof Date) {
                jpaVars.add(((Date) var).getTime() / 1000);
            } else {
                jpaVars.add(var);
            }
        }
        return jpaVars.toArray();
    }
    
    //=========================================================================
    // 其他公共方法
    //=========================================================================
    
    public Query getNestedQuery(NestedQueryType type) {
        return nestedQueries.get(type);
    }
    
    public Query putNestedQuery(NestedQueryType type, Query query) {
        return nestedQueries.put(type, query);
    }
    
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
