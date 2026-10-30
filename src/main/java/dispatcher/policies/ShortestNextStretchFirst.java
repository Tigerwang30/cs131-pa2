package dispatcher.policies;

import java.util.List;

import trip.Trip;

/**
 * The Shortest Next Stretch First policy.
 * The vehicle that is expected to need the driver for the fewest ticks will be driven next.
 * Trips with the same estimate go in the order they joined the ready line.
 * <p>
 * The expectation is estimated by exponential averaging with the given alpha, going through the trip's stretch
 * lengths ({@code trip.getStretchLengths()}) oldest first:
 * new estimate = alpha * old estimate + (1 - alpha) * length of the stretch.
 * A new trip's estimate starts at 0. If the old estimate is 0, the new estimate is the length of the stretch.
 * The trip's priority number is not used.
 */
public class ShortestNextStretchFirst implements Policy {

    private final double alpha;

    public ShortestNextStretchFirst(double alpha) {
        this.alpha = alpha;
    }

    @Override
    public Trip pickNext(List<Trip> readyLine) {
        Trip bestTrip = null;
        double bestEstimation = Double.MAX_VALUE;

        for (Trip trip : readyLine) {
            double estimation = getEstimate(trip);
            if (estimation < bestEstimation) {
                bestEstimation = estimation;
                bestTrip = trip;
            }
        }
        return bestTrip;
    }

    /**
     * Returns the estimated length of the given trip's next stretch.
     *
     * @param trip the trip
     * @return the estimate, or 0 if the trip has not been driven yet
     */
    public double getEstimate(Trip trip) {
        double guess = 0; 
        for (int length : trip.getStretchLengths()) {
            if (guess == 0) {
                guess = length;
            } else {
                guess = alpha * guess + (1 - alpha) * length;
            }
        }
        return guess; 
    }
}
