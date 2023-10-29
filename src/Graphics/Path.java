package Graphics;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;
import java.util.List;
import java.util.Objects;

import static java.lang.Float.NaN;


/**
 * An arbitrary path or polygon with straight sides, possibly self-intersecting.
 * <p>
 * The path is internally represented as a list of points, with an edge of the
 * path connecting each adjacent pair of points in the list. The order of the list
 * is thus important. Incorrectly ordering the list can lead to self-intersecting
 * polygon, which are neat, but possibly not what you want.
 * <p>
 * An optional final edge connects the first and last point in the list. You can
 * disable this by passing `false` to the second argument of setPoints().
 * <p>
 * A path’s {@link getPosition() position} is the upper left corner of the bounding box of all its
 * points, and its {@link getSize() size} is the size of that bounding box.
 *
 * @author Daniel Kluver
 */
public class Path extends GraphicsObject implements Strokable, Fillable {

    private Path2D.Float shape;
    private Paint fillColor;
    private Paint strokeColor;
    private boolean isFilled;
    private boolean isStroked;
    private boolean isClosed;
    private BasicStroke stroke;

    private int vertexCount;

    /**
     * Convenience method to create a triangle from three individual coordinates.
     */
    public static Path makeTriangle(double x0, double y0, double x1, double y1, double x2, double y2) {
        return new Path(
                new Point(x0, y0),
                new Point(x1, y1),
                new Point(x2, y2));
    }

    /**
     * Create a closed polygon whose vertices comes from the given list of points.
     * The list of points must not non-null.
     */
    public Path(List<Point> points) {
        this(points, true);
    }

    /**
     * Creates a closed polygon from vertices passed as separate arguments.
     */
    public Path(Point... points) {
        this(List.of(points));
    }

    /**
     * Create an optionally closed path whose vertices comes from the given list of points.
     * The list of points must not non-null.
     *
     * @param closed If true, a final line connects the end of the path back to the start, forming
     *  a polygon.
     */
    public Path(List<Point> points, boolean closed) {
        setVertices(points, closed);

        fillColor = Color.BLACK;
        setFilled(false);

        setStrokeColor(Color.BLACK);
        setStrokeWidth(1.0);
    }

    /**
     * Replaces the path's coordinates, preserving its open/close status. The coordinates are
     * relative to the path’s container; the method ignores the path’s current position.
     */
    public void setVertices(List<Point> points) {
        setVertices(points, isClosed());
    }

    /**
     * Changes the verices of this path, replacing any existing ones. The coordinates are
     * relative to the path’s container; the method ignores the path’s current position.
     *
     * @param closed If true, a final line connects the end of the path back to the start, forming
     *  a polygon.
     */
    public void setVertices(List<Point> points, boolean closed) {
        Objects.requireNonNull(points, "points");

        shape = new Path2D.Float(GeneralPath.WIND_EVEN_ODD, points.size());
        this.isClosed = closed;

        if(points.isEmpty()) {
            shape.moveTo(NaN, NaN);
        } else {
            shape.moveTo(points.get(0).getX(), points.get(0).getY());
            for (Point point : points.subList(1, points.size())) {
                shape.lineTo(point.getX(), point.getY());
            }
            if (closed) {
                shape.closePath();
            }
        }

        Rectangle shapeBounds = shape.getBounds();
        setPosition(shapeBounds.getX(), shapeBounds.getY());
        shape.transform(AffineTransform.getTranslateInstance(-getX(), -getY()));

        vertexCount = points.size();

        changed();
    }

    @Override
    protected void drawInLocalCoordinates(Graphics2D gc) {
        Paint originalColor = gc.getPaint();
        if (isFilled) {
            gc.setPaint(fillColor);
            gc.fill(shape);
        }
        if (isStroked) {
            gc.setStroke(stroke);
            gc.setPaint(strokeColor);
            gc.draw(shape);
        }
        gc.setPaint(originalColor); // set the color back to the original
    }

    @Override
    public Paint getFillColor() {
        return fillColor;
    }

    @Override
    public void setFillColor(Paint fillColor) {
        this.fillColor = fillColor;
        setFilled(true);
        changed();
    }

    @Override
    public Paint getStrokeColor() {
        return strokeColor;
    }

    @Override
    public void setStrokeColor(Paint strokeColor) {
        this.strokeColor = strokeColor;
        setStroked(true);
    }

    public boolean isFilled() {
        return isFilled;
    }

    public void setFilled(boolean filled) {
        isFilled = filled;
        changed();
    }

    public boolean isStroked() {
        return isStroked;
    }

    public void setStroked(boolean stroked) {
        isStroked = stroked;
        changed();
    }

    public double getStrokeWidth() {
        return stroke.getLineWidth();
    }

    public void setStrokeWidth(double width) {
        stroke = new BasicStroke((float) width, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
        setStroked(true);
    }

    /**
     * Returns true if this path loops back to its starting point.
     */
    public boolean isClosed() {
        return isClosed;
    }

    /**
     * Tests whether the given point is on the interior of this path. Does not account for stroke width.
     */
    @Override
    public boolean testHitInLocalCoordinates(double x, double y) {
        return shape.contains(x, y);
    }

    @Override
    public Rectangle2D getBounds() {
        return shape.getBounds2D();
    }

    @Override
    protected Object getEqualityAttributes() {
        return shape;
    }

    /**
     * Returns a string representation of the Polygon
     */
    @Override
    public String toString() {
        return "Polygon with " + vertexCount + " points at position (" + getX() + ", " + getY() + ") with width=" + getWidth() + " and height=" + getHeight();
    }
}
