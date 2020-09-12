/*******************************************************************************
 * Copyright (c) 2006, 2008 IBM Corporation and Others
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Norimasa HAYASHIDA - initial API and implementation
 *******************************************************************************/

package org.eclipse.actf.visualization.eval.preferences;

//import java.util.ArrayList;
//import java.util.List;
//
//import org.eclipse.actf.visualization.internal.eval.EvaluationPlugin;
//
//
//
//
//public class GuidelineTreeItemType implements IGuidelineTreeItem {
//
//    public static final int TYPE_HTML = 10;
//
//    public static final int TYPE_ODF = 11;
//
//    public static final int TYPE_FLASH = 12;
//
//    public int _type;
//
//    private IGuidelineTreeItem _parent = null;
//
//    private List<IGuidelineTreeItem> _children = null;
//
//    GuidelineTreeItemType(int type) {
//        this._type = type;
//        this._children = new ArrayList<IGuidelineTreeItem>();
//    }
//
//    public void add(IGuidelineTreeItem guidelineTreeItem) {
//        this._children.add(guidelineTreeItem);
//        this._parent = this;
//    }
//
//    public IGuidelineTreeItem getParent() {
//        return this._parent;
//    }
//
//    public List<IGuidelineTreeItem> getChildren() {
//        return this._children;
//    }
//
//
//    @SuppressWarnings("nls")
//	public String getTypeStr() {
//
//        switch (this._type) {
//        case TYPE_HTML:
//            return "HTML";
//        case TYPE_ODF:
//            return "ODF";
//        case TYPE_FLASH:
//            return "FLASH";
//        }
//
//        return "";
//    }
//
//
//    @SuppressWarnings("nls")
//	public Image getTypeImage() {
//
//        switch (this._type) {
//        case TYPE_HTML:
//            return EvaluationPlugin.getImageDescriptor("icons/media/w3c.png").createImage();
//        case TYPE_ODF:
//            return EvaluationPlugin.getImageDescriptor("icons/media/odf.png").createImage();
//        case TYPE_FLASH:
//            return EvaluationPlugin.getImageDescriptor("icons/media/flash.png").createImage();
//        }
//
//        return null;
//    }
//
//}
