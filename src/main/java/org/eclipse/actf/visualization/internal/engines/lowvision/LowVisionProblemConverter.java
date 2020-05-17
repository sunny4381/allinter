/*******************************************************************************
 * Copyright (c) 2005, 2020 IBM Corporation and Others
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Kentarou FUKUDA - initial API and implementation
 *******************************************************************************/

package org.eclipse.actf.visualization.internal.engines.lowvision;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.actf.visualization.eval.problem.ILowvisionProblemSubtype;
import org.eclipse.actf.visualization.eval.problem.IProblemItem;
import org.eclipse.actf.visualization.internal.engines.lowvision.problem.ColorProblem;
import org.eclipse.actf.visualization.internal.engines.lowvision.problem.ColorWarning;
import org.eclipse.actf.visualization.internal.engines.lowvision.problem.ILowVisionProblem;
import org.eclipse.actf.visualization.internal.engines.lowvision.problem.LowVisionProblem;
import org.eclipse.actf.visualization.internal.engines.lowvision.problem.LowVisionProblemGroup;
import org.eclipse.actf.visualization.internal.engines.lowvision.problem.ProblemItemLV;

public class LowVisionProblemConverter {

	public static List<IProblemItem> convert(LowVisionProblemGroup[] targets, String urlS, int frameId) {

		ArrayList<IProblemItem> result = new ArrayList<IProblemItem>();

		for (int i = 0; i < targets.length; i++) {
			LowVisionProblemGroup target = targets[i];
			int type = target.getLowVisionProblemType();
			ProblemItemLV tmp;
			switch (type) {
			case ILowVisionProblem.LOWVISION_COLOR_PROBLEM:
				{
					ColorProblem cp = (ColorProblem) target.getRepresentative();
					tmp = new ProblemItemLV("L_" + target.getLowVisionProblemType() + "." + cp.getLevel(), cp.getXPath(), cp.getCssPath()); //$NON-NLS-1$
					tmp.setTargetNode(cp.getElement());
					tmp.setTargetString(cp.getAdditionalDescription());
				}
				break;
			case ILowVisionProblem.LOWVISION_COLOR_WITH_ALPHA_WARNING:
				{
					ColorWarning cw = (ColorWarning) target.getRepresentative();
					tmp = new ProblemItemLV("L_" + target.getLowVisionProblemType() + "." + cw.getWarningType(), cw.getXPath(), cw.getCssPath()); //$NON-NLS-1$
				}
				break;
			default:
				{
					ILowVisionProblem problem = target.getRepresentative();
					tmp = new ProblemItemLV("L_" + target.getLowVisionProblemType(), problem.getXPath(), problem.getCssPath()); //$NON-NLS-1$
				}
			}
			tmp.setSubType(type);
			try {
				switch (type) {
				case ILowvisionProblemSubtype.LOWVISION_BACKGROUND_IMAGE_WARNING:
					ColorProblem cp = (ColorProblem) target.getRepresentative();
					tmp.setTargetNode(cp.getElement());
					tmp.setTargetString(cp.getAdditionalDescription());
					break;
				case ILowvisionProblemSubtype.LOWVISION_COLOR_PROBLEM:
					break;
				default:
					tmp.setDescription(target.getDescription());
					if (target.getRepresentative() != null)
						tmp.setTargetNode(target.getRepresentative().getElement());
				}
			} catch (Exception e) {
				tmp.setDescription("unknown"); //$NON-NLS-1$
			}
			tmp.setCanHighlight(true);

			tmp.setFrameId(frameId);
			tmp.setFrameUrl(urlS);

			tmp.setSeverityLV(target.getIntProbability());// TODO
			tmp.setForeground(getLVProblemColorString(target, true));
			tmp.setBackground(getLVProblemColorString(target, false));
			tmp.setX(target.getX());
			tmp.setY(target.getY());
			tmp.setWidth(target.getWidth());
			tmp.setHeight(target.getHeight());
			tmp.setArea(target.getWidth() * target.getHeight());

			// TODO recommendation
			result.add(tmp);
		}

		return (result);
	}

	private static String getLVProblemColorString(LowVisionProblemGroup problem, boolean isFore) {
		int probType;
		int origAll;
		int origR;
		int origG;
		int origB;

		probType = problem.getLowVisionProblemType();

		if (probType == LowVisionProblem.LOWVISION_COLOR_PROBLEM
				|| probType == LowVisionProblem.LOWVISION_BACKGROUND_IMAGE_WARNING) {
			ColorProblem cp = (ColorProblem) (problem.getRepresentative());
			if (isFore) {
				origAll = cp.getForegroundColor();
			} else {
				origAll = cp.getBackgroundColor();
			}
			origR = origAll >> 16 & 0xff;
			origG = origAll >> 8 & 0xff;
			origB = origAll & 0xff;
			return origR + "," + origG + "," + origB; //$NON-NLS-1$ //$NON-NLS-2$
		} else {
			return " "; //$NON-NLS-1$
		}
	}

}
