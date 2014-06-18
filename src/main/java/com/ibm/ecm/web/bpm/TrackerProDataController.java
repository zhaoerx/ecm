package com.ibm.ecm.web.bpm;

import java.io.IOException;
import java.net.URLDecoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ibm.ecm.web.AbstractRestController;
import com.ibm.eu.services.base.tracker.Builder;
import com.ibm.eu.services.base.tracker.WorkflowDefinitionBuilder;
import com.ibm.eu.services.base.tracker.WorkflowRuntimeBuilder;

import filenet.vw.api.VWException;
import filenet.vw.api.VWSession;

/**
 * @author Erxing
 *
 * /trackerProData.do?type=definition&processName=<processName>
 * /trackerProData.do?type=history&processName=<processName>&workflowNumber=<workflowNumber>&fieldNames=[fieldNames]
 */
@Controller
@RequestMapping("/trackerProData")
public class TrackerProDataController extends AbstractRestController {

    protected final Logger log = Logger.getLogger(getClass());

    private static final String ENCODING = "UTF-8";
    private static final String TYPE_HISTORY = "history";
    private static final String TYPE_DEFINITION = "definition";
    
    @Override
    protected void process(HttpServletRequest req, HttpServletResponse res)
            throws Exception {
        req.setCharacterEncoding(ENCODING);
        res.setCharacterEncoding(ENCODING);
        usePeInput(req, res);
    }

    private void usePeInput(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, VWException {
        String type = req.getParameter("type");
        String workflowNumber = req.getParameter("workflowNumber");
        String processName = req.getParameter("processName");
        
       // processName = URLDecoder.decode(processName, ENCODING);
        String fieldNames = req.getParameter("fieldNames");
        String[] fieldNamesArray = {};
        if (fieldNames != null) {
            fieldNamesArray = fieldNames.split(",");
        }
        if (type.equals(TYPE_HISTORY)) {
        	resp.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        }
        VWSession vwSession = getVWSession();
        Builder builder = getBuilder(vwSession, processName, workflowNumber, type, fieldNamesArray);
        JSONObject rootNode = builder.build();
        writeResponse(rootNode.toString(), resp);
    }

    private Builder getBuilder(VWSession vwSession, String processName, String workflowNumber, String type, String[] fieldNames) throws VWException  {
        if (type.equals(TYPE_DEFINITION)) {
            return new WorkflowDefinitionBuilder(vwSession, processName);
        } else if (type.equals(TYPE_HISTORY)) {
            return new WorkflowRuntimeBuilder(vwSession, processName, workflowNumber, fieldNames);
        } else {
            throw new IllegalArgumentException("unsupported type '" + type
                    + "'");
        }
    }
}
