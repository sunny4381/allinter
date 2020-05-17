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

package org.eclipse.actf.core.config;

/**
 * This class is used to report errors with ACTF's configuration
 * @author Randy Horwitz
 *
 */
public class ConfigurationException extends Exception
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ConfigurationException () {
		super();
	}

	public ConfigurationException (String message) {
		super(message);
	}

	public ConfigurationException (Throwable cause) {
		super(cause);
	}

	public ConfigurationException (String message, Throwable cause) {
		super(message, cause);
	}
}
