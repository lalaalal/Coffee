package com.lalaalal.coffee.service;

import com.lalaalal.coffee.Language;
import com.lalaalal.coffee.Permission;
import com.lalaalal.coffee.config.Configurations;
import com.lalaalal.coffee.dto.MenuDTO;
import com.lalaalal.coffee.dto.OrderDTO;
import com.lalaalal.coffee.dto.OrderItemDTO;
import com.lalaalal.coffee.exception.ClientCausedException;
import com.lalaalal.coffee.misc.DateBasedKeyGenerator;
import com.lalaalal.coffee.misc.DelegateGetter;
import com.lalaalal.coffee.misc.KeyGenerator;
import com.lalaalal.coffee.model.Accessor;
import com.lalaalal.coffee.model.Event;
import com.lalaalal.coffee.model.Result;
import com.lalaalal.coffee.model.order.Order;
import com.lalaalal.coffee.model.order.OrderItem;
import com.lalaalal.coffee.registry.MenuRegistry;
import com.lalaalal.coffee.registry.PermissionRegistry;
import com.lalaalal.coffee.registry.Registries;
import lombok.Getter;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

@Service
public class OrderService extends DataStoreService<String, Order> {
    private static final Permission REQUIRED_EDIT_CURRENT_ORDER = Registries.get(PermissionRegistry.class, "edit.current_order_number");
    private final KeyGenerator<String> keyGenerator;
    @Getter
    private int currentOrderNumber;

    public OrderService() {
        super(String.class, Order.class, HashMap::new,
                Configurations.getConfiguration("data.path.order"));
        keyGenerator = new DateBasedKeyGenerator();
        keyGenerator.setKeySetSupplier(data::keySet);
    }

    public Result setCurrentOrderNumber(int number, Accessor accessor) {
        if (!accessor.canAccess(REQUIRED_EDIT_CURRENT_ORDER))
            return Result.forbidden("result.message.forbidden.set_current_order_number", accessor.whoami());

        currentOrderNumber = number;
        return Result.SUCCEED;
    }

    public void increaseCurrentOrderNumber() {
        this.currentOrderNumber += 1;
    }

    public List<MenuDTO> getMenuList(Event event, Language language) {
        ArrayList<MenuDTO> menuList = new ArrayList<>();
        Registries.get(MenuRegistry.class).values()
                .forEach(menu -> menuList.add(new MenuDTO(menu, event, language)));
        return menuList;
    }

    public Result addOrder(OrderDTO orderDTO, Event event) {
        String orderId = keyGenerator.generateKey();

        return addOrder(orderId, orderDTO, event);
    }

    public Result addOrder(String orderId, OrderDTO orderDTO, Event event) {
        Order order = orderDTO.convertToOrder(event);

        for (OrderItem item : order.getItems()) {
            if (!item.canMake())
                // TODO: 12/28/23 add translation
                throw new ClientCausedException("error.client.message.unable_to_make_menu", item.getMenuId());
        }

        order.setId(orderId);
        data.put(orderId, order);
        save();

        return Result.SUCCEED;
    }

    public Result addOrderItem(String orderId, OrderItemDTO orderItemDTO, Event event) {
        OrderItem orderItem = orderItemDTO.convertToOrderItem(event);
        if (!orderItem.canMake())
            throw new ClientCausedException("error.client.message.unable_to_make_menu", orderItem.getMenuId());

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

    public OrderDTO getOrder(String orderId) {
        return OrderDTO.convertFrom(data.get(orderId));
    }

    public Collection<OrderDTO> collectDTO() {
        ArrayList<OrderDTO> dtoList = new ArrayList<>();
        for (Order order : data.values())
            dtoList.add(OrderDTO.convertFrom(order));
        return dtoList;
    }

    public DelegateGetter<String, OrderDTO> delegateGetter() {
        return key -> OrderDTO.convertFrom(data.get(key));
    }
}
