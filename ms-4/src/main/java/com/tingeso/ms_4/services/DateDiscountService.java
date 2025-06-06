package com.tingeso.ms_4.services;

import com.tingeso.ms_4.DTOs.DiscountDTO;
import com.tingeso.ms_4.entities.DateDiscountEntity;
import com.tingeso.ms_4.repositories.DateDiscountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;

@Service
public class DateDiscountService {

    @Autowired
    DateDiscountRepository dateDiscountRepository;

    public double calculateBestDateDiscount(DiscountDTO dto) {
        LocalDate date = dto.getScheduleDate();
        LocalDate birthdate = dto.getBirthdate();
        Long userId = dto.getUserId();

        double specialDate = List.of(
                LocalDate.of(2025, 9, 18),
                LocalDate.of(2025, 12, 25)
        ).contains(date) ? 0.25 : 0.0;

        double weekend = (date.getDayOfWeek() == DayOfWeek.SATURDAY ||
                date.getDayOfWeek() == DayOfWeek.SUNDAY) ? 0.10 : 0.0;

        double birthday = (birthdate != null &&
                date.getMonth() == birthdate.getMonth() &&
                date.getDayOfMonth() == birthdate.getDayOfMonth()) ? 0.50 : 0.0;

        double bestDiscount = Stream.of(specialDate, weekend, birthday)
                .max(Double::compare)
                .orElse(0.0);

        String discountType = bestDiscount == birthday ? "birthday" :
                bestDiscount == specialDate ? "specialDate" :
                        bestDiscount == weekend ? "weekend" : "none";

        DateDiscountEntity discount = new DateDiscountEntity();
        discount.setScheduleDate(date);
        discount.setUserId(userId);
        discount.setDiscountApplied(bestDiscount);
        discount.setDiscountType(discountType);

        dateDiscountRepository.save(discount);

        return bestDiscount;
    }

    public List<DateDiscountEntity> getDiscountsByDate(LocalDate date) {
        return dateDiscountRepository.findByScheduleDate(date);
    }
}

