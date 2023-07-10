package com.weather.repository;

import com.weather.model.Weather;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Repository
public interface WeatherRepository extends JpaRepository<Weather, String> {
    List<Weather> findByLocationLatitudeAndLocationLongitude(Float lat, Float lon);

    List<Weather> findByDateRecorded(Date date);

    Weather findById(Long id);

    @Transactional
    @Modifying
    @Query("delete from Weather w where w.dateRecorded >= ?1 and w.dateRecorded <=?2 and w.location.latitude= ?3 and w.location.longitude= ?4")
    void deleteByDateRangeAndLocCoordinates(Date start, Date end, Float lat, Float lon);

    //should't be deleted
    List<Weather> findByLocationLatitudeAndLocationLongitudeAndDateRecordedBetween(float lat, float lon, Date date1, Date date2);

}
