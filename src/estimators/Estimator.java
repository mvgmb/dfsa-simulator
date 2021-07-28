package estimators;

import utils.Frame;

public interface Estimator {
    long run(Frame f, Boolean powerOfTwoFrameSize);
}
