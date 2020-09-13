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
package org.eclipse.actf.visualization.lowvision;

import java.io.File;
import java.io.IOException;

import compat.FileUtils;


/**
 * The activator class controls the plug-in life cycle
 */
public class LowVisionVizPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "org.eclipse.actf.visualization.lowvision"; //$NON-NLS-1$

//	// The shared instance
//	private static LowVisionVizPlugin plugin;

	private static File tmpDir = null;

//	/**
//	 * The constructor
//	 */
//	public LowVisionVizPlugin() {
//		plugin = this;
//	}

//	/*
//	 * (non-Javadoc)
//	 *
//	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
//	 */
//	public void start(BundleContext context) throws Exception {
//		super.start(context);
//
//		createTempDirectory();
//		String tmpS;
//		if (tmpDir != null) {
//			tmpS = tmpDir.getAbsolutePath() + File.separator + "img"; //$NON-NLS-1$
//			if (FileUtils.isAvailableDirectory(tmpS)) {
//				String tmpS2 = tmpS + File.separator;
//				LowVisionVizResourceUtil.saveImages(tmpS2);
//			}
//		}
//	}

//	/*
//	 * (non-Javadoc)
//	 *
//	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
//	 */
//	public void stop(BundleContext context) throws Exception {
//		plugin = null;
//		super.stop(context);
//
//		if (tmpDir != null) {
//			FileUtils.deleteFiles(tmpDir);
//		}
//	}

//	/**
//	 * Returns the shared instance
//	 *
//	 * @return the shared instance
//	 */
//	public static LowVisionVizPlugin getDefault() {
//		return plugin;
//	}

	private static void createTempDirectory() throws IOException {
		if (tmpDir == null) {
			tmpDir = FileUtils.newTempDirectory();
		}
	}

	public static File createTempFile(String prefix, String suffix) throws Exception {
		createTempDirectory();
		return (File.createTempFile(prefix, suffix, tmpDir));
	}

	public static File getTempDirectory() throws IOException {
		if (tmpDir == null) {
			createTempDirectory();
		}
		return tmpDir;
	}

}
