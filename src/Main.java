import estimators.EomLeeEstimator;
import estimators.Estimator;
import estimators.LowerBoundEstimator;
import estimators.SchouteEstimator;

public class Main {
    public static void main(String[] args) {
        Simulator s = new Simulator();
        s.initialTagCount = 100;
        s.incrementStep = 100;
        s.maxTagCount = 1000;
        s.repetitionPerTagCount = 2000;
        s.initialFrameSize = 64;
        s.powerOfTwoFrameSize = true;
        s.estimators = new Estimator[]{
                new LowerBoundEstimator(),
                new EomLeeEstimator(0.001),
                new SchouteEstimator(),
        };
        s.run();
    }
}
