package utils;

public class Frame {
    public long empty;
    public long collision;
    public long successful;

    public Frame(long empty, long collision, long successful) {
        this.empty = empty;
        this.collision = collision;
        this.successful = successful;
    }

    public Frame() {
        this(0, 0, 0);
    }

    public void clear() {
        empty = 0;
        collision = 0;
        successful = 0;
    }

    public long size() {
        return empty + collision + successful;
    }
}
