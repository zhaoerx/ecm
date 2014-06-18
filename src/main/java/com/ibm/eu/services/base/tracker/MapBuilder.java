package com.ibm.eu.services.base.tracker;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import filenet.vw.api.VWActionType;
import filenet.vw.api.VWCompoundStepDefinition;
import filenet.vw.api.VWException;
import filenet.vw.api.VWInstructionDefinition;
import filenet.vw.api.VWMapDefinition;
import filenet.vw.api.VWMapNode;
import filenet.vw.api.VWSimpleInstruction;
import filenet.vw.api.VWWorkflowDefinition;

public class MapBuilder extends Builder {

    private VWMapDefinition vwMap;
    private VWWorkflowDefinition vwDef;

    public MapBuilder(VWMapDefinition vwMap, VWWorkflowDefinition vwDef) {
        this.vwMap = vwMap;
        this.vwDef = vwDef;
    }

    @Override
    public JSONObject build() {
        try {
            VWMapNode[] steps = vwMap.getSteps();
            JSONObject root = new JSONObject();
            root.put(K_name, vwMap.getName());

            JSONArray stepsNode = new JSONArray();
            for (VWMapNode node : steps) {
//            	if(node instanceof VWCompoundStepDefinition){
//            		//图形追踪中不显示系统节点
//            		continue;
//            	}
            	if(node.getName().equals("系统") || node.getName().equals("System")){
            		continue;
            	}
                Builder builder = null;
                if (isCallStep(node)) {
                    builder = new CallStepBuilder(node, vwDef);
                } else {
                    builder = new StepBuilder(node, vwDef);
                }
                stepsNode.add(builder.build());
            }
            root.put(K_steps, stepsNode);

            return root;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    private static boolean isCallStep(VWMapNode node) throws VWException {
        if (node instanceof VWCompoundStepDefinition) {
            VWInstructionDefinition[] vwInsDefs = ((VWCompoundStepDefinition) node).getInstructions();
            if (vwInsDefs != null) {
                for (VWInstructionDefinition vwInsDef : vwInsDefs) {
                    if (vwInsDef instanceof VWSimpleInstruction && ((VWSimpleInstruction) vwInsDef).getAction() == VWActionType.ACTION_TYPE_CALL) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
