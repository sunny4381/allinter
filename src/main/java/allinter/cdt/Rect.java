package allinter.cdt;

import java.util.Map;

public class Rect {
    private Double x;
    private Double y;
    private Double width;
    private Double height;
    private Double left;
    private Double top;
    private Double right;
    private Double bottom;

    public Rect() {}

    public static Rect convertFrom(Map<String, ?> map) {
        Rect ret = new Rect();

        map.forEach((key, _value) -> {
            final Double value;
            if (_value == null) {
                value = null;
            } else if (_value instanceof Double) {
                value = (Double) _value;
            } else if (_value instanceof Integer) {
                value = ((Integer) _value).doubleValue();
            } else {
                value = Double.parseDouble(_value.toString());
            }

            switch (key) {
            case "x":
                ret.setX(value);
                break;
            case "y":
                ret.setY(value);
                break;
            case "width":
                ret.setWidth(value);
                break;
            case "height":
                ret.setHeight(value);
                break;
            case "left":
                ret.setLeft(value);
                break;
            case "top":
                ret.setTop(value);
                break;
            case "right":
                ret.setRight(value);
                break;
            case "bottom":
                ret.setBottom(value);
                break;
            }
        });

        return ret;
    }

    public Double getX() {
        return x;
    }

    public void setX(Double x) {
        this.x = x;
    }

    public Double getY() {
        return y;
    }

    public void setY(Double y) {
        this.y = y;
    }

    public Double getWidth() {
        return width;
    }

    public void setWidth(Double width) {
        this.width = width;
    }

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public Double getLeft() {
        return left;
    }

    public void setLeft(Double left) {
        this.left = left;
    }

    public Double getTop() {
        return top;
    }

    public void setTop(Double top) {
        this.top = top;
    }

    public Double getRight() {
        return right;
    }

    public void setRight(Double right) {
        this.right = right;
    }

    public Double getBottom() {
        return bottom;
    }

    public void setBottom(Double bottom) {
        this.bottom = bottom;
    }
}
