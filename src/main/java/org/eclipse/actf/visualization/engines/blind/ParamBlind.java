/*******************************************************************************
 * Copyright (c) 2004, 2016 IBM Corporation and Others
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Hironobu TAKAGI - initial API and implementation
 *******************************************************************************/
package org.eclipse.actf.visualization.engines.blind;

import org.eclipse.actf.visualization.engines.blind.ui.preferences.IBlindPreferenceConstants;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferenceConverter;
import org.eclipse.jface.resource.StringConverter;
import org.eclipse.swt.graphics.RGB;

import java.util.ResourceBundle;

/**
 * This class stores configuration parameters of blind usability visualization
 */
public class ParamBlind {

	public static final int EN = 0;

	public static final int JP = 1;

	public static String BLIND_LAYOUT_MODE = IBlindPreferenceConstants.BLIND_LAYOUT_MODE;

	public static String BLIND_BROWSER_MODE = IBlindPreferenceConstants.BLIND_BROWSER_MODE;

	private static final String BUNDLE_NAME = "org/eclipse/actf/visualization/engines/blind/default";

	private static ParamBlind INSTANCE;

	public boolean oReplaceImage;

	public boolean oVisualizArrival; // Visualize arrival time (default on)

	public int iLanguage; //

	public String visualizeMode;

	public boolean bVisualizeTime;

	public boolean bColorizeTags;

	public boolean bVisualizeTable;

	public int iMaxTime;

	public RGB maxTimeColor;

	public RGB tableHeaderColor;

	public RGB headingTagsColor;

	public RGB inputTagsColor;

	public RGB labelTagsColor;

	public RGB tableBorderColor;

	public RGB captionColor;

	/**
	 * Get instance of {@link ParamBlind}
	 * 
	 * @return instance of {@link ParamBlind}
	 */
	public static ParamBlind getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new ParamBlind();
		}
		return (INSTANCE);
	}

	/**
	 * Reset configuration parameters to default
	 */
	public static void refresh() {
		ParamBlind pb = getInstance();
		setValues(pb);
	}

	private static void setValues(ParamBlind pb) {
		ResourceBundle bundle = ResourceBundle.getBundle(BUNDLE_NAME, ResourceBundleControlHolder.getInstance());

		String lang = bundle.getString(IBlindPreferenceConstants.BLIND_LANG);
		if (lang == null || lang.equals(IBlindPreferenceConstants.LANG_JA)) {
			pb.iLanguage = JP;
		} else {
			pb.iLanguage = EN;
		}

		pb.visualizeMode = bundle.getString(IBlindPreferenceConstants.BLIND_MODE);

		pb.iMaxTime = Integer.parseInt(bundle.getString(IBlindPreferenceConstants.BLIND_MAX_TIME_SECOND));
		pb.maxTimeColor = getColor(bundle.getString(IBlindPreferenceConstants.BLIND_MAX_TIME_COLOR));
		pb.tableHeaderColor = getColor(bundle.getString(IBlindPreferenceConstants.BLIND_TABLE_HEADER_COLOR));
		pb.headingTagsColor = getColor(bundle.getString(IBlindPreferenceConstants.BLIND_HEADING_TAGS_COLOR));
		pb.inputTagsColor = getColor(bundle.getString(IBlindPreferenceConstants.BLIND_INPUT_TAGS_COLOR));
		pb.labelTagsColor = getColor(bundle.getString(IBlindPreferenceConstants.BLIND_LABEL_TAGS_COLOR));
		pb.tableBorderColor = getColor(bundle.getString(IBlindPreferenceConstants.BLIND_TABLE_BORDER_COLOR));
		pb.captionColor = getColor(bundle.getString(IBlindPreferenceConstants.BLIND_CAPTION_COLOR));
	}

	private static RGB getColor(String value) {
		if (value == null || IPreferenceStore.STRING_DEFAULT_DEFAULT.equals(value)) {
			return PreferenceConverter.COLOR_DEFAULT_DEFAULT;
		}

		RGB color = StringConverter.asRGB(value, null);
		if (color == null) {
			return PreferenceConverter.COLOR_DEFAULT_DEFAULT;
		}
		return color;
	}

	private ParamBlind() {

		oReplaceImage = true;
		oVisualizArrival = true;
		bVisualizeTime = true;
		bColorizeTags = true;
		bVisualizeTable = true;

		setValues(this);
	}

}
