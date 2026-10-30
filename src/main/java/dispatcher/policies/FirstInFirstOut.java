package dispatcher.policies;

import java.util.List;

import trip.Trip;

/**
 * The First In, First Out policy.
 * The vehicle that has been in the ready line longest goes first.
 */
public class FirstInFirstOut implements Policy {

    @Override
    public Trip pickNext(List<Trip> readyLine) {
        return readyLine.get(0);
    }
}
