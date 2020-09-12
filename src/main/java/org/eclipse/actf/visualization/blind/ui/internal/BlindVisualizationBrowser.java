/*******************************************************************************
 * Copyright (c) 2004, 2011 IBM Corporation and Others
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Kentarou FUKUDA - initial API and implementation
 *******************************************************************************/

package org.eclipse.actf.visualization.blind.ui.internal;

//import org.eclipse.swt.SWT;
//import org.eclipse.swt.layout.GridData;
//import org.eclipse.swt.layout.GridLayout;
//import org.eclipse.swt.ole.win32.OLE;
//import org.eclipse.swt.ole.win32.OleAutomation;
//import org.eclipse.swt.ole.win32.OleControlSite;
//import org.eclipse.swt.ole.win32.OleFrame;
//import org.eclipse.swt.ole.win32.Variant;
//import org.eclipse.swt.widgets.Composite;
//
//
//public class BlindVisualizationBrowser {
//
//    private final static String WEBBROWSER_CONTROL_NAME = "Shell.Explorer"; //$NON-NLS-1$
//
//    private final static String NAVIGATE_METHOD_NAME = "Navigate"; //$NON-NLS-1$
//
//    private final static String URL_PARAM_NAME = "URL"; //$NON-NLS-1$
//
//    private final static Variant[] ARG_CLEAR_HIGHLIGHT = new Variant[] { new Variant("clearHighlight();") }; //$NON-NLS-1$
//
//    private OleAutomation oleAutomationIE;
//
//    private OleAutomation ieWindowAutomation = null;
//
//    private int id_exec_script;
//
//    private int id_navigate;
//
//    private int[] id_url_param;
//
//    public BlindVisualizationBrowser(Composite parent) {
//
//        GridLayout gridLayout = new GridLayout();
//        gridLayout.marginHeight = gridLayout.marginWidth = 0;
//        gridLayout.horizontalSpacing = gridLayout.verticalSpacing = 0;
//        parent.setLayout(gridLayout);
//
//        // Web browser
//        OleFrame webFrame = new OleFrame(parent, SWT.NONE);
//        OleControlSite webControlSite = new OleControlSite(webFrame, SWT.NONE, WEBBROWSER_CONTROL_NAME);
//        oleAutomationIE = new OleAutomation(webControlSite);
//
//        // enable OLE
//        webControlSite.doVerb(OLE.OLEIVERB_INPLACEACTIVATE);
//
//        // init ids
//        int[] rgdispid = oleAutomationIE.getIDsOfNames(new String[] { NAVIGATE_METHOD_NAME, URL_PARAM_NAME });
//        id_navigate = rgdispid[0];
//        id_url_param = new int[1];
//        id_url_param[0] = rgdispid[1];
//
//        GridData gridData2 = new GridData();
//        gridData2.horizontalAlignment = GridData.FILL;
//        gridData2.grabExcessHorizontalSpace = true;
//        gridData2.verticalAlignment = GridData.FILL;
//        gridData2.grabExcessVerticalSpace = true;
//        webFrame.setLayoutData(gridData2);
//
//    }
//
//    protected void setBrowserSilent() {
//        int[] rgdispid = oleAutomationIE.getIDsOfNames(new String[] { "Silent" }); //$NON-NLS-1$
//        int dispIdMember = rgdispid[0];
//        Variant varSilent = new Variant((short) 1); // set to true (0 = false)
//        oleAutomationIE.setProperty(dispIdMember, varSilent);
//    }
//
//    protected void disposeOleBrowser() {
//        if (oleAutomationIE != null) {
//            oleAutomationIE.dispose();
//        }
//    }
//
//    // Display the result HTML in blind IE
//    // Checked:
//    protected void navigate(String url) {
//
//        Variant[] rgvarg = new Variant[1];
//        rgvarg[0] = new Variant(url);
//
//        // Variant pVarResult =
//        oleAutomationIE.invoke(id_navigate, rgvarg, id_url_param);
//    }
//
//
//    private boolean initWindowAutomation() {
//        if (ieWindowAutomation == null) {
//            try {
//                // get IE WindowAutomation
//                int[] rgdispid = oleAutomationIE.getIDsOfNames(new String[] { "Document" }); //$NON-NLS-1$
//                int dispIdMember = rgdispid[0];
//                Variant result = oleAutomationIE.getProperty(dispIdMember);
//                OleAutomation htmlDocumentAutomation = result.getAutomation();
//                rgdispid = htmlDocumentAutomation.getIDsOfNames(new String[] { "parentWindow" }); //$NON-NLS-1$
//                dispIdMember = rgdispid[0];
//                result = htmlDocumentAutomation.getProperty(dispIdMember);
//                ieWindowAutomation = result.getAutomation();
//
//                rgdispid = ieWindowAutomation.getIDsOfNames(new String[] { "execScript" }); //$NON-NLS-1$
//                id_exec_script = rgdispid[0];
//            } catch (Exception e) {
//                return false;
//            }
//        }
//        return true;
//    }
//
//    protected void execScript(String str) {
//        if (initWindowAutomation()) {
//            Variant[] rgvarg = new Variant[1];
//            rgvarg[0] = new Variant(str);
//
//            if(ieWindowAutomation.invoke(id_exec_script, rgvarg)==null){
//            	//try again
//            	ieWindowAutomation = null;
//            	if(initWindowAutomation()){
//            		ieWindowAutomation.invoke(id_exec_script, rgvarg);
//            	}
//            }
//        }
//    }
//
//    protected void clearHighlight() {
//        if (initWindowAutomation()) {
//            ieWindowAutomation.invoke(id_exec_script, ARG_CLEAR_HIGHLIGHT);
//        }
//    }
//
//}
