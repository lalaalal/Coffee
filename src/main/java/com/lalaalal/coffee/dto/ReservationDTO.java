package com.lalaalal.coffee.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.lalaalal.coffee.Permission;
import com.lalaalal.coffee.initializer.Initialize;
import com.lalaalal.coffee.misc.DelegateGetter;
import com.lalaalal.coffee.misc.TextHider;
import com.lalaalal.coffee.model.Accessor;
import com.lalaalal.coffee.model.order.Reservation;
import com.lalaalal.coffee.registry.PermissionRegistry;
import com.lalaalal.coffee.registry.Registries;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@JsonPropertyOrder({"id", "name", "contact", "time", "order", "comment", "for_meeting"})
public class ReservationDTO {
    private static Permission READ_RESERVATION_NAME;
    private static Permission READ_RESERVATION_CONTACT;

    @Initialize(with = Registries.class)
    public static void initialize() {
        READ_RESERVATION_NAME = Registries.get(PermissionRegistry.class, "read.reservation.name");
        READ_RESERVATION_CONTACT = Registries.get(PermissionRegistry.class, "read.reservation.contact");
    }

    private final String name;
    private final String contact;
    private final OrderDTO order;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private final LocalDateTime time;
    // TODO : rename to message
    private final String message;
    @JsonProperty("for_meeting")
    private final boolean forMeeting;

    @JsonCreator
    public ReservationDTO(
            @JsonProperty("name") String name,
            @JsonProperty("contact") String contact,
            @JsonProperty("order") OrderDTO order,
            @JsonProperty("time") LocalDateTime time,
            @JsonProperty("message") String message,
            @JsonProperty(value = "for_meeting", defaultValue = "true") boolean forMeeting) {
        this.name = name;
        this.contact = contact;
        this.order = order;
        this.time = time;
        this.message = message;
        this.forMeeting = forMeeting;
    }

    public static ReservationDTO convertFrom(DelegateGetter<String, OrderDTO> delegate, Reservation reservation, Accessor accessor) {
        if (reservation == null)
            return null;
        return new ReservationDTO(
                hideText(accessor, READ_RESERVATION_NAME, reservation.getName(), TextHider.SHOW_FIRST_CHARACTER),
                hideText(accessor, READ_RESERVATION_CONTACT, reservation.getContact(), TextHider.HIDE_ALL),
                delegate.get(reservation.getOrderId()),
                reservation.getTime(),
                reservation.getMessage(),
                reservation.isForMeeting()
        );
    }

    protected static String hideText(Accessor accessor, Permission required, String originalText, TextHider textHider) {
        if (accessor.canAccess(required))
            return originalText;
        return textHider.hide(originalText);
    }

    @JsonProperty("id")
    public String getId() {
        return order.getId();
    }

    public Reservation convertToReservation(String hashedPassword) {
        return new Reservation(
                this.getName(),
                this.getContact(),
                hashedPassword,
                this.order.getId(),
                this.getTime(),
                this.getMessage(),
                this.isForMeeting()
        );
    }
}
