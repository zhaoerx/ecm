package com.ibm.eu.services.base.tracker;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import filenet.vw.api.VWFieldDefinition;
import filenet.vw.api.VWFieldType;
import filenet.vw.api.VWParticipant;

public class FieldBuilder extends Builder {

    private Logger logger = Logger.getLogger(getClass());

    private JSONObject root;
    private VWFieldDefinition vwField;

    public FieldBuilder(VWFieldDefinition vwField) {
        this.vwField = vwField;
    }

    @Override
    public JSONObject build() {
        // TODO handle more vw field types

        String name = vwField.getName();
        int vwType = vwField.getFieldType();
        root = new JSONObject();
        root.put(K_name, name);
        if (name.equals("F_Trackers"))
            return null;
        switch (vwType) {
        case VWFieldType.FIELD_TYPE_STRING:
            set(T_string, vwField);
            break;
        case VWFieldType.FIELD_TYPE_BOOLEAN:
            set(T_boolean, vwField);
            break;
        case VWFieldType.FIELD_TYPE_INT:
            set(T_int, vwField);
            break;
        case VWFieldType.FIELD_TYPE_FLOAT:
            set(T_float, vwField);
            break;
        case VWFieldType.FIELD_TYPE_PARTICIPANT:
            set(T_participant, vwField);
            break;
        case VWFieldType.FIELD_TYPE_TIME:
            set(T_date, vwField);
            break;
        default:
            // throw new IllegalArgumentException("field " + name
            // + " has an unsupported vw field type " + vwType);
//            logger.error("Error occurred but not thrown",
//                    new IllegalArgumentException("field " + name
//                            + " has an unsupported vw field type " + vwType));
            return null;
        }
        return root;
    }

    private void set(String type, VWFieldDefinition vwField) {
        setType(type);
        setValue(vwField.getValue());
    }

    private void setValue(Object value) {
        if (value instanceof VWParticipant[]) {
            JSONArray usersNode = new JSONArray();
            for (VWParticipant par : (VWParticipant[]) value)
                if (par != null)
                    usersNode.add(par.getParticipantName());
            root.put(K_value, usersNode);
        } else
            root.put(K_value, String.valueOf(value));
    }

    private void setType(String type) {
        root.put(K_type, type);
    }
}
