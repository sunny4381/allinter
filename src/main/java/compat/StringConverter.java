/*******************************************************************************
 * Copyright (c) 2000, 2015 IBM Corporation and others.
 *
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Christian Georgi <christian.georgi@sap.com> - [Themes] Invalid RGB color value in themes extension makes workbench unusable - http://bugs.eclipse.org/419435
 *******************************************************************************/
package compat;

import java.util.NoSuchElementException;
import java.util.StringTokenizer;

public class StringConverter {
    /**
     * Converts the given value into an SWT point. This method fails if the value
     * does not represent a point.
     * <p>
     * A valid point representation is a string of the form
     * <code><i>x</i>,<i>y</i></code> where <code><i>x</i></code> and
     * <code><i>y</i></code> are valid ints.
     * </p>
     *
     * @param value the value to be converted
     * @return the value as a point
     * @exception NumberFormatException if the given value does not represent a point
     */
    public static Point asPoint(String value) throws NumberFormatException {
        if (value == null) {
            throw new IllegalArgumentException("Null doesn't represent a valid point"); //$NON-NLS-1$
        }
        StringTokenizer stok = new StringTokenizer(value, ","); //$NON-NLS-1$
        String x = stok.nextToken();
        String y = stok.nextToken();

        return new Point(Integer.parseInt(x), Integer.parseInt(y));
    }

    /**
     * Converts the given value into an SWT point.
     * Returns the given default value if the
     * value does not represent a point.
     *
     * @param value the value to be converted
     * @param dflt the default value
     * @return the value as a point, or the default value
     */
    public static Point asPoint(String value, Point dflt) {
        try {
            return asPoint(value);
        } catch (IllegalArgumentException e) {
            return dflt;
        }
    }

    /**
     * Converts the given value into an SWT rectangle. This method fails if the
     * value does not represent a rectangle.
     * <p>
     * A valid rectangle representation is a string of the form
     * <code><i>x</i>,<i>y</i>,<i>width</i>,<i>height</i></code> where
     * <code><i>x</i></code>, <code><i>y</i></code>, <code><i>width</i></code>, and
     * <code><i>height</i></code> are valid ints.
     * </p>
     *
     * @param value the value to be converted
     * @return the value as a rectangle
     * @exception NumberFormatException if the given value does not represent a
     *                                rectangle
     */
    public static Rectangle asRectangle(String value)
            throws NumberFormatException {
        if (value == null) {
            throw new IllegalArgumentException(
                    "Null doesn't represent a valid rectangle"); //$NON-NLS-1$
        }
        StringTokenizer stok = new StringTokenizer(value, ","); //$NON-NLS-1$
        String x = stok.nextToken();
        String y = stok.nextToken();
        String width = stok.nextToken();
        String height = stok.nextToken();
        int xval = 0, yval = 0, wval = 0, hval = 0;

        xval = Integer.parseInt(x);
        yval = Integer.parseInt(y);
        wval = Integer.parseInt(width);
        hval = Integer.parseInt(height);

        return new Rectangle(xval, yval, wval, hval);
    }

    /**
     * Converts the given value into an SWT rectangle.
     * Returns the given default value if the
     * value does not represent a rectangle.
     *
     * @param value the value to be converted
     * @param dflt the default value
     * @return the value as a rectangle, or the default value
     */
    public static Rectangle asRectangle(String value, Rectangle dflt) {
        try {
            return asRectangle(value);
        } catch (IllegalArgumentException e) {
            return dflt;
        }
    }

    /**
     * Converts the given value into an SWT RGB color value. This method fails if
     * the value does not represent an RGB color value.
     * <p>
     * A valid RGB color value representation is a string of the form
     * <code><i>red</i>,<i>green</i>,<i>blue</i></code> where
     * <code><i>red</i>,<i>green</i></code> and <code><i>blue</i></code> are valid
     * ints.
     * </p>
     *
     * @param value the value to be converted
     * @return the value as an RGB color value
     * @exception IllegalArgumentException if the given value does not represent an RGB
     *                                color value
     */
    public static RGB asRGB(String value) throws IllegalArgumentException {
        if (value == null) {
            throw new IllegalArgumentException("Null doesn't represent a valid RGB"); //$NON-NLS-1$
        }
        StringTokenizer stok = new StringTokenizer(value, ","); //$NON-NLS-1$

        try {
            String red = stok.nextToken().trim();
            String green = stok.nextToken().trim();
            String blue = stok.nextToken().trim();
            int rval = 0, gval = 0, bval = 0;

            rval = Integer.parseInt(red);
            gval = Integer.parseInt(green);
            bval = Integer.parseInt(blue);

            return new RGB(rval, gval, bval);
        } catch (NoSuchElementException e) {
            throw new IllegalArgumentException(e);
        }
    }

    /**
     * Converts the given value into an SWT RGB color value.
     * Returns the given default value if the
     * value does not represent an RGB color value.
     *
     * @param value the value to be converted
     * @param dflt the default value
     * @return the value as a RGB color value, or the default value
     */
    public static RGB asRGB(String value, RGB dflt) {
        try {
            return asRGB(value);
        } catch (IllegalArgumentException e) {
            return dflt;
        }
    }
}
