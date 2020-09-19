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

package org.eclipse.actf.visualization.internal.eval;

import java.util.ArrayList;

import org.eclipse.actf.examples.adesigner.eval.html.HtmlCheckerInfoProvider;
import org.eclipse.actf.visualization.eval.ICheckerInfoProvider;

public class CheckerExtension {
	private static CheckerExtension[] extensions;

	private static ICheckerInfoProvider[] infoProviders = null;

	public static ICheckerInfoProvider[] getCheckerInfoProviders() {
		if (infoProviders != null) {
			return infoProviders;
		}

		CheckerExtension[] tmpExtensions = getExtensions();
		ArrayList<ICheckerInfoProvider> tmpList = new ArrayList<ICheckerInfoProvider>();
		if (tmpExtensions != null) {
			for (int i = 0; i < tmpExtensions.length; i++) {
				ICheckerInfoProvider tmpInfo = tmpExtensions[i]
						.getCheckerInfoProvider();
				if (tmpInfo != null) {
					tmpList.add(tmpInfo);
				}
			}
		}
		infoProviders = new ICheckerInfoProvider[tmpList.size()];
		tmpList.toArray(infoProviders);
		return infoProviders;
	}

	public static CheckerExtension[] getExtensions() {
		if (extensions != null)
			return extensions;

		extensions = new CheckerExtension[] { new CheckerExtension() };
		return extensions;
	}

	private ICheckerInfoProvider infoProvider = null;

	private CheckerExtension() {
		this.infoProvider = new HtmlCheckerInfoProvider();
	}

	private ICheckerInfoProvider getCheckerInfoProvider() {
		return this.infoProvider;
	}

}
