package com.ibm.eu.services.base.tracker;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import filenet.vw.api.VWActionType;
import filenet.vw.api.VWCompoundStepDefinition;
import filenet.vw.api.VWException;
import filenet.vw.api.VWInstructionDefinition;
import filenet.vw.api.VWMapDefinition;
import filenet.vw.api.VWMapNode;
import filenet.vw.api.VWNodeType;
import filenet.vw.api.VWRouteDefinition;
import filenet.vw.api.VWSimpleInstruction;
import filenet.vw.api.VWStepDefinition;
import filenet.vw.api.VWWorkflowDefinition;

public class CallStepBuilder extends Builder {

    protected static final String K_routes = "routes";
    protected static final String K_x_coordinate = "x_coordinate";
    protected static final String K_y_coordinate = "y_coordinate";

    private VWMapNode vwStep;
    private VWWorkflowDefinition vwDef;

    public CallStepBuilder(VWMapNode vwMapNode, VWWorkflowDefinition vwDef) {
        this.vwStep = vwMapNode;
        this.vwDef = vwDef;
    }

    @Override
    public JSONObject build() {
        try {
            JSONObject root = new JSONObject();
            
            List<VWMapNode> submapMainSteps = getSubmapMainSteps(vwStep);
            // 这里把Submap中人工环节叠加起来来替换当前Submap Step，但位置保持不变。需要更改前端处理方式，暂不实现。
//            List<String> submapMainStepNames = new ArrayList<String>();
//            List<String> submapMainStepDisplayNames = new ArrayList<String>();
//            for (VWMapNode subStep : submapMainSteps) {
//                submapMainStepNames.add(subStep.getName());
//                submapMainStepDisplayNames.add(TrackerDataBuilderUtil.getStepDisplayName(subStep.getName()));
//            }
//            root.put(K_name, (String[]) submapMainStepNames.toArray(new String[submapMainStepNames.size()]));
//            root.put(K_displayName, (String[]) submapMainStepDisplayNames.toArray(new String[submapMainStepDisplayNames.size()]));
            // 这里简化人工节点的逻辑，认为Submap中只有一个人工节点。
            root.put(K_name, submapMainSteps.get(0).getName());
            root.put(K_displayName, submapMainSteps.get(0).getName());
            
            root.put(K_id, vwStep.getStepId());
            Point point = vwStep.getLocation();
            root.put(K_x_coordinate, point.getX());
            root.put(K_y_coordinate, point.getY());

            // routes
            JSONArray routesNode = new JSONArray();
            VWRouteDefinition[] vwRoutes = vwStep.getNextRoutes();
            if (vwRoutes != null)
                for (VWRouteDefinition vwr : vwRoutes) {
                    RouteBuilder routeBuider = new RouteBuilder(vwr);
                    routesNode.add(routeBuider.build());
                }
            root.put(K_routes, routesNode);

            return root;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    /**
     * 返回对应Submap中的所有人工环节。
     * 
     * TODO：这里没有额外处理流程内多次调用同一Submap的情况。
     */
    private List<VWMapNode> getSubmapMainSteps(VWMapNode vwStep) throws VWException {
        List<VWMapNode> nodes = new ArrayList<VWMapNode>();
        VWInstructionDefinition[] vwInsDefs = ((VWCompoundStepDefinition) vwStep).getInstructions();
        
        String submapName = null;
        for (VWInstructionDefinition vwInsDef : vwInsDefs) {
            if (vwInsDef instanceof VWSimpleInstruction && ((VWSimpleInstruction) vwInsDef).getAction() == VWActionType.ACTION_TYPE_CALL) {
                submapName = ((VWSimpleInstruction) vwInsDef).getParams()[0];
                break;
            }
        }
        
        VWMapDefinition submap = vwDef.getMap(submapName);
        for (VWMapNode node : submap.getSteps()) {
            if (node.getNodeType() == VWNodeType.NODE_TYPE_OTHER && node instanceof VWStepDefinition) {
                nodes.add(node);
            }
        }
        
        return nodes;
    }
}
