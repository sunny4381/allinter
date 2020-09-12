/*******************************************************************************
 * Copyright (c) 2006, 2016 IBM Corporation and Others
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Kentarou FUKUDA - initial API and implementation
 *******************************************************************************/
package org.eclipse.actf.visualization.internal.engines.blind.html;

import compat.NLS;

import java.nio.charset.StandardCharsets;

public final class Messages extends NLS {

	private static final String BUNDLE_NAME = "messages";//$NON-NLS-1$

	private Messages() {
		// Do not instantiate
	}

	public static String BlindView_Open_ID;
	public static String CSSViewer_0;
	public static String CSSViewer_1;
	public static String ElementViewerJFace_0;
	public static String ElementViewerJFace_1;
	public static String ProgressBar_1;
	
	static {
		NLS.initializeMessages(BUNDLE_NAME, Messages.class, StandardCharsets.UTF_8);
	}
}