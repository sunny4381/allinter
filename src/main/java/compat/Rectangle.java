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

public final class Rectangle implements Serializable {
    public int x;
    public int y;
    public int width;
    public int height;
    static final long serialVersionUID = 3256439218279428914L;

    public Rectangle(int var1, int var2, int var3, int var4) {
        this.x = var1;
        this.y = var2;
        this.width = var3;
        this.height = var4;
    }

    public void add(Rectangle var1) {
        if (var1 == null) {
            throw new IllegalArgumentException("Argument cannot be null");
        }

        int var2 = this.x < var1.x ? this.x : var1.x;
        int var3 = this.y < var1.y ? this.y : var1.y;
        int var4 = this.x + this.width;
        int var5 = var1.x + var1.width;
        int var6 = var4 > var5 ? var4 : var5;
        var4 = this.y + this.height;
        var5 = var1.y + var1.height;
        int var7 = var4 > var5 ? var4 : var5;
        this.x = var2;
        this.y = var3;
        this.width = var6 - var2;
        this.height = var7 - var3;
    }

    public boolean contains(int var1, int var2) {
        return var1 >= this.x && var2 >= this.y && var1 < this.x + this.width && var2 < this.y + this.height;
    }

    public boolean contains(Point var1) {
        if (var1 == null) {
            throw new IllegalArgumentException("Argument cannot be null");
        }

        return this.contains(var1.x, var1.y);
    }

    public boolean equals(Object var1) {
        if (var1 == this) {
            return true;
        } else if (!(var1 instanceof Rectangle)) {
            return false;
        } else {
            Rectangle var2 = (Rectangle)var1;
            return var2.x == this.x && var2.y == this.y && var2.width == this.width && var2.height == this.height;
        }
    }

    public int hashCode() {
        return this.x ^ this.y ^ this.width ^ this.height;
    }

    public void intersect(Rectangle var1) {
        if (var1 == null) {
            throw new IllegalArgumentException("Argument cannot be null");
        }

        if (this != var1) {
            int var2 = this.x > var1.x ? this.x : var1.x;
            int var3 = this.y > var1.y ? this.y : var1.y;
            int var4 = this.x + this.width;
            int var5 = var1.x + var1.width;
            int var6 = var4 < var5 ? var4 : var5;
            var4 = this.y + this.height;
            var5 = var1.y + var1.height;
            int var7 = var4 < var5 ? var4 : var5;
            this.x = var6 < var2 ? 0 : var2;
            this.y = var7 < var3 ? 0 : var3;
            this.width = var6 < var2 ? 0 : var6 - var2;
            this.height = var7 < var3 ? 0 : var7 - var3;
        }
    }

    public Rectangle intersection(Rectangle var1) {
        if (var1 == null) {
            throw new IllegalArgumentException("Argument cannot be null");
        }

        if (this == var1) {
            return new Rectangle(this.x, this.y, this.width, this.height);
        } else {
            int var2 = this.x > var1.x ? this.x : var1.x;
            int var3 = this.y > var1.y ? this.y : var1.y;
            int var4 = this.x + this.width;
            int var5 = var1.x + var1.width;
            int var6 = var4 < var5 ? var4 : var5;
            var4 = this.y + this.height;
            var5 = var1.y + var1.height;
            int var7 = var4 < var5 ? var4 : var5;
            return new Rectangle(var6 < var2 ? 0 : var2, var7 < var3 ? 0 : var3, var6 < var2 ? 0 : var6 - var2, var7 < var3 ? 0 : var7 - var3);
        }
    }

    public boolean intersects(int var1, int var2, int var3, int var4) {
        return var1 < this.x + this.width && var2 < this.y + this.height && var1 + var3 > this.x && var2 + var4 > this.y;
    }

    public boolean intersects(Rectangle var1) {
        if (var1 == null) {
            throw new IllegalArgumentException("Argument cannot be null");
        }

        return var1 == this || this.intersects(var1.x, var1.y, var1.width, var1.height);
    }

    public boolean isEmpty() {
        return this.width <= 0 || this.height <= 0;
    }

    public String toString() {
        return "Rectangle {" + this.x + ", " + this.y + ", " + this.width + ", " + this.height + "}";
    }

    public Rectangle union(Rectangle var1) {
        if (var1 == null) {
            throw new IllegalArgumentException("Argument cannot be null");
        }

        int var2 = this.x < var1.x ? this.x : var1.x;
        int var3 = this.y < var1.y ? this.y : var1.y;
        int var4 = this.x + this.width;
        int var5 = var1.x + var1.width;
        int var6 = var4 > var5 ? var4 : var5;
        var4 = this.y + this.height;
        var5 = var1.y + var1.height;
        int var7 = var4 > var5 ? var4 : var5;
        return new Rectangle(var2, var3, var6 - var2, var7 - var3);
    }
}
