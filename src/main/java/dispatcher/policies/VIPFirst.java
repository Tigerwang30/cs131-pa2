package dispatcher.policies;

import java.util.List;

import trip.Trip;

/**
 * The VIP First policy.
 * The trip with the lowest priority number goes first, since it is the most important.
 * Trips with the same priority number go in the order they joined the ready line.
 */
public class VIPFirst implements Policy {

    @Override
    public Trip pickNext(List<Trip> readyLine) {
        Trip nextTrip = readyLine.get(0);
        for (Trip trip : readyLine) {
            if (trip.getPriority() < nextTrip.getPriority()) {
                nextTrip = trip;
            }
        }
        return nextTrip;
    }
}
