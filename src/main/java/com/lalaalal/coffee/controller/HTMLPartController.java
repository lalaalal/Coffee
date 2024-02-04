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
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.*;

@Controller
@RequestMapping("/part")
public class HTMLPartController extends SessionHelper {
    private final EventService eventService;

    public HTMLPartController(HttpSession httpSession, EventService eventService) {
        super(httpSession);
        this.eventService = eventService;
    }

    @GetMapping("/header")
    public String header(Model model) {
        String title = getUserLanguage().translate("app.name");
        model.addAttribute("title", title);

        return "/part/header";
    }

    @GetMapping("/menu-selector")
    public String menuSelector(Model model, @RequestParam(value = "date", required = false) LocalDate date) {
        if (date == null)
            date = LocalDate.now();
        GroupRegistry groupRegistry = Registries.get(GroupRegistry.class);
        Event event = eventService.getEventAt(date);
        // TODO : add all menus tab
        List<MenuDTO> menuList = new ArrayList<>();
        List<GroupDTO> groupList = new ArrayList<>();
        Map<GroupDTO, List<MenuDTO>> menuTable = new HashMap<>();
        for (Group group : groupRegistry.values()) {
            GroupDTO groupDTO = new GroupDTO(group, getUserLanguage());
            groupList.add(groupDTO);
            List<MenuDTO> groupMenuList = new ArrayList<>();
            for (Menu menu : group) {
                MenuDTO menuDTO = new MenuDTO(menu, event, getUserLanguage());
                groupMenuList.add(menuDTO);
                menuList.add(menuDTO);
            }
            groupMenuList.sort(Comparator.comparingInt(MenuDTO::getCost));
            menuTable.put(groupDTO, groupMenuList);
        }

        model.addAttribute("groupList", groupList);
        model.addAttribute("menuTable", menuTable);

        return "/part/menu-selector";
    }

    @GetMapping("/order-viewer")
    public String order(Model model, @RequestParam(value = "editable", defaultValue = "false") Boolean editable) {
        model.addAttribute("editable", editable);
        return "/part/order-viewer";
    }
}
