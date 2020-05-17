/*******************************************************************************
* Copyright (c) 2004, 2008 IBM Corporation.
* All rights reserved. This program and the accompanying materials
* are made available under the terms of the Eclipse Public License v1.0
* which accompanies this distribution, and is available at
* http://www.eclipse.org/legal/epl-v10.html
*
* Contributors:
*  IBM Corporation - initial API and implementation
*******************************************************************************/ 

package org.eclipse.actf.util.logging;

import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * A set of static methods utilized by the Accessibility Tools Framework.
 * All parameters for the trace stream and level are also set
 * and maintained by this class
 *
 * @author Mike Squillace
 */
public final class LoggingUtil
{

	
	/**
	 * Logger names for the main plugins
	 */
	public static final String ACCSERVICE_LOGGER_NAME = "org.eclipse.actf.accservice";
	public static final String ACTF_CORE_LOGGER_NAME = "org.eclipse.actf.core";
	public static final String ACTF_VALIDATION_LOGGER_NAME = "org.eclipse.actf.validation";
	public static final String ACTF_JAVACO_LOGGER_NAME = "org.eclipse.actf.javaco";
	public static final String ACTF_WEBELO_LOGGER_NAME = "org.eclipse.actf.webelo";
	public static final String ACTF_UI_LOGGER_NAME = "org.eclipse.actf.ui";

	protected LoggingUtil () {
	}


	
	/**
	 * print the members of the given array.
	 * This method is typically used in the trace by methods that wish to
	 * output the list of parameters given to a constructor
	 * or method.
	 *
	 * @param logger -- the Logger instance to use
	 * @param level -- level at which to print
	 * @param params -- parameters to be printed
	 */
	public static void printParams(Logger logger, Object[] params) {
		if (logger != null) {
			logger.log(Level.FINE, "With parameters:");
			for (int p = 0; params != null && p < params.length; ++p) {
				logger
						.log(Level.FINE, (params[p] == null ? "" : params[p]
								.getClass().getName())
								+ ":"
								+ params[p]
								+ (p < params.length - 1 ? ", " : ""));
			}
		}

	} // printParams

	/**
	 * converts the first character of the given string to upper case
	 *
	 * @param str -- a string
	 * @return str with its first character converted to upper case
	 */
	public static String firstCharToUpper (String str) {
		return Character.toUpperCase(str.charAt(0)) + str.substring(1);
	}

	public static String arrayAsString (String[] str) {
		StringBuffer sb = new StringBuffer();
		for (int a = 0; a < str.length; ++a) {
			sb.append(str[a]);
			if (a < str.length - 1) {
				sb.append('\n');
			}
		}
		return sb.toString();
	}

	/**
	 * retrieve the root exception of the given Throwable
	 * @param t - throwable
	 * @return root exception of t or t if t has no cause
	 */
	public static Throwable getRootException (Throwable t) {
		Throwable root = t;
		while ((t = t.getCause()) != null) {
			root = t;
		}
		return root;
	}

	/**
	 * Replace XML specific characters with entities.
	 * @param s text to fix
	 */
	public static String escape (String s) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if (c == '\"') {
				sb.append("&quote;");
			}else if (c == '\'') {
				sb.append("&apos;");
			}else if (c == '&') {
				sb.append("&amp;");
			}else if (c == '<') {
				sb.append("&lt;");
			}else if (c == '>') {
				sb.append("&gt;");
			}else if (c < ' ' || c > 0x7E) {
				sb.append("&#");
				String xi = Integer.toHexString(c);
				if (xi.length() % 2 != 0) {
					sb.append("0");
				}
				sb.append(xi);
				sb.append(";");
			}else {
				sb.append(c);
			}
		}
		return sb.toString();
	}
} // Utils
