package com.ibm.eu.services.base.tracker;

import java.io.InputStream;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import filenet.vw.api.VWException;
import filenet.vw.api.VWFieldDefinition;
import filenet.vw.api.VWSession;
import filenet.vw.api.VWWorkflowDefinition;

public class WorkflowDefinitionBuilder extends Builder {

    private static final String ERROR_CODE = "ERR1512";

    protected final Logger logger = Logger.getLogger(getClass());

    protected static final String K_fields = "fields";

    private VWWorkflowDefinition wfDef;
    private JSONObject wfNode;

    public WorkflowDefinitionBuilder(InputStream is) {
        try {
            init(VWWorkflowDefinition.read(is));
        } catch (VWException e) {
            throw new RuntimeException(e);
        }
    }

    public WorkflowDefinitionBuilder(VWSession vwSession, String processName)
            throws VWException {
            init(vwSession.fetchWorkflowDefinition(-1, processName, false));
    }

    private void init(VWWorkflowDefinition wfDef) {
        this.wfDef = wfDef;
        wfNode = new JSONObject();
    }

    @Override
    public JSONObject build() {
        try {
            // workflow name
            wfNode.put(K_name, wfDef.getName());

            // fields
            buildFields(wfDef);

            // maps
            // TODO support multiple maps
            JSONArray maps = new JSONArray();
            MapBuilder mapBuilder = new MapBuilder(wfDef.getMainMap(), wfDef);
            maps.add(mapBuilder.build());
            // Put after added items, due to json-lib limitation.
            wfNode.put(K_maps, maps);

            return wfNode;
        } catch (VWException e) {
            throw new RuntimeException(e);
        }
    }

    private void buildFields(VWWorkflowDefinition wfDef) {
        try {
            JSONArray fields = new JSONArray();
            for (VWFieldDefinition vwField : wfDef.getFields()) {
                FieldBuilder fieldBuilder = new FieldBuilder(vwField);
                JSONObject fieldObject = fieldBuilder.build();
                if (fieldObject != null)
                    fields.add(fieldObject);
            }
            wfNode.put(K_fields, fields);
        } catch (VWException e) {
            throw new RuntimeException(e);
        }
    }
}
