package com.ibm.eu.services.base.tracker;

import java.awt.Point;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import filenet.vw.api.VWMapNode;
import filenet.vw.api.VWRouteDefinition;
import filenet.vw.api.VWWorkflowDefinition;

public class StepBuilder extends Builder {

    protected static final String K_routes = "routes";
    protected static final String K_x_coordinate = "x_coordinate";
    protected static final String K_y_coordinate = "y_coordinate";

    private VWMapNode vwStep;
    private VWWorkflowDefinition vwDef;

    public StepBuilder(VWMapNode vwMapNode, VWWorkflowDefinition vwDef) {
        this.vwStep = vwMapNode;
        this.vwDef = vwDef;
    }

    @Override
    public JSONObject build() {
        try {
            JSONObject root = new JSONObject();
            root.put(K_id, vwStep.getStepId());
            root.put(K_name, vwStep.getName());
            root.put(K_displayName, vwStep.getName());
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
}
