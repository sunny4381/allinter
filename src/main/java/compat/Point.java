/*******************************************************************************
 * Copyright (c) 2000, 2011 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package compat;

import java.io.Serializable;

public class Point implements Serializable {
    public int x;
    public int y;
    static final long serialVersionUID = 3257002163938146354L;

    public Point(int var1, int var2) {
        this.x = var1;
        this.y = var2;
    }

    public boolean equals(Object var1) {
        if (var1 == this) {
            return true;
        } else if (!(var1 instanceof Point)) {
            return false;
        } else {
            Point var2 = (Point)var1;
            return var2.x == this.x && var2.y == this.y;
        }
    }

    public int hashCode() {
        return this.x ^ this.y;
    }

    public String toString() {
        return "Point {" + this.x + ", " + this.y + "}";
    }
}
