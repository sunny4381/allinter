/*******************************************************************************
 * Copyright (c) 2007,2008 IBM Corporation and Others
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Kentarou FUKUDA - initial API and implementation
 *******************************************************************************/
package org.eclipse.actf.visualization.eval;

import org.eclipse.actf.examples.adesigner.eval.html.Checker;
import org.eclipse.actf.visualization.eval.preferences.IPreferenceConstants;
import org.eclipse.actf.visualization.internal.eval.CheckerExtension;

/**
 * Utility class for org.eclipse.actf.visualization.eval plugin
 */
public class EvaluationUtil {

//	/**
//	 * The plug-in ID
//	 */
//	public static final String PLUGIN_ID = "org.eclipse.actf.visualization.eval"; //$NON-NLS-1$

	/**
	 * Check user selection of target DOM (original source or live)
	 * 
	 * @return true, if user selected original DOM in preference page
	 */
	public static boolean isOriginalDOM() {
		String targetDOM = System.getProperty(IPreferenceConstants.CHECKER_TARGET);
		if (targetDOM == null || targetDOM.isEmpty()) {
//			targetDOM = IPreferenceConstants.CHECKER_ORG_DOM;
			targetDOM = IPreferenceConstants.CHECKER_LIVE_DOM;
		}
		return IPreferenceConstants.CHECKER_ORG_DOM.equals(targetDOM);
	}

	/**
	 * Get all registered {@link IChecker}
	 * 
	 * @return array of {@link IChecker}
	 */
	public static IChecker[] getCheckers() {
//		return CheckerExtension.getCheckers();
		return new IChecker[] { new Checker() };
	}

	/**
	 * Get all registered {@link ICheckerInfoProvider}
	 * 
	 * @return array of {@link ICheckerInfoProvider}
	 */
	public static ICheckerInfoProvider[] getCheckerInfoProviders() {
		return CheckerExtension.getCheckerInfoProviders();
	}

}
