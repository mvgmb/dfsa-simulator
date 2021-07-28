package plots;

public abstract class Plot {
    public String title;
    public String xLabel;
    public String yLabel;

    public Plot(String title, String xLabel, String yLabel) {
        this.title = title;
        this.xLabel = xLabel;
        this.yLabel = yLabel;
    }
}
