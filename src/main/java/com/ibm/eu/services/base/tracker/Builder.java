package com.ibm.eu.services.base.tracker;

import net.sf.json.JSONObject;

public abstract class Builder {

  protected static final String K_id = "id";
  protected static final String K_name = "name";
  protected static final String K_displayName = "displayName";
  protected static final String K_type = "type";
  protected static final String K_value = "value";
  protected static final String K_steps = "steps";
  protected static final String K_maps = "maps";

  protected static final String T_string = "string";
  protected static final String T_boolean = "boolean";
  protected static final String T_int = "int";
  protected static final String T_date = "time";
  protected static final String T_participant = "participant";
  
  // data field type float is java double
  protected static final String T_float = "float";

  protected static final String K_no = "no";
  protected static final String K_history = "history";
  
  // add work object number and workflow number
  // histories
  
  //protected static final ObjectMapper mapper = JsonUtil.getObjectMapper();

  public abstract JSONObject build();
}
