package compat;

import java.io.Serializable;

public class RGB implements Serializable {
    public int red;
    public int green;
    public int blue;
    static final long serialVersionUID = 3258415023461249074L;

    public RGB(int var1, int var2, int var3) {
        if (var1 > 255 || var1 < 0 || var2 > 255 || var2 < 0 || var3 > 255 || var3 < 0) {
            throw new IllegalArgumentException("Argument not valid");
        }

        this.red = var1;
        this.green = var2;
        this.blue = var3;
    }

    public RGB(float var1, float var2, float var3) {
        if (var1 < 0.0F || var1 > 360.0F || var2 < 0.0F || var2 > 1.0F || var3 < 0.0F || var3 > 1.0F) {
            throw new IllegalArgumentException("Argument not valid");
        }

        float var4;
        float var5;
        float var6;
        if (var2 == 0.0F) {
            var6 = var3;
            var5 = var3;
            var4 = var3;
        } else {
            if (var1 == 360.0F) {
                var1 = 0.0F;
            }

            var1 /= 60.0F;
            int var7 = (int)var1;
            float var8 = var1 - (float)var7;
            float var9 = var3 * (1.0F - var2);
            float var10 = var3 * (1.0F - var2 * var8);
            float var11 = var3 * (1.0F - var2 * (1.0F - var8));
            switch(var7) {
            case 0:
                var4 = var3;
                var5 = var11;
                var6 = var9;
                break;
            case 1:
                var4 = var10;
                var5 = var3;
                var6 = var9;
                break;
            case 2:
                var4 = var9;
                var5 = var3;
                var6 = var11;
                break;
            case 3:
                var4 = var9;
                var5 = var10;
                var6 = var3;
                break;
            case 4:
                var4 = var11;
                var5 = var9;
                var6 = var3;
                break;
            case 5:
            default:
                var4 = var3;
                var5 = var9;
                var6 = var10;
            }
        }

        this.red = (int)((double)(var4 * 255.0F) + 0.5D);
        this.green = (int)((double)(var5 * 255.0F) + 0.5D);
        this.blue = (int)((double)(var6 * 255.0F) + 0.5D);
    }

    public float[] getHSB() {
        float var1 = (float)this.red / 255.0F;
        float var2 = (float)this.green / 255.0F;
        float var3 = (float)this.blue / 255.0F;
        float var4 = Math.max(Math.max(var1, var2), var3);
        float var5 = Math.min(Math.min(var1, var2), var3);
        float var6 = var4 - var5;
        float var7 = 0.0F;
        float var9 = var4 == 0.0F ? 0.0F : (var4 - var5) / var4;
        if (var6 != 0.0F) {
            if (var1 == var4) {
                var7 = (var2 - var3) / var6;
            } else if (var2 == var4) {
                var7 = 2.0F + (var3 - var1) / var6;
            } else {
                var7 = 4.0F + (var1 - var2) / var6;
            }

            var7 *= 60.0F;
            if (var7 < 0.0F) {
                var7 += 360.0F;
            }
        }

        return new float[]{var7, var9, var4};
    }

    public boolean equals(Object var1) {
        if (var1 == this) {
            return true;
        } else if (!(var1 instanceof RGB)) {
            return false;
        } else {
            RGB var2 = (RGB)var1;
            return var2.red == this.red && var2.green == this.green && var2.blue == this.blue;
        }
    }

    public int hashCode() {
        return this.blue << 16 | this.green << 8 | this.red;
    }

    public String toString() {
        return "RGB {" + this.red + ", " + this.green + ", " + this.blue + "}";
    }
}
