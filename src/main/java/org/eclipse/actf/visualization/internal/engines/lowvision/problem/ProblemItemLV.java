/*******************************************************************************
 * Copyright (c) 2005, 2011 IBM Corporation and Others
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Junji MAEDA - initial API and implementation
 *******************************************************************************/

package org.eclipse.actf.visualization.internal.engines.lowvision.problem;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.eclipse.actf.visualization.eval.problem.IProblemItemImage;
import org.eclipse.actf.visualization.eval.problem.ProblemItemImpl;

@SuppressWarnings("nls")
public class ProblemItemLV extends ProblemItemImpl implements IProblemItemImage {

	private static final String SPACE = " "; //$NON-NLS-1$

	// TODO i18n, consider plugin
	private static final String ERR_IRO = "Color problem";

	private static final String ERR_COMPLIANCEALERT = "Compliance information";

	private static final String ERR_BOKE = "Blur problem";

	private static final String ERR_HIGHLIGHT = "(can highlight)";

	private final String xpath;

	private final String cssPath;

	int area = 0;

	String backgroundS = ""; //$NON-NLS-1$

	String foregroundS = ""; //$NON-NLS-1$

	int frameId = -1;

	int frameOffset = 0;

	String frameUrlS = ""; //$NON-NLS-1$

	private int height = 0;

	int iconId;

	int severityLV = 0;

	private int width = 0;

	private int x = 0;

	private int y = 0;

	// TODO recommendation

	/**
	 * @param id
	 */
	public ProblemItemLV(String id, String xpath, String cssPath) {
		super(id);
		this.xpath = xpath;
		this.cssPath = cssPath;
	}

	public String getXPath() {
		return this.xpath;
	}

	public String getCssPath() {
		return this.cssPath;
	}

	public int getArea() {
		return area;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.actf.visualization.eval.problem.IProblemItemImage#getBackgroundS
	 * ()
	 */
	public String getBackground() {
		return backgroundS;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.actf.visualization.eval.problem.IProblemItemImage#getForegroundS
	 * ()
	 */
	public String getForeground() {
		return foregroundS;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.actf.visualization.eval.problem.IProblemItemImage#getFrameId
	 * ()
	 */
	@JsonIgnore
	public int getFrameId() {
		return frameId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.actf.visualization.eval.problem.IProblemItemImage#getFrameOffset
	 * ()
	 */
	@JsonIgnore
	public int getFrameOffset() {
		return frameOffset;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.actf.visualization.eval.problem.IProblemItemImage#getFrameUrlS
	 * ()
	 */
	@JsonIgnore
	public String getFrameUrl() {
		return frameUrlS;
	}

	@JsonIgnore
	public int getHeight() {
		return height;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.actf.visualization.eval.problem.IProblemItemImage#getIconId()
	 */
	@JsonIgnore
	public int getIconId() {
		return iconId;
	}

//	/*
//	 * (non-Javadoc)
//	 *
//	 * @see
//	 * org.eclipse.actf.visualization.eval.problem.IProblemItemImage#getImageIcon
//	 * ()
//	 */
//	@JsonIgnore
//	public Image getIconImage() {
//		// return imageIcon;
//		String evalPluginID = EvaluationUtil.PLUGIN_ID;
//		if (isCanHighlight()) {
//			switch (iconId) {
//			case ICON_COLOR:
//				return AbstractUIPlugin.imageDescriptorFromPlugin(evalPluginID,
//						"icons/lowvision/HiIro21.gif").createImage(); //$NON-NLS-1$
//			case ICON_BLUR:
//				return AbstractUIPlugin.imageDescriptorFromPlugin(evalPluginID,
//						"icons/lowvision/HiBoke21.gif").createImage(); //$NON-NLS-1$
//			default:
//				return null;
//			}
//		} else {
//			switch (iconId) {
//			case ICON_COLOR:
//				return AbstractUIPlugin.imageDescriptorFromPlugin(evalPluginID,
//						"icons/lowvision/ErrIro21.gif").createImage(); //$NON-NLS-1$
//			case ICON_BLUR:
//				return AbstractUIPlugin.imageDescriptorFromPlugin(evalPluginID,
//						"icons/lowvision/ErrBoke21.gif").createImage(); //$NON-NLS-1$
//			default:
//				return null;
//			}
//		}
//	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.actf.visualization.eval.problem.IProblemItemImage#
	 * getImageIconTooltip()
	 */
	public String getIconTooltip() {
		// return imageIcon;
		if (isCanHighlight()) {
			switch (iconId) {
			case ICON_COLOR:
				return ERR_IRO + SPACE + ERR_HIGHLIGHT;
			case ICON_BLUR:
				return ERR_BOKE + SPACE + ERR_HIGHLIGHT;
			default:
				// System.out.println("Icon not found: " + iconId);
				return ERR_COMPLIANCEALERT + SPACE + ERR_HIGHLIGHT;

			}
		} else {
			switch (iconId) {
			case ICON_COLOR:
				return ERR_IRO;
			case ICON_BLUR:
				return ERR_BOKE;
			default:
				// System.out.println("Icon not found: " + iconId);
				return ERR_COMPLIANCEALERT;
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.actf.visualization.eval.problem.IProblemItemImage#getSeverityLV
	 * ()
	 */
	public int getSeverityLV() {
		return severityLV;
	}

	public int getWidth() {
		return width;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public void setArea(int area) {
		this.area = area;
	}

	public void setBackground(String backgroundS) {
		this.backgroundS = backgroundS;
	}

	public void setForeground(String foregroundS) {
		this.foregroundS = foregroundS;
	}

	public void setFrameId(int frameId) {
		this.frameId = frameId;
	}

	public void setFrameOffset(int frameOffset) {
		this.frameOffset = frameOffset;
	}

	public void setFrameUrl(String frameUrlS) {
		this.frameUrlS = frameUrlS;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public void setSeverityLV(int severityLV) {
		this.severityLV = severityLV;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public void setSubType(int subType) {
		this.subType = subType;
		// TODO
		if (subType == LOWVISION_COLOR_PROBLEM
				|| subType == LOWVISION_IMAGE_COLOR_PROBLEM
				|| subType == LOWVISION_BACKGROUND_IMAGE_WARNING) {
			iconId = ICON_COLOR;
		} else {
			iconId = ICON_BLUR;
		}

	}
}
