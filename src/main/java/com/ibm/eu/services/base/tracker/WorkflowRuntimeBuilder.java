package com.ibm.eu.services.base.tracker;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;

import com.ibm.eu.constants.P8BpmConstants;
import com.ibm.eu.services.base.query.HistoricalWorkItem;
import com.ibm.eu.services.base.query.HistoricalWorkflow;
import com.ibm.eu.services.base.query.ProcessHistoryService;

import filenet.vw.api.VWException;
import filenet.vw.api.VWSession;

public class WorkflowRuntimeBuilder extends Builder {
    protected static final String K_params = "params";
    protected static final String K_SEQUENCE_IN_WORKFLOW = "_sequenceInWorkflow";

    private VWSession vwSession;
    private String[] fieldNames;
    private HistoricalWorkflow hwf;

    public WorkflowRuntimeBuilder(VWSession vwSession, String processName,
            String workflowNumber, String... fieldNames) throws VWException {
        this.vwSession = vwSession;
        this.fieldNames = fieldNames;
        String eventLogName = P8BpmConstants.DEFAULT_EVENT_LOG_NAME;
        ProcessHistoryService service = new ProcessHistoryService();
        hwf = service.fetchWorkflow(vwSession, eventLogName, workflowNumber,
                fieldNames);
    }

    @Override
    public JSONObject build() {
        try {
            JSONObject root = new JSONObject();
            root.put(K_no, hwf.getWorkflowNumber());

            // TODO support multiple maps
            JSONArray mapsNode = new JSONArray();
            JSONObject mainMapNode = new JSONObject();

            // TODO set work object numbers for all the work items in this workflow
            JSONArray stepsNode = addHisotry(hwf, fieldNames);
            mainMapNode.put(K_steps, stepsNode);

            mapsNode.add(mainMapNode);
            root.put(K_maps, mapsNode);
            
            // 打时间戳，目的是每次刷新流程历史都是唯一的，前端会再走一次动画。
            root.put("_timestamp", System.currentTimeMillis());

            return root;
        } catch (VWException e) {
            throw new RuntimeException(e);
        }
    }

    JSONArray addHisotry(HistoricalWorkflow hwf, String... fieldNames)
            throws VWException {
        // runtime: participant, received, dispatched, status, response, comments
        // data field values. (stepName)
        Collections.sort(hwf.getWorkItems(), new HistoricalWorkItemSequenceComparator());
        setSequenceInWorkflow(hwf.getWorkItems());
        Map<String, List<HistoricalWorkItem>> stepNameToWorkItemMap = getWorkItemsByStep(hwf.getWorkItems());
        
        JSONArray stepsNode = new JSONArray();
        for (String stepName : stepNameToWorkItemMap.keySet()) {
            JSONObject stepNode = new JSONObject();

            stepNode.put(K_name, stepName);
            
            JSONArray historyNode = new JSONArray();
            for (HistoricalWorkItem hwi : stepNameToWorkItemMap.get(stepName)) {
                // TODO handle multiple events for a history in a step
                JSONObject eventNode = new JSONObject();

                eventNode.put(K_SEQUENCE_IN_WORKFLOW, hwi.getFieldValue(K_SEQUENCE_IN_WORKFLOW));
                eventNode.put("user", vwSession.convertIdToUserNamePx(hwi.getUserId()).getDisplayName());
                eventNode.put("timestamp", hwi.getTimestamp().getTime());
                eventNode.put("enqueueTime", hwi.getEnqueueTime().getTime());
                eventNode.put("selectedResponse", hwi.getSelectedResponse());
                eventNode.put("sequenceNumber", hwi.getSequenceNumber());
                
                JSONArray paramsNode = new JSONArray();
                for (String fn : fieldNames) {
                    String value = String.valueOf(hwi.getFieldValue(fn));
                    addParam(paramsNode, fn, value);
                }
                eventNode.put(K_params, paramsNode);

                historyNode.add(eventNode);
                
                // 标识当前step, 前端认为带着WobNum即为当前步骤
                if (hwi.getEventType() == P8BpmConstants.LOG_TYPE_STEP_QUEUED) {
                    stepNode.put(K_no, hwi.getWobNum());
                }
            }
            stepNode.put(K_history, historyNode);
            
            stepsNode.add(stepNode);
        }

        return stepsNode;
    }

    private static void addParam(JSONArray paramsNode, String name, String value) {
        JSONObject paramNode = new JSONObject();
        paramNode.put(K_type, T_string);
        paramNode.put(K_name, name);
        paramNode.put(K_value, value);
        paramsNode.add(paramNode);
    }
    
    /**
     * 为每一个历史任务按顺序编号，从1开始。
     */
    private static void setSequenceInWorkflow(List<HistoricalWorkItem> hwis) {
        for (HistoricalWorkItem hwi : hwis) {
            // TODO 暂用DataField集合，应该建立新字段。
            hwi.putFieldValue(K_SEQUENCE_IN_WORKFLOW, hwis.indexOf(hwi) + 1);
        }
    }
    
    private static Map<String, List<HistoricalWorkItem>> getWorkItemsByStep(List<HistoricalWorkItem> hwis) {
        Map<String, List<HistoricalWorkItem>> map = new HashMap<String, List<HistoricalWorkItem>>();
        for (HistoricalWorkItem hwi : hwis) {
            String stepName = hwi.getStepName();
            if (!map.containsKey(stepName)) {
                map.put(stepName, new ArrayList<HistoricalWorkItem>());
            }
            map.get(stepName).add(hwi);
        }
        return map;
    }
    
    protected static class HistoricalWorkItemSequenceComparator implements Comparator<HistoricalWorkItem> {
        public int compare(HistoricalWorkItem o1, HistoricalWorkItem o2) {
            return o1.getSequenceNumber() < o2.getSequenceNumber() ? -1 : 1;
        }
    }
}
