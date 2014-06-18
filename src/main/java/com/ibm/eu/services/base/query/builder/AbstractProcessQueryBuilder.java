package com.ibm.eu.services.base.query.builder;

import java.lang.reflect.Array;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import com.ibm.eu.services.base.query.Query;


/**
 * 这个类以Builder模式搭建流程查询条件。用户需实例化该类，按字段按类型加入单个
 * 查询条件，然后调用方法产生完整的查询条件。其中，各条件间为“与”的关系。
 */
public abstract class AbstractProcessQueryBuilder {

    protected static final String UPPER_BOUND = "_Max";
    protected static final String LOWER_BOUND = "_Min";
    
    protected static final String FIELD_PROCESS_NAME = "F_Class";
    protected static final String FIELD_PROCESS_SUBJECT = "F_Subject";
    
    // 
    /**
     * Need to put entries ultimately via 
     * {@link #put(String, ParamType, Object, Bounds)} instead of Map interface. 
     * Use linked hash map to keep the input sequence.
     */
    protected Map<String, Entry> queryConditions = new LinkedHashMap<String, Entry>();
    protected boolean isFuzzy = false;
    /**
     * 用来区别不同Builder构建Query中的占位符，预防多个Query混用占位符同名的情况。
     */
    protected String placeHolderSuffix = "_" + Long.toHexString(System.currentTimeMillis()).toUpperCase();
    
    public AbstractProcessQueryBuilder() {
        // TODO Auto-generated constructor stub
    }
    
    //=========================================================================
    // 公共方法
    //=========================================================================
    
    /**
     * 返回是否打开所有字符串字段的模糊查询。
     * 
     * @return <code>true</code>为字符串字段进行模糊匹配，否则为精确
     */
    public boolean isFuzzy() {
        return isFuzzy;
    }

    /**
     * 设定是否打开所有字符串字段的模糊查询。
     * 
     * @param isFuzzy <code>true</code>为字符串字段进行模糊匹配，否则为精确
     * @return 当前ProcessQueryBuilder实例
     */
    public AbstractProcessQueryBuilder setFuzzy(boolean isFuzzy) {
        this.isFuzzy = isFuzzy;
        return this;
    }

    /**
     * 指定当前查询的流程。不指定或指定为null则查询所有流程。
     * 
     * @param processName 流程名称，如果为null则查询所有流程。
     * @return 当前ProcessQueryBuilder实例
     */
    public AbstractProcessQueryBuilder setProcess(String processName) {
        putOrRemove(FIELD_PROCESS_NAME, processName);
        return this;
    }
    
    /**
     * 指定当前查询的流程主题。不指定或指定为null则忽略此条件。
     * 
     * @param subject 流程主题。
     * @return 当前ProcessQueryBuilder实例
     */
    public AbstractProcessQueryBuilder setSubject(String subject) {
        putOrRemove(FIELD_PROCESS_SUBJECT, subject);
        return this;
    }
        
    /**
     * 指定一个字符串字段的查询条件。
     * 
     * @param key 查询字段名
     * @param value 匹配字符串值
     * @return 当前ProcessQueryBuilder实例
     */
    public AbstractProcessQueryBuilder put(String key, String value) {
        return put(key, ParamType.STRING, value, null);
    }
    
    /**
     * 指定一个字符串字段的查询条件。
     * 
     * @param key 查询字段名
     * @param value 匹配字符串数组，数组成员之间是“或”的关系。
     * @return 当前ProcessQueryBuilder实例
     */
    public AbstractProcessQueryBuilder put(String key, String[] value) {
        return put(key, ParamType.STRING_ARRAY, value, null);
    }
    
    /**
     * 指定一个日期型字段的查询条件。
     * 
     * @param key 查询字段名
     * @param date 匹配日期，在输入参数表示的当天内，任何时间都算作符合条件
     * @return 当前ProcessQueryBuilder实例
     */
    public AbstractProcessQueryBuilder put(String key, Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        Date start = calendar.getTime();
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        Date end = calendar.getTime();
        return put(key, start, end);
    }
    
    /**
     * 指定一个日期型字段的查询条件。
     * 
     * @param key 查询字段名
     * @param start 匹配的时间下限
     * @param end 匹配的时间上限
     * @return 当前ProcessQueryBuilder实例
     */
    public AbstractProcessQueryBuilder put(String key, Date start, Date end) {
        return put(key, ParamType.TIME, null, new Bounds(start, end));
    }
    
    /**
     * 指定一个整型字段的查询条件。
     * 
     * @param key 查询字段名
     * @param value 匹配整型值
     * @return 当前ProcessQueryBuilder实例
     */
    public AbstractProcessQueryBuilder put(String key, int value) {
        return put(key, ParamType.INT, value, null);
    }
    
    /**
     * 指定一个整型字段的查询条件。
     * 
     * @param key 查询字段名
     * @param value 匹配整型数组，数组成员之间是“或”的关系。
     * @return 当前ProcessQueryBuilder实例
     */
    public AbstractProcessQueryBuilder put(String key, int[] value) {
        return put(key, ParamType.INT_ARRAY, value, null);
    }
    
    /**
     * 指定一个整型字段的查询条件。
     * 
     * @param key 查询字段名
     * @param value 匹配整型数组，数组成员之间是“或”的关系。
     * @return 当前ProcessQueryBuilder实例
     */
    public AbstractProcessQueryBuilder put(String key, Integer[] value) {
        return put(key, ParamType.INT_ARRAY, value, null);
    }
    
    /**
     * 指定一个整型字段的查询条件。
     * 
     * @param key 查询字段名
     * @param min 匹配的整型数值下限
     * @param max 匹配的整型数值上限
     * @return 当前ProcessQueryBuilder实例
     */
    public AbstractProcessQueryBuilder put(String key, int min, int max) {
        return put(key, ParamType.INT, null, new Bounds(min, max));
    }
    
    /**
     * 指定一个双精度型字段的查询条件。FileNet中显示为浮点型。
     * 
     * @param key 查询字段名
     * @param value 匹配双精度型值
     * @return 当前ProcessQueryBuilder实例
     */
    public AbstractProcessQueryBuilder put(String key, double value) {
        return put(key, ParamType.FLOAT, value, null);
    }
    
    /**
     * 指定一个双精度型字段的查询条件。FileNet中显示为浮点型。
     * 
     * @param key 查询字段名
     * @param value 匹配整型数组，数组成员之间是“或”的关系。
     * @return 当前ProcessQueryBuilder实例
     */
    public AbstractProcessQueryBuilder put(String key, double[] value) {
        return put(key, ParamType.FLOAT_ARRAY, value, null);
    }
    
    /**
     * 指定一个双精度型字段的查询条件。FileNet中显示为浮点型。
     * 
     * @param key 查询字段名
     * @param min 匹配的双精度型数值下限
     * @param max 匹配的双精度型数值上限
     * @return 当前ProcessQueryBuilder实例
     */
    public AbstractProcessQueryBuilder put(String key, double min, double max) {
        return put(key, ParamType.FLOAT, null, new Bounds(min, max));
    }
    
    /**
     * 指定一个布尔型字段的查询条件。
     * 
     * @param key 查询字段名
     * @param value 匹配布尔型值
     * @return 当前ProcessQueryBuilder实例
     */
    public AbstractProcessQueryBuilder put(String key, boolean value) {
        return put(key, ParamType.BOOLEAN, value, null);
    }
    
    /**
     * 根据输入的字段产生查询条件。
     * 
     * @return 可供查询服务使用的查询条件对象，每次调用都会产生新的Query对象。
     */
    public abstract Query buildQuery();
    
    //=========================================================================
    // 内部实现
    //=========================================================================
    
    protected void buildEntry(QueryProxy query, Entry entry) {
        switch (entry.type) {
        case INT:
        case FLOAT:
        case TIME:
            if (entry.getValue() != null) {
                buildSingleEntry(query, entry);
            } else if (entry.getBounds() != null) {
            	/* added by lixf start */
            	//以下代码目的是给将结束日期变为当天的日期的23点59分59秒
            	Date date = (Date) entry.getBounds().getUpperBound();
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);
                calendar.set(Calendar.HOUR_OF_DAY, 23);
                calendar.set(Calendar.MINUTE, 59);
                calendar.set(Calendar.SECOND, 59);
                calendar.set(Calendar.MILLISECOND, 999);
                entry.getBounds().upperBound = calendar.getTime();
                /* added by lixf end */
                query.appendFilterKey(entry.getKey())
                    .appendFilter(" >= :")
                    .appendFilter(entry.getKey())
                    .appendFilter(placeHolderSuffix)
                    .appendFilter(LOWER_BOUND)
                    .appendFilter(" AND ")
                    .appendFilterKey(entry.getKey())
                    .appendFilter(" <= :")
                    .appendFilter(entry.getKey())
                    .appendFilter(placeHolderSuffix)
                    .appendFilter(UPPER_BOUND);
                query.addSubstitutionVar(entry.getBounds().getLowerBound())
                    .addSubstitutionVar(entry.getBounds().getUpperBound());
            }
            break;
            
        case STRING:
            if (entry.getValue() != null) {
                if (isFuzzy() && !FIELD_PROCESS_NAME.equals(entry.getKey())) {
                    query.appendFilterKey(entry.getKey())
                        .appendFilter(" LIKE :")
                        .appendFilter(entry.getKey())
                        .appendFilter(placeHolderSuffix);
                    query.addSubstitutionVar("%" + entry.getValue() + "%");
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
    protected void buildSingleEntry(QueryProxy query, Entry entry) {
        query.appendFilterKey(entry.getKey())
            .appendFilter(" = :")
            .appendFilter(entry.getKey())
            .appendFilter(placeHolderSuffix);
        query.addSubstitutionVar(entry.getValue());
    }
    
    /**
     * 构造多值条目的“或”查询
     */
    protected void buildArrayEntry(QueryProxy query, Entry entry) {
        String key = entry.getKey();
        Object value = entry.getValue();
        int length = Array.getLength(value);
        
        for (int i = 0; i < length; i++) {
            if (i == 0) {
                query.appendFilter("(");
            }
            query.appendFilterKey(key)
                .appendFilter(" = :")
                .appendFilter(key)
                .appendFilter(placeHolderSuffix)
                .appendFilter("_")
                .appendFilter(String.valueOf(i));
            if (i == (length - 1)) {
                query.appendFilter(")");
            } else {
                query.appendFilter(" OR ");
            }
            
            Object item = Array.get(value, i);
            query.addSubstitutionVar(item);
        }
    }
    
    protected AbstractProcessQueryBuilder put(String key, ParamType type, Object value, Bounds bounds) {
        if (value == null && bounds == null) {
            throw new IllegalArgumentException("Value of query condition (key: '" 
                    + key + "') cannot be null.");
        }
        
        if (bounds == null && value.getClass().isArray() && Array.getLength(value) == 0) {
            throw new IllegalArgumentException("Array value of query condition (key: '" 
                    + key + "') cannot be empty.");
        }
        
        queryConditions.put(key, new Entry(key, type, value, bounds));
        return this;
    }
    
    protected boolean putOrRemove(String key, Object value) {
        /* updated by lixuf 20120218 start*/
    	//if (value == null) {
    	if (value == null || "".equals(value)) {
    	/* updated by lixuf 20120218 end*/
            queryConditions.remove(key);
            return false;
        } else {
            put(key, ParamType.STRING, value, null);
            return true;
        }
    }
    
    //=========================================================================
    // 内部类
    //=========================================================================
    
    /**
     * 一条独立的字段查询条件。
     */
    protected static class Entry {
        private String key;
        private ParamType type;
        private Object value;
        private Bounds bounds;
        
        public Entry(String key, ParamType type, Object value, Bounds bounds) {
            this.key = key;
            this.type = type;
            this.value = value;
            this.bounds = bounds;
        }

        public String getKey() {
            return key;
        }

        public ParamType getType() {
            return type;
        }

        public Object getValue() {
            return value;
        }

        public Bounds getBounds() {
            return bounds;
        }
    }
    
    /**
     * 值的上下限。
     */
    protected static class Bounds {
        private Object lowerBound;
        private Object upperBound;
        
        public Bounds(Object lowerBound, Object upperBound) {
            this.lowerBound = lowerBound;
            this.upperBound = upperBound;
        }
        
        public Object getLowerBound() {
            return lowerBound;
        }
        
        public Object getUpperBound() {
            return upperBound;
        }
    }
    
    /**
     * 字段类型。
     */
    protected static enum ParamType {
        INT, 
        INT_ARRAY,
        STRING, 
        STRING_ARRAY,
        BOOLEAN, 
        FLOAT, 
        FLOAT_ARRAY, 
        TIME, 
        ATTACHMENT, 
        PARTICIPANT
    }
}
