/*******************************************************************************
 * Copyright (c) 2006, 2008 IBM Corporation and Others
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Kentarou FUKUDA - initial API and implementation
 *******************************************************************************/
package org.eclipse.actf.examples.adesigner.eval.html.internal;

import org.eclipse.osgi.util.NLS;

import java.nio.charset.StandardCharsets;

public final class Messages extends NLS {

	private static final String BUNDLE_NAME = "org.eclipse.actf.examples.adesigner.eval.html.internal.messages";//$NON-NLS-1$

	private Messages() {
		// Do not instantiate
	}

	public static String CheckEngine_ChildTable;
	public static String CheckEngine_TieredChildTable;
	public static String CheckEngine_Headings;
	public static String StyleElement;
	public static String Selector;
	public static String Dynamic;
	

	static {
		NLS.initializeMessages(BUNDLE_NAME, Messages.class, StandardCharsets.UTF_8);
	}
}