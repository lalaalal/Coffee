package com.lalaalal.coffee.service;

import com.lalaalal.coffee.config.Configurations;
import com.lalaalal.coffee.misc.IntegerKeyGenerator;
import com.lalaalal.coffee.misc.KeyGenerator;
import com.lalaalal.coffee.model.Event;
import com.lalaalal.coffee.model.Result;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;

@Service
public class EventService extends DataStoreService<Integer, Event> {
    private final KeyGenerator<Integer> keyGenerator;

    public EventService() {
        super(Integer.class, Event.class, HashMap::new,
                Configurations.getConfiguration("data.event.path"));
        keyGenerator = new IntegerKeyGenerator();
        keyGenerator.setKeySetSupplier(data::keySet);
    }

    public Event getCurrentEvent() {
        return getEventAt(LocalDate.now());
    }

    public Event getEventAt(LocalDate date) {
        if (date == null)
            return getCurrentEvent();
        for (Event event : data.values()) {
            if (date.isAfter(event.getStart().minusDays(1))
                    && date.isBefore(event.getEnd().plusDays(1)))
                return event;
        }

        return Event.USUAL;
    }

    public Result addEvent(Event event) {
        // TODO : check date conflict

        int id = keyGenerator.generateKey();
        data.put(id, event);
        save();

        return Result.SUCCEED;
    }

    public Result cancel(int id) {
        if (!data.containsKey(id))
            // TODO : translate
            return Result.failed("result.message.failed.no_such_event_id", id);
        data.remove(id);
        save();
        return Result.SUCCEED;
    }
}
