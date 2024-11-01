public class Vector2D {
    public double x;
    public double y;

    public Vector2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Vector2D() {
        this.x = 0;
        this.y = 0;
    }

    public void add(Vector2D v) {
        x += v.x;
        y += v.y;
    }

    public void mul(double f) {
        x = (x * f);
        y = (y * f);
    }

    public double lenght() {
        return Math.sqrt(x * x + y * y);
    }

    public void normalize() {
        double norm = this.lenght();
        if (norm != 0.0) {
            x = x / norm;
            y = y / norm;
        }
    }

    public double dot(Vector2D v) {
        return this.x * v.x + this.y * v.y;
    }

    public static Vector2D mul(Vector2D v1, Vector2D v2) {
        return new Vector2D(v1.x * v2.x, v1.y * v2.y);
    }

    public static Vector2D mul(Vector2D v1, double f) {
        return new Vector2D(v1.x * f, v1.y * f);
    }

    public static Vector2D add(Vector2D v1, Vector2D v2) {
        return new Vector2D(v1.x + v2.x, v1.y + v2.y);
    }

    public static double dot(Vector2D v1, Vector2D v2) {
        return v1.x * v2.x + v1.y * v2.y;
    }

    public static Vector2D normalize(Vector2D v) {
        double norm = v.lenght();
        return new Vector2D(v.x / norm, v.y / norm);
    }
}
