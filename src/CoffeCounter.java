import java.time.LocalTime;

/**
 * CoffeCounter class Calculates the amount of caffeine left in the body after
 * an amount of time.
 *
 * @author Simon Sørensen
 * @version 1.0
 */
public class CoffeCounter {
    private LocalTime timeInBody;
    private Double caffeineInBody;
    /**
     * The Constructor for CoffeCounter.
     *
     */
    public CoffeCounter() {
        caffeineInBody = 0.0;
    }

    /**
     * Calculate mg of Caffeine in the body after accumulated amount of time
     * <p>
     * The amount of caffeine in the body of a user is calculated by taking the
     * current time, converting it to minutes, and find the difference in minutes
     * from when the last cup of coffee was consumed OR from when the method was
     * last called. For each minute's difference, the percentile difference is
     * subtracted from the caffeineInBody. IFF the caffeineInBody gets below 1, the
     * caffeineInBody variable is set to 0, and the loop breaks.
     * </p>
     */
    public double mgInBody() {
        if (timeInBody != null) {
            LocalTime toCalculate = LocalTime.now();
            int hour = toCalculate.getHour();
            int minute = toCalculate.getMinute();
            int greaterInMinutes = (hour * 60) + (minute);
            int lesserInMinutes = ((timeInBody.getHour() * 60) + (timeInBody.getMinute()));
            double difference = (greaterInMinutes - lesserInMinutes);

            for (int i = 0; i < difference; i++) {
                Double x = caffeineInBody * 0.0018;
                caffeineInBody = caffeineInBody - x;
                if (caffeineInBody < 1) {
                    caffeineInBody = 0.0;
                    break;
                }
            }
        }

        return caffeineInBody;
    }

    /**
     *
     * @return LocalTime.now();
     */
    public LocalTime getTime() {
        return LocalTime.now();
    }

    /**
     *
     * @return timeInBody
     */
    public LocalTime getTimeInBody() {
        return timeInBody;
    }

    /**
     * sets the time to be the current time
     */

    public void setTimeInBody() {
        timeInBody = LocalTime.now();
    }

    /**
     *
     * @param time the time to be set
     */

    public void setTime2(LocalTime time) {
        timeInBody = time;
    }

    /**
     * @param caffeineInBody the caffeine in user's body
     */
    public void setCaffeineInBody(Double caffeineInBody) {
        this.caffeineInBody = caffeineInBody;
    }

}