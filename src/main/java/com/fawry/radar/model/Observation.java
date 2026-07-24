package com.fawry.radar.model;

import java.time.LocalDate;
import java.util.Objects;

public final class Observation {

    private final String plateNumber;
    private final LocalDate date;
    private final CarType carType;
    private final int speed;
    private final boolean seatbeltFastened;

    private Observation(Builder builder) {
        this.plateNumber = Objects.requireNonNull(builder.plateNumber, "plateNumber is required");
        this.date = Objects.requireNonNull(builder.date, "date is required");
        this.carType = Objects.requireNonNull(builder.carType, "carType is required");
        this.speed = builder.speed;
        this.seatbeltFastened = builder.seatbeltFastened;
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public LocalDate getDate() {
        return date;
    }

    public CarType getCarType() {
        return carType;
    }

    public int getSpeed() {
        return speed;
    }

    public boolean isSeatbeltFastened() {
        return seatbeltFastened;
    }

    public static Builder builder() {
        return new Builder();
    }

    /** Builder keeps Observation immutable while allowing readable construction. */
    public static final class Builder {
        private String plateNumber;
        private LocalDate date;
        private CarType carType;
        private int speed;
        private boolean seatbeltFastened;

        public Builder plateNumber(String plateNumber) {
            this.plateNumber = plateNumber;
            return this;
        }

        public Builder date(LocalDate date) {
            this.date = date;
            return this;
        }

        public Builder carType(CarType carType) {
            this.carType = carType;
            return this;
        }

        public Builder speed(int speed) {
            this.speed = speed;
            return this;
        }

        public Builder seatbeltFastened(boolean seatbeltFastened) {
            this.seatbeltFastened = seatbeltFastened;
            return this;
        }

        public Observation build() {
            return new Observation(this);
        }
    }

    @Override
    public String toString() {
        return "Observation{plate='" + plateNumber + "', date=" + date +
                ", carType=" + carType + ", speed=" + speed +
                ", seatbeltFastened=" + seatbeltFastened + '}';
    }
}
