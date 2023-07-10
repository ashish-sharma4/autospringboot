package com.weather.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
public class Weather {
    @Id
    private Long id;

    @Temporal(TemporalType.DATE)
    private Date dateRecorded;

    @Embedded
    private Location location;

    @JoinColumn
    @OnDelete(action = OnDeleteAction.CASCADE)
    @ElementCollection
    @CollectionTable(name = "weather_temperature")
    private List<Double> temperature;

    public Weather() {
    }

    public Weather(Long id, Date dateRecorded, Location location, List<Double> temperature) {
        this.id = id;
        this.dateRecorded = dateRecorded;
        this.location = location;
        this.temperature = temperature;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDateRecorded() {
        return dateRecorded;
    }

    public void setDateRecorded(Date dateRecorded) {
        this.dateRecorded = dateRecorded;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public List<Double> getTemperature() {
        return temperature;
    }

    public void setTemperature(List<Double> temperature) {
        this.temperature = temperature;
    }
}
