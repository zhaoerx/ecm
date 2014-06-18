package com.ibm.eu.services.base.tracker;
/**
 * 
 * <h1>Introduction</h1>
 * 
 * Design a process tracker which can be customized for different business
 * applications. By default, it functions like the built-in process tracker. But
 * it is highly customizable.
 * <p>
 * 
 * <h1>Design</h1>
 * 
 * How to display process in browser?
 * <p>
 * Choose 4 since it is easy to use. We may need to re-implement it when we put
 * it into product.
 * <ol>
 * <li>Java applet: too heavy, not well integrated with web apps.</li>
 * <li>HTML canvas: Only HTML5 supports canvas.</li>
 * <li>Dojo drawing API: only provides primitive functions.</li>
 * <li>Flex: widely used IRA.</li>
 * </ol>
 * 
 * Use FileNet VW API to access workflow definition since PEP format is not
 * meant to be parsed directly by applications.
 * 
 * Java side will use VW API (both definition and runtime) to access workflow
 * definition and build a json object. Flex side will parse the json object to
 * display the workflow. Events occurred in Flex UI triggers Java side actions
 * with the use of Ajax.
 * 
 * <h1>Dev</h1>
 * <h2>Package Description</h2>
 * <ul>
 * <li>simple-definition.json: sample json created for workflow definition.</li>
 * <li>simple-runtime.json: sample json created for workflow runtime.</li>
 * <li>simple.pep: a sample worklow definition which is also stored as
 * <tt>fnos1 -- workflow -- shanghai-bank -- simple</tt> in filenet45.</li>
 * <li>流动资金贷款申请审批.pep: workflow defintion used in Shanghai Bank POC</li>
 * <li>workflow.png: screenshot for <tt>simple.def</tt>.</li>
 * </ul>
 * <h2>Packaging</h2> This package will move into an IVY module.
 */
