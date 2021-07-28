package utils;

import plots.Plot;

public class CsvExporter {
    public static void exportTitleValues(Plot pt) {
        String str = String.format("__plot__,%s,%s,%s", pt.title, pt.xLabel, pt.yLabel);
        System.out.println(str);
    }

    public static void exportPlotValues(String title, double[] values) {
        StringBuilder sb = new StringBuilder();
        sb.append(title);
        for (double val : values) {
            sb.append(",").append(val);
        }
        System.out.println(sb);
    }

    public static void exportPlotEnd() {
        System.out.println("__end__");
    }
}
