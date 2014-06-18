/*
 * @copyright(disclaimer)
 *
 
 
 *
 
 
 
 *
 * @endCopyright
 */
package com.ibm.ecm.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.beanutils.BeanUtils;

/**
 * 
 * 
 * @version $Rev: 625 $ $Date: 2011-11-30 17:11:36 +0800 (星期�? 30 十一�?2011) $
 */
public class JsonUtil {
    /**
     * Private constructor
     */
    private JsonUtil() {
    }

    public static String toJson(Object obj) {
        if (obj instanceof Collection<?>) {
            return JSONArray.fromObject(obj).toString(2);
        } else {
            return JSONObject.fromObject(obj).toString(2);
        }
    }

    public static Map<String, Object> fromJson(String json) {
        JSONObject jsonObj = JSONObject.fromObject(json);
        return fromJsonObject(jsonObj);
    }

    @SuppressWarnings("unchecked")
    public static <T> T fromJson(String json, Class<T> cls) {

        return (T) JSONObject.toBean(JSONObject.fromObject(json), cls);
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static <T> T fromJson(String json, Class<T> cls, Map<String, Class> classMap) {
        return (T) JSONObject.toBean(JSONObject.fromObject(json), cls, classMap);
    }

    public static List<Object> fromJsonArray(String json) {
        JSONArray jsonArray = JSONArray.fromObject(json);
        return fromJsonArray(jsonArray);
    }

    @SuppressWarnings("unchecked")
    public static <T> List<T> fromJsonArray(String json, Class<T> cls) {
        return new ArrayList<T>(JSONArray.toCollection(JSONArray.fromObject(json), cls));
    }

    @SuppressWarnings("unchecked")
    private static Map<String, Object> fromJsonObject(JSONObject jsonObj) {
        Map<String, Object> result = new HashMap<String, Object>();

        for (Iterator<String> iter = jsonObj.keys(); iter.hasNext();) {
            String key = iter.next();
            Object value = jsonObj.get(key);
            if (value instanceof JSONObject) {
                value = fromJsonObject((JSONObject) value);
            } else if (value instanceof JSONArray) {
                value = fromJsonArray((JSONArray) value);
            }
            result.put(key, value);
        }

        return result;
    }

    @SuppressWarnings("unchecked")
    private static List<Object> fromJsonArray(JSONArray jsonArray) {
        List<Object> result = new ArrayList<Object>();

        for (Iterator<String> iter = jsonArray.iterator(); iter.hasNext();) {
            Object item = iter.next();
            if (item instanceof JSONObject) {
                item = fromJsonObject((JSONObject) item);
            } else if (item instanceof JSONArray) {
                item = fromJsonArray((JSONArray) item);
            }
            result.add(item);
        }

        return result;
    }
    
    
    public static void autoSetFieldsFromMap(Object object, Map fldValues) throws Exception {
		if (fldValues == null || fldValues.size() < 1) return;
		
		Class clazz = object.getClass();
        for (Iterator iter = fldValues.entrySet().iterator(); iter.hasNext();) {
            Map.Entry entry = (Map.Entry) iter.next();
            String fldName = (String) entry.getKey();
            Object value = entry.getValue();
            if (value == null) continue;
            
            BeanUtils.setProperty(object, fldName, value);
        }
    }
    
}
