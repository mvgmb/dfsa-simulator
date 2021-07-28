package estimators;

import utils.Frame;
import utils.PowerOfTwoFrameSize;

public class EomLeeEstimator implements Estimator {
    double threshold;

    public EomLeeEstimator(double threshold) {
        this.threshold = threshold;
    }

    @Override
    public String toString() {
        return "Eom-Lee";
    }

    @Override
    public long run(Frame f, Boolean powerOfTwoFrameSize) {
        long l = f.size();
        double b;
        double y = 2;
        double diff;
        do {
            b = l / ((y * f.collision) + f.successful);
            double e = Math.pow(Math.E, -1 / b);
            double y0 = y;
            y = (1 - e) / (b * (1 - ((1 + (1 / b)) * e)));
            diff = Math.abs(y0 - y);
        } while (diff >= threshold);

        if (powerOfTwoFrameSize)
            return PowerOfTwoFrameSize.get((long) Math.ceil(y / b * f.collision));
        return (long) Math.ceil(y * f.collision);
    }
}
