package estimators;

import utils.Frame;
import utils.PowerOfTwoFrameSize;

public class LowerBoundEstimator implements Estimator {
    @Override
    public String toString() {
        return "Lower Bound";
    }

    @Override
    public long run(Frame f, Boolean powerOfTwoFrameSize) {
        if (powerOfTwoFrameSize)
            return PowerOfTwoFrameSize.get(f.successful + f.collision * 2);
        return f.collision * 2;
    }
}
