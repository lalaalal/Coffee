package com.lalaalal.coffee.service;

import com.lalaalal.coffee.Configurations;
import com.lalaalal.coffee.misc.DateBasedKeyGenerator;
import com.lalaalal.coffee.misc.DelegateGetter;
import com.lalaalal.coffee.misc.KeyGenerator;
import com.lalaalal.coffee.model.Result;
import com.lalaalal.coffee.model.order.Order;
import com.lalaalal.coffee.model.order.OrderItem;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;

@Service
public class OrderService extends DataStoreService<String, Order> {
    private final KeyGenerator<String> keyGenerator;

    public OrderService() {
        super(String.class, Order.class, HashMap::new,
                Configurations.getConfiguration("data.order.path"));
        keyGenerator = new DateBasedKeyGenerator();
        keyGenerator.setKeySetSupplier(data::keySet);
    }

    public Result addOrder(Order order) {

        String orderId = keyGenerator.generateKey();
        data.put(orderId, order);
        order.setId(orderId);

        save();
        return Result.SUCCEED;
    }

    public Result addOrder(String orderId, Order order) {
        data.put(orderId, order);
        save();

        return Result.SUCCEED;
    }

    public Result addOrderItem(String orderId, OrderItem orderItem) {
        Order order = data.get(orderId);
        if (order == null)
            return Result.failed("result.message.failed.no_such_order_id", orderId);
        order.add(orderItem);
        save();

        return Result.SUCCEED;
    }

    public Result cancelOrder(String orderId) {
        if (!data.containsKey(orderId))
            return Result.failed("result.message.failed.no_such_order_id", orderId);

        data.remove(orderId);
        save();
        return Result.SUCCEED;
    }

    public Result cancelMenu(String orderId, String menuId) {
        Order order = data.get(orderId);
        if (order == null)
            return Result.failed("result.message.failed.no_such_order_id", orderId);
        if (!order.containsMenu(menuId))
            return Result.failed("result.message.failed.menu_not_exist", orderId, menuId);
        order.remove(menuId);
        save();
        return Result.SUCCEED;
    }

    public Order getOrder(String orderId) {
        return data.get(orderId);
    }

    public Collection<Order> collect() {
        return data.values().stream()
                .sorted(Comparator.comparing(Order::getId))
                .toList();
    }

    public DelegateGetter<String, Order> delegateGetter() {
        return data::get;
    }
}
