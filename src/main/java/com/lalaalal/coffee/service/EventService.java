package com.lalaalal.coffee.service;

import com.lalaalal.coffee.Configurations;
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
        LocalDate today = LocalDate.now();
        for (Event event : data.values()) {
            if (today.isAfter(event.getStart().minusDays(1))
                    && today.isBefore(event.getEnd().plusDays(1)))
                return event;
        }

        return null;
    }

    public Result addEvent(Event event) {
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
