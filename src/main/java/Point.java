import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

public class Point implements Serializable {
    private final float x;
    private final float y;
    private final float r;
    private boolean res;
    private final LocalDateTime requestTime;
    private long executionTime;

    Point(float x, float y, float r) {
        this.x = x;
        this.y = y;
        this.r = r;
        this.requestTime = LocalDateTime.now();
    }

    public float getX() {
        return this.x;
    }

    public float getY() {
        return this.y;
    }

    public float getR() {
        return this.r;
    }

    public void setRes(boolean res) {
        this.res = res;
    }

    public boolean getRes() {
        return this.res;
    }

    public LocalDateTime getRequestTime() {
        return this.requestTime;
    }

    public void setExecutionTime(long executionTime) {
        this.executionTime = executionTime;
    }

    public long getExecutionTime() {
        return this.executionTime;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o != null && this.getClass() == o.getClass()) {
            Point that = (Point)o;
            if (this.x != that.x) {
                return false;
            } else if (this.y != that.y) {
                return false;
            } else if (this.r != that.r) {
                return false;
            } else if (this.res != that.res) {
                return false;
            } else if (!this.requestTime.equals(that.requestTime)) {
                return false;
            } else {
                return this.executionTime == that.executionTime;
            }
        } else {
            return false;
        }
    }

    public int hashCode() {
        return Objects.hash(new Object[]{this.x, this.y, this.r, this.res, this.requestTime, this.executionTime});
    }

    public String toString() {
        return "Point{x='" + this.x + '\'' + "y='" + this.y + '\'' + "r='" + this.r + '\'' + "res='" + this.res + '\'' + "requestTime'" + this.requestTime + '\'' + "executionTime'" + this.executionTime + '\'' + '}';
    }
}
