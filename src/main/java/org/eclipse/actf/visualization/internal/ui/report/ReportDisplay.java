/*******************************************************************************
 * Copyright (c) 2004, 2008 IBM Corporation and Others
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Kentarou FUKUDA - initial API and implementation
 *******************************************************************************/
package org.eclipse.actf.visualization.internal.ui.report;

//import java.io.File;
//
//import org.eclipse.actf.util.logging.DebugPrintUtil;
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
//public class ReportDisplay {
//
//	//old implementation
//
//    private final static String WEBBROWSER_CONTROL_NAME = "Shell.Explorer"; //$NON-NLS-1$
//    private final static String NAVIGATE_METHOD_NAME = "Navigate"; //$NON-NLS-1$
//    private final static String URL_PARAM_NAME = "URL"; //$NON-NLS-1$
//
//
//    private OleFrame webFrame;
//    private OleControlSite webControlSite;
//    private OleAutomation oleAutomation;
//    private int dispIdMember;
//    int[] rgdispidNamedArgs = new int[1];
//
//
//    public void disposeOleBrowser() {
//        if (oleAutomation != null) {
//            oleAutomation.dispose();
//            oleAutomation = null;
//        }
//    }
//
//    public ReportDisplay(Composite _compositeParent) {
//
//        Composite compositeRecommendationHalf = new Composite(_compositeParent, SWT.NONE);
//        GridLayout gridLayout = new GridLayout();
//        gridLayout.numColumns = 1;
//        gridLayout.marginWidth = 0;
//        gridLayout.marginHeight = 0;
//        gridLayout.verticalSpacing = 0;
//        gridLayout.horizontalSpacing = 0;
//        compositeRecommendationHalf.setLayout(gridLayout);
//
//        webFrame = new OleFrame(compositeRecommendationHalf, SWT.NONE);
//        webControlSite = new OleControlSite(webFrame, SWT.NONE, WEBBROWSER_CONTROL_NAME);
//        oleAutomation = new OleAutomation(webControlSite);
//
//        // activate OLE
//        if (!(webControlSite.doVerb(OLE.OLEIVERB_INPLACEACTIVATE) == OLE.S_OK)) {
//            DebugPrintUtil.devOrDebugPrintln("ole init failure"); //$NON-NLS-1$
//        }
//
//        if (oleAutomation != null) {
//            int[] rgdispid = oleAutomation.getIDsOfNames(new String[] { NAVIGATE_METHOD_NAME, URL_PARAM_NAME });
//            dispIdMember = rgdispid[0];
//            int[] rgdispidNamedArgs = new int[1];
//            rgdispidNamedArgs[0] = rgdispid[1];
//        }
//
//        GridData gridData = new GridData();
//        gridData.horizontalAlignment = GridData.FILL;
//        gridData.grabExcessHorizontalSpace = true;
//        gridData.verticalAlignment = GridData.FILL;
//        gridData.grabExcessVerticalSpace = true;
//        webFrame.setLayoutData(gridData);
//
//        displayReportFile(""); //$NON-NLS-1$
//    }
//
//    public void displayReportFile(String url) {
//        File targetFile = new File(url);
//        if (!targetFile.exists()) {
//            url = "about:blank"; //$NON-NLS-1$
//        }
//        if (oleAutomation != null) {
//            Variant[] rgvarg = new Variant[1];
//            rgvarg[0] = new Variant(url);
//            //Variant pVarResult =
//            oleAutomation.invoke(dispIdMember, rgvarg, rgdispidNamedArgs);
//        }
//    }
//
//    public OleControlSite getOleControleSite() {
//        return webControlSite;
//    }
//
//}
