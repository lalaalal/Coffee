package com.lalaalal.coffee.service;

import com.lalaalal.coffee.Configurations;
import com.lalaalal.coffee.dto.ReservationDTO;
import com.lalaalal.coffee.misc.DelegateGetter;
import com.lalaalal.coffee.model.Result;
import com.lalaalal.coffee.model.order.Order;
import com.lalaalal.coffee.model.order.Reservation;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;

@Service
public class ReservationService extends DataStoreService<String, Reservation> {
    public static final String DATE_TIME_PATTERN = "yyMMddHHmm";

    public ReservationService() {
        super(String.class, Reservation.class, HashMap::new,
                Configurations.getConfiguration("data.reservation.path"));
    }

    protected ReservationDTO convertToDTO(DelegateGetter<String, Order> delegate, Reservation reservation) {
        if (reservation == null)
            return null;
        Order order = delegate.get(reservation.getOrderId());
        return new ReservationDTO(
                reservation.getName(),
                reservation.getOrderer(),
                order,
                reservation.getTime()
        );
    }

    protected Reservation createReservation(ReservationDTO reservationDTO, String hashedPassword) {
        return new Reservation(
                reservationDTO.getName(),
                reservationDTO.getOrderer(),
                hashedPassword,
                reservationDTO.getOrder().getId(),
                reservationDTO.getTime()
        );
    }

    public String createReservationId(ReservationDTO reservationDTO) {
        return reservationDTO.getTime().format(DateTimeFormatter.ofPattern(DATE_TIME_PATTERN));
    }

    public Result makeReservation(ReservationDTO reservationDTO, String hashedPassword) {
        String reservationId = createReservationId(reservationDTO);
        if (data.containsKey(reservationId))
            return Result.failed("result.message.failed.reservation_exists_at", reservationId);

        Reservation reservation = createReservation(reservationDTO, hashedPassword);
        reservation.setOrderId(reservationId);
        data.put(reservationId, reservation);
        save();
        return Result.SUCCEED;
    }

    public Result cancel(String id) {
        if (!data.containsKey(id))
            return Result.failed("result.message.failed.no_such_reservation_id", id);
        data.remove(id);
        save();
        return Result.SUCCEED;
    }

    public ReservationDTO getReservation(DelegateGetter<String, Order> delegate, String id) {
        Reservation reservation = data.get(id);
        return convertToDTO(delegate, reservation);
    }

    public Collection<ReservationDTO> collectDTO(DelegateGetter<String, Order> delegate) {
        ArrayList<ReservationDTO> list = new ArrayList<>();
        data.values().stream()
                .sorted(Comparator.comparing(Reservation::getOrderId))
                .forEach(reservation -> list.add(convertToDTO(delegate, reservation)));
        return list;
    }
}