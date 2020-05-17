/*******************************************************************************
 * Copyright (c) 2003, 2008 IBM Corporation and Others
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Kentarou FUKUDA - initial API and implementation
 *******************************************************************************/
package org.eclipse.actf.visualization.ui;

//import java.util.ArrayList;
//import java.util.List;
//
//import org.eclipse.actf.model.ui.IModelService;
//import org.eclipse.actf.model.ui.IModelServiceScrollManager;
//import org.eclipse.actf.model.ui.ModelServiceSizeInfo;
//import org.eclipse.swt.SWT;
//import org.eclipse.swt.SWTException;
//import org.eclipse.swt.events.PaintEvent;
//import org.eclipse.swt.events.PaintListener;
//import org.eclipse.swt.events.SelectionAdapter;
//import org.eclipse.swt.events.SelectionEvent;
//import org.eclipse.swt.graphics.Color;
//import org.eclipse.swt.graphics.Image;
//import org.eclipse.swt.graphics.ImageData;
//import org.eclipse.swt.graphics.Rectangle;
//import org.eclipse.swt.widgets.Canvas;
//import org.eclipse.swt.widgets.Composite;
//import org.eclipse.swt.widgets.Display;
//import org.eclipse.swt.widgets.Event;
//import org.eclipse.swt.widgets.Listener;
//import org.eclipse.swt.widgets.ScrollBar;
//
///**
// * Utility class to show visualization result into {@link Canvas}
// */
//public class VisualizationCanvas extends Canvas {
//
//	private final Display display;
//
//	private Image image = null;
//
//	private ImageData imageData = null; // the currently-displayed image
//
//	private int curX;
//
//	private int curY;
//
//	private ScrollBar horizontalBar;
//
//	private ScrollBar verticalBar;
//
//	private List<IPositionSize> highlightTargetList = new ArrayList<IPositionSize>();
//
//	private IModelService current = null;
//
//	private IModelService target = null;
//
//	private String targetUrl = null;
//
//	private boolean sync = true;
//
//	/**
//	 * Create {@link VisualizationCanvas}
//	 *
//	 * @param parent
//	 *            parent {@link Composite}
//	 */
//	public VisualizationCanvas(Composite parent) {
//		this(parent, SWT.NONE);
//	}
//
//	/**
//	 * Create {@link VisualizationCanvas}
//	 *
//	 * @param parent
//	 *            parent {@link Composite}
//	 * @param style
//	 *            widget style
//	 */
//	public VisualizationCanvas(Composite parent, int style) {
//		super(parent, SWT.V_SCROLL | SWT.H_SCROLL | style);
//		display = parent.getDisplay();
//		init();
//
//		// TODO
//		parent.getShell().addListener(100, new Listener() {
//			public void handleEvent(Event e) {
//				setVScrollBar(e.detail);
//			}
//		});
//
//	}
//
//	private void init() {
//		setBackground(new Color(display, 255, 255, 255));
//
//		curX = 0;
//		curY = 0;
//
//		// Set up the image canvas scroll bars.
//		horizontalBar = getHorizontalBar();
//		horizontalBar.setVisible(true);
//		horizontalBar.setMinimum(0);
//		horizontalBar.setEnabled(false);
//		horizontalBar.addSelectionListener(new SelectionAdapter() {
//			public void widgetSelected(SelectionEvent event) {
//				scrollHorizontally((ScrollBar) event.widget);
//			}
//		});
//		verticalBar = getVerticalBar();
//		verticalBar.setVisible(true);
//		verticalBar.setMinimum(0);
//		verticalBar.setEnabled(false);
//		verticalBar.addSelectionListener(new SelectionAdapter() {
//			public void widgetSelected(SelectionEvent event) {
//				scrollVertically((ScrollBar) event.widget);
//			}
//		});
//
//		addPaintListener(new PaintListener() {
//			public void paintControl(PaintEvent event) {
//				if (null == imageData) {
//					return;
//				}
//				if (null != image) {
//					paintImage(event);
//				}
//
//				resizeScrollBars();
//
//			}
//		});
//	}
//
//	private void paintImage(PaintEvent event) {
//		if (null == image || null == imageData) {
//			return;
//		}
//
//		Image paintImage = image;
//		int w = Math.round(imageData.width); // xscale
//		int h = Math.round(imageData.height); // yscale
//		event.gc.drawImage(paintImage, 0, 0, imageData.width, imageData.height,
//				curX + imageData.x, curY + imageData.y, w, h);
//
//		List<IPositionSize> tmpV = highlightTargetList;
//		if (tmpV != null) {
//			// event.gc.setXORMode(false);
//			event.gc.setLineWidth(2);
//			for (IPositionSize ips : tmpV) {
//
//				event.gc.setForeground(display.getSystemColor(SWT.COLOR_WHITE));
//				event.gc.drawRectangle(curX + ips.getX(), curY + ips.getY(),
//						ips.getWidth(), ips.getHeight());
//
//				event.gc.setForeground(display.getSystemColor(SWT.COLOR_BLACK));
//				event.gc.drawRectangle(curX + ips.getX() - 2, curY + ips.getY()
//						- 2, ips.getWidth() + 4, ips.getHeight() + 4);
//
//			}
//		}
//	}
//
//	protected void resetScrollBars() {
//		if (image == null) {
//			horizontalBar.setEnabled(false);
//			verticalBar.setEnabled(false);
//			return;
//		}
//
//		curX = 0;
//		curY = 0;
//		resizeScrollBars();
//		horizontalBar.setSelection(0);
//		verticalBar.setSelection(0);
//	}
//
//	private void resizeScrollBars() {
//		if (imageData == null)
//			return;
//
//		// Set the max and thumb for the image canvas scroll bars.
//
//		Rectangle canvasBounds = getClientArea();
//		int width = Math.round(imageData.width); // xscale
//		if (width > canvasBounds.width) {
//			// The image is wider than the canvas.
//			horizontalBar.setEnabled(true);
//			horizontalBar.setMaximum(width);
//			horizontalBar.setThumb(canvasBounds.width);
//			horizontalBar.setPageIncrement(canvasBounds.width);
//		} else {
//			// The canvas is wider than the image.
//			horizontalBar.setEnabled(false);
//			if (curX != 0) {
//				// Make sure the image is completely visible.
//				curX = 0;
//				redraw();
//			}
//		}
//		int height = Math.round(imageData.height); // yscale
//		if (height > canvasBounds.height) {
//			// The image is taller than the canvas.
//			verticalBar.setEnabled(true);
//			verticalBar.setMaximum(height);
//			verticalBar.setThumb(canvasBounds.height);
//			verticalBar.setPageIncrement(canvasBounds.height);
//		} else {
//			// The canvas is taller than the image.
//			verticalBar.setEnabled(false);
//			if (curY != 0) {
//				// Make sure the image is completely visible.
//				curY = 0;
//				redraw();
//			}
//		}
//	}
//
//	private void setVScrollBar(int iPos) {
//		if (sync) {
//			if (image == null) {
//				return;
//			}
//			// Rectangle canvasBounds = _imageCanvas.getClientArea();
//			int width = Math.round(imageData.width); // xscale
//			int height = Math.round(imageData.height); // yscale
//
//			scroll(curX, -iPos, curX, curY, width, height, false);
//			curY = -iPos;
//			verticalBar.setSelection(iPos);
//		}
//	}
//
//	private void scrollHorizontally(ScrollBar scrollBar) {
//		if (image == null)
//			return;
//		Rectangle canvasBounds = getClientArea();
//		int width = Math.round(imageData.width); // xscale
//		int height = Math.round(imageData.height); // yscale
//		if (width > canvasBounds.width) {
//			// Only scroll if the image is bigger than the canvas.
//			int x = -scrollBar.getSelection();
//			if (x + width < canvasBounds.width) {
//				// Don't scroll past the end of the image.
//				x = canvasBounds.width - width;
//			}
//			scroll(x, curY, curX, curY, width, height, false);
//			curX = x;
//
//		}
//	}
//
//	private void scrollVertically(ScrollBar scrollBar) {
//		if (null != image) {
//			Rectangle canvasBounds = getClientArea();
//			int width = Math.round(imageData.width); // xscale
//			int height = Math.round(imageData.height); // yscale
//			if (height > canvasBounds.height) {
//				// Only scroll if the image is bigger than the canvas.
//				int y = -scrollBar.getSelection();
//				if (y + height < canvasBounds.height) {
//					// Don't scroll past the end of the image.
//					y = canvasBounds.height - height;
//				}
//				scroll(curX, y, curX, curY, width, height, false);
//
//				if (sync) {
//					if (current != null && current == target
//							&& targetUrl.equals(target.getURL())) {
//						IModelServiceScrollManager scrollManager = target
//								.getScrollManager();
//						switch (scrollManager.getScrollType()) {
//						case IModelServiceScrollManager.ABSOLUTE_COORDINATE:
//							int targetHeight = scrollManager.getSize(true)
//									.getWholeSizeY();
//							double ratio = (double) targetHeight
//									/ (double) (height - horizontalBar
//											.getSize().y);
//							scrollManager.absoluteCoordinateScroll(
//									(int) ((curY - y) * ratio), false);
//							break;
//						case IModelServiceScrollManager.INCREMENTAL:
//							// TODO
//							break;
//						case IModelServiceScrollManager.PAGE:
//							ModelServiceSizeInfo sizeInfo = scrollManager
//									.getSize(false);
//							int page = Math.abs(y / sizeInfo.getViewSizeY()) + 1;
//							if (page < 1) {
//								page = 1;
//							} else if (page > scrollManager.getLastPageNumber()) {
//								page = scrollManager.getCurrentPageNumber();
//							}
//							if (page != scrollManager.getCurrentPageNumber()) {
//								scrollManager.jumpToPage(page, false);
//							}
//
//							break;
//						case IModelServiceScrollManager.NONE:
//						default:
//						}
//					}
//				}
//				curY = y;
//			}
//		}
//	}
//
//	/**
//	 * Set current active model service
//	 *
//	 * @param currentModelService
//	 *            target {@link IModelService}
//	 */
//	public void setCurrentModelService(IModelService currentModelService) {
//		this.current = currentModelService;
//	}
//
//	/**
//	 * Show {@link ImageData} to Canvas and set specified {@link IModelService}
//	 * as a view sync target
//	 *
//	 * @param imageData
//	 *            image data to show
//	 * @param targetModelService
//	 *            target {@link IModelService} for view sync
//	 */
//	public void showImage(ImageData imageData, IModelService targetModelService) {
//		// Dispose of the old image
//		if (null != image && !image.isDisposed()) {
//			image.dispose();
//		}
//		this.imageData = imageData;
//		target = targetModelService;
//		if (null == current) { // first time
//			current = target;
//		}
//
//		if (null != target) {
//			targetUrl = target.getURL();
//		} else {
//			targetUrl = null;
//		}
//
//		if (null != this.imageData) {
//			try {
//				// Cache the new image
//				image = new Image(display, this.imageData);
//			} catch (SWTException se) {
//				se.printStackTrace();
//			}
//		}
//		redraw();
//		resetScrollBars();
//	}
//
//	/**
//	 * Clear canvas image
//	 */
//	public void clear() {
//		if (image != null && !image.isDisposed()) {
//			image.dispose();
//			image = null;
//		}
//		if (imageData != null) {
//			imageData = null;
//		}
//		highlightTargetList = new ArrayList<IPositionSize>();
//		resetScrollBars();
//		redraw();
//	}
//
//	/**
//	 * Highlight specified areas
//	 *
//	 * @param highlightList
//	 *            target area information in {@link IPositionSize} format
//	 */
//	public void highlight(List<IPositionSize> highlightList) {
//		if (highlightList != null) {
//			// int topX = 0;
//			int topY = -1;
//
//			for (IPositionSize ips : highlightList) {
//				if (topY < 0 || topY > ips.getY()) {
//					topY = ips.getY();
//					// TODO topX = ips.getX();
//				}
//			}
//
//			if (topY >= 0) {
//				Rectangle canvasBounds = getClientArea();
//				int height = Math.round(imageData.height); // yscale
//				if (height > canvasBounds.height) {
//					if (height - topY < canvasBounds.height) {
//						// Don't scroll past the end of the image.
//						topY = height - canvasBounds.height;
//					}
//					// if (sync && canSync) {
//					// // TODO use VizResult ModelService mapping
//					// //
//					// BrowserEventListenerManager.getInstance().fireScrollY(this._iy
//					// // + topY);
//					// }
//					setVScrollBar(topY);
//				}
//			}
//			highlightTargetList = highlightList;
//		}
//		redraw();
//	}
//
//	/**
//	 * Enable/disable view sync with target {@link IModelService}
//	 *
//	 * @param sync
//	 *            if true, enable view sync
//	 */
//	public void setSync(boolean sync) {
//		this.sync = sync;
//	}
//
//}
