/*******************************************************************************
 * Copyright (c) 2007, 2008 IBM Corporation and Others
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Kentarou FUKUDA - initial API and implementation
 *******************************************************************************/

package org.eclipse.actf.util.logging;

/**
 * Utility class to print logs into console in debug or development mode.
 */
public class DebugPrintUtil {
	private static boolean IN_DEBUG = System.getProperty("debug") != null;
	private static boolean IN_DEV = System.getProperty("develop") != null;
	private static boolean IN_DEV_OR_DEBUG = IN_DEBUG || IN_DEV;

	/**
	 * Print target Object to console in debug mode.
	 * 
	 * @param target
	 *            the target Object to print
	 */
	public static void debugPrintln(Object target) {
		if (IN_DEBUG) {
			System.out.println(target);
		}
	}

	/**
	 * Print target Object to console in development mode.
	 * 
	 * @param target
	 *            the target Object to print
	 */
	public static void devPrintln(Object target) {
		if (IN_DEBUG) {
			System.out.println(target);
		}
	}

	/**
	 * Print target Object to console in debug or development mode.
	 * 
	 * @param target
	 *            the target Object to print
	 */
	public static void devOrDebugPrintln(Object target) {
		if (IN_DEV_OR_DEBUG) {
			System.out.println(target);
		}
	}

	/**
	 * Print stack trace of target Exception in debug mode.
	 * 
	 * @param e
	 *            the target Exception
	 */
	public static void debugPrintStackTrace(Exception e) {
		if (IN_DEBUG) {
			e.printStackTrace();
		}
	}

	/**
	 * Print stack trace of target Exception in development mode.
	 * 
	 * @param e
	 *            the target Exception
	 */
	public static void devPrintStackTrace(Exception e) {
		if (IN_DEV) {
			e.printStackTrace();
		}
	}

	/**
	 * Print stack trace of target Exception in debug or development mode.
	 * 
	 * @param e
	 *            the target Exception
	 */
	public static void devOrDebugPrintStackTrace(Exception e) {
		if (IN_DEV_OR_DEBUG) {
			e.printStackTrace();
		}
	}

}
