package com.lalaalal.coffee.controller;

import com.lalaalal.coffee.dto.GroupDTO;
import com.lalaalal.coffee.dto.MenuDTO;
import com.lalaalal.coffee.model.Event;
import com.lalaalal.coffee.model.menu.Group;
import com.lalaalal.coffee.model.menu.Menu;
import com.lalaalal.coffee.registry.GroupRegistry;
import com.lalaalal.coffee.registry.Registries;
import com.lalaalal.coffee.service.EventService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.*;

@Controller
@RequestMapping("/order")
public class OrderController extends SessionHelper {
    private final EventService eventService;

    public OrderController(HttpSession httpSession, EventService eventService) {
        super(httpSession);
        this.eventService = eventService;
    }

    @GetMapping("/menu-selector")
    public String getMenuSelector(Model model) {
        GroupRegistry groupRegistry = Registries.get(GroupRegistry.class);
        Event event = eventService.getCurrentEvent();
        // TODO : add all menus tab
        List<MenuDTO> menuList = new ArrayList<>();
        List<GroupDTO> groupList = new ArrayList<>();
        Map<GroupDTO, List<MenuDTO>> menuTable = new HashMap<>();
        for (Group group : groupRegistry.values()) {
            GroupDTO groupDTO = new GroupDTO(group);
            groupList.add(groupDTO);
            List<MenuDTO> groupMenuList = new ArrayList<>();
            for (Menu menu : group) {
                MenuDTO menuDTO = new MenuDTO(menu, event, getUserLanguage());
                groupMenuList.add(menuDTO);
                menuList.add(menuDTO);
            }
            groupMenuList.sort(Comparator.comparingInt(menuDTO -> menuDTO.cost));
            menuTable.put(groupDTO, groupMenuList);
        }
        groupList.sort(Comparator.comparingInt(GroupDTO::getPriority));

        model.addAttribute("groupList", groupList);
        model.addAttribute("menuTable", menuTable);

        return "/order/menu-selector";
    }
}
