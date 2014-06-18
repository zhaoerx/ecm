package com.ibm.eu.services.base.query.builder;

import java.util.Collection;

import com.ibm.eu.services.base.query.Query;

/**
 * Query对象的代理，可以加入查询条件。
 */
public class QueryProxy extends Query {

    public QueryProxy() {
    }

    /**
     * Duplicate from existing instance.
     * 
     * @param query
     */
    public QueryProxy(Query query) {
        super(query);
    }
    
    //=========================================================================
    // 保护方法，仅供查询构建器使用
    //=========================================================================
    
    protected QueryProxy appendFilter(String filterPart) {
        filter.append(filterPart);
        jpaFilter.append(filterPart);
        return this;
    }
    
    protected QueryProxy appendFilterKey(String filterKey) {
        filter.append(filterKey);
        jpaFilter.append(JPA_TABLE_ALIAS)
            .append(".\"")
            .append(filterKey)
            .append("\"");
        return this;
    }
    
    protected QueryProxy addSubstitutionVar(Object var) {
        substitutionVarList.add(var);
        return this;
    }
    
    protected QueryProxy addSubstitutionVarCollection(Collection<?> var) {
        substitutionVarList.addAll(var);
        return this;
    }
}
