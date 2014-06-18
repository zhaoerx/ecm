package com.ibm.eu.services.base.tracker;

import net.sf.json.JSONObject;
import filenet.vw.api.VWRouteDefinition;

public class RouteBuilder extends Builder {

  protected static final String K_source_id = "source_id";
  protected static final String K_target_id = "target_id";

  private VWRouteDefinition vwRoute;

  public RouteBuilder(VWRouteDefinition vwRoute) {
    this.vwRoute = vwRoute;
  }

  @Override
  public JSONObject build() {
    JSONObject root = new JSONObject();
    root.put(K_id, vwRoute.getRouteId());
    root.put(K_source_id, vwRoute.getSourceStepId());
    root.put(K_target_id, vwRoute.getDestinationStepId());
    return root;
  }

}
