package com.lalaalal.coffee.service;

import com.lalaalal.coffee.config.Configurations;
import com.lalaalal.coffee.dto.OrderDTO;
import com.lalaalal.coffee.dto.ReservationDTO;
import com.lalaalal.coffee.exception.FatalError;
import com.lalaalal.coffee.initializer.Initialize;
import com.lalaalal.coffee.initializer.Initializer;
import com.lalaalal.coffee.misc.DelegateGetter;
import com.lalaalal.coffee.model.Accessor;
import com.lalaalal.coffee.model.Result;
import com.lalaalal.coffee.model.order.Reservation;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class ReservationService extends DataStoreService<String, Reservation>
        implements Initializer {
    public static final String DATE_TIME_PATTERN = "yyMMddHHmm";

    public ReservationService() throws FatalError {
        super(String.class, Reservation.class, HashMap::new,
                Configurations.getConfiguration("data.path.reservation"));
        Initializer.initialize(getClass(), Initialize.Time.Post);
    }

    public String createReservationId(ReservationDTO reservationDTO) {
        return reservationDTO.getTime().format(DateTimeFormatter.ofPattern(DATE_TIME_PATTERN));
    }

    public Result makeReservation(ReservationDTO reservationDTO, String hashedPassword) {
        String reservationId = createReservationId(reservationDTO);
        if (data.containsKey(reservationId))
            return Result.failed("result.message.failed.reservation_exists_at", reservationId);

        Reservation reservation = reservationDTO.convertToReservation(hashedPassword);
        reservation.setOrderId(reservationId);
        data.put(reservationId, reservation);
        save();
        return Result.SUCCEED;
    }

    public Result cancel(String id, String password) {
        if (!data.containsKey(id))
            return Result.failed("result.message.failed.no_such_reservation_id", id);
        Reservation reservation = data.get(id);
        if (!reservation.checkPassword(password))
            // TODO : trans
            return Result.forbidden("result.message.forbidden.cancel_reservation", reservation.getId());
        data.remove(id);
        save();
        return Result.SUCCEED;
    }

    public ReservationDTO getReservation(DelegateGetter<String, OrderDTO> delegate, String id, Accessor accessor) {
        Reservation reservation = data.get(id);
        return ReservationDTO.convertFrom(delegate, reservation, accessor);
    }

    public Map<String, List<ReservationDTO>> getMonthlyUsage(DelegateGetter<String, OrderDTO> delegate, Accessor accessor, int year, int month) {
        Map<String, List<ReservationDTO>> monthlyUsage = new HashMap<>();

        collectDTO(delegate, accessor)
                .stream()
                .filter(reservationDTO -> {
                    LocalDateTime dateTime = reservationDTO.getTime();
                    return dateTime.getYear() == year && dateTime.getMonthValue() == month;
                }).forEach(reservationDTO -> {
                    String name = reservationDTO.getName();
                    if (monthlyUsage.containsKey(name))
                        monthlyUsage.get(name).add(reservationDTO);
                    else {
                        ArrayList<ReservationDTO> list = new ArrayList<>();
                        list.add(reservationDTO);
                        monthlyUsage.put(name, list);
                    }
                });

        return monthlyUsage;
    }

    public List<ReservationDTO> collectDTO(DelegateGetter<String, OrderDTO> delegate, Accessor accessor) {
        return data.values().stream()
                .sorted(Comparator.comparing(Reservation::getOrderId))
                .map(reservation -> ReservationDTO.convertFrom(delegate, reservation, accessor))
                .toList();
    }
}