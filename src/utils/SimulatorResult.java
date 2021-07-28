package utils;

public class SimulatorResult {
    public int totalSlots = 0;
    public int totalEmptySlots = 0;
    public int totalCollisionSlots = 0;
    public int totalCommands = 0;
    public double estimatorTotalExecutionTimeInNanoseconds = 0;
    public int estimatorCount = 0;

    public double getEstimatorAverageExecutionTimeInNanoseconds() {
        return estimatorTotalExecutionTimeInNanoseconds / estimatorCount;
    }

    public void clear() {
        totalSlots = 0;
        totalEmptySlots = 0;
        totalCollisionSlots = 0;
        totalCommands = 0;
        estimatorTotalExecutionTimeInNanoseconds = 0;
        estimatorCount = 0;
    }

    @Override
    public String toString() {
        return "utils.SimulatorResult {" +
                "\n    totalSlots = " + totalSlots +
                "\n    totalEmptySlots = " + totalEmptySlots +
                "\n    totalCollisionSlots = " + totalCollisionSlots +
                "\n    totalCommands = " + totalCommands +
                "\n    estimatorAverageExecutionTime = " + getEstimatorAverageExecutionTimeInNanoseconds() +
                "\n}";
    }
}
