package estimators;

import utils.Frame;
import utils.PowerOfTwoFrameSize;

public class SchouteEstimator implements Estimator {

    @Override
    public String toString() {
        return "Schoute";
    }

    @Override
    public long run(Frame f, Boolean powerOfTwoFrameSize) {
        if (powerOfTwoFrameSize)
            return PowerOfTwoFrameSize.get((long) Math.ceil(f.successful + f.collision * 2.37));
        return (long) Math.ceil(f.collision * 2.37);
    }
}
