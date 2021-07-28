import estimators.EomLeeEstimator;
import estimators.Estimator;
import estimators.LowerBoundEstimator;
import estimators.SchouteEstimator;
import plots.*;
import utils.CsvExporter;
import utils.Frame;
import utils.SimulatorResult;

public class Simulator {
    int initialTagCount = 100;
    int incrementStep = 100;
    int maxTagCount = 1000;
    int repetitionPerTagCount = 2000;
    int initialFrameSize = 64;
    boolean powerOfTwoFrameSize = false;
    Estimator[] estimators = new Estimator[]{
            new LowerBoundEstimator(),
            new EomLeeEstimator(0.001),
            new SchouteEstimator(),
    };

    enum PlotType {
        TOTAL_SLOTS,
        EMPTY_SLOTS,
        COLLISION_SLOTS,
        TOTAL_COMMANDS,
        ESTIMATOR_AVG_TIME
    }

    private final Plot[] plots = new Plot[]{
            new TotalSlots(),
            new EmptySlots(),
            new CollisionSlots(),
            new TotalCommands(),
            new EstimatorAvgTime(),
    };

    public void run() {
        SimulatorResult res = new SimulatorResult();
        int numberOfValues = maxTagCount / incrementStep;

        double[] xValues = new double[numberOfValues];
        for (int i = initialTagCount; i <= maxTagCount; i += incrementStep) {
            xValues[(i - initialTagCount) / incrementStep] = i;
        }

        double[][][] plotValues = new double[plots.length][estimators.length][numberOfValues];
        int estimatorIndex = 0;
        for (Estimator e : estimators) {
            for (int i = initialTagCount; i <= maxTagCount; i += incrementStep) {
                int index = (i - initialTagCount) / incrementStep;
                for (int j = 0; j < repetitionPerTagCount; j++) {
                    runSimulator(res, e, i, initialFrameSize);

                    plotValues[PlotType.TOTAL_SLOTS.ordinal()][estimatorIndex][index] += res.totalSlots;
                    plotValues[PlotType.EMPTY_SLOTS.ordinal()][estimatorIndex][index] += res.totalEmptySlots;
                    plotValues[PlotType.COLLISION_SLOTS.ordinal()][estimatorIndex][index] += res.totalCollisionSlots;
                    plotValues[PlotType.TOTAL_COMMANDS.ordinal()][estimatorIndex][index] += res.totalCommands;
                    plotValues[PlotType.ESTIMATOR_AVG_TIME.ordinal()][estimatorIndex][index] += res.getEstimatorAverageExecutionTimeInNanoseconds();

                    res.clear();
                }
            }

            for (double[][] pv : plotValues)
                for (int i = 0; i < pv[estimatorIndex].length; i++)
                    pv[estimatorIndex][i] /= repetitionPerTagCount;

            estimatorIndex++;
        }

        for (int i = 0; i < plots.length; i++) {
            CsvExporter.exportTitleValues(plots[i]);
            CsvExporter.exportPlotValues("__x__", xValues);
            for (int j = 0; j < estimators.length; j++)
                CsvExporter.exportPlotValues(estimators[j].toString(), plotValues[i][j]);
            CsvExporter.exportPlotEnd();
        }
    }

    public void runSimulator(SimulatorResult res, Estimator e, int tagsCount, long frameSize) {
        Frame frame = new Frame();
        while (true) {
            res.totalSlots += frameSize;
            frame.clear();

            int curTagsCount = tagsCount;
            for (int i = 0; i < frameSize; ++i) {
                res.totalCommands++; // COMMAND slot init

                long numberOfTransmissions = 0;
                double chanceTagWillTransmit = 1.0 / (frameSize - i);

                // Simulate tag transmissions
                for (int j = 0; j < curTagsCount; ++j)
                    // [0,1) => [0,x) and [x,1)
                    if (Math.random() < chanceTagWillTransmit)
                        ++numberOfTransmissions;

                curTagsCount -= numberOfTransmissions;

                // Set slot result
                if (numberOfTransmissions == 1) {
                    ++frame.successful;
                    --tagsCount;
                    ++res.totalCommands; // COMMAND silence tag
                } else if (numberOfTransmissions == 0) {
                    ++frame.empty;
                    ++res.totalEmptySlots;
                } else {
                    ++frame.collision;
                    ++res.totalCollisionSlots;
                }
            }

            if (tagsCount == 0)
                break;

            // Estimate next frame size
            long startTime = System.nanoTime();
            frameSize = e.run(frame, powerOfTwoFrameSize);
            long endTime = System.nanoTime();
            res.estimatorTotalExecutionTimeInNanoseconds += endTime - startTime;
            ++res.estimatorCount;
        }
    }
}
