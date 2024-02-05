class MenuList {
    constructor(list) {
        this.list = list;
    }

    getMenu(id) {
        return this.list.find((element) => element['id'] === id);
    }
}

let menuList;
let currentMenu;

$(document).ready(function () {
    let target = $('#menu-selector-container')[0];
    const observer = new MutationObserver(mutationList =>
        mutationList.filter(m => m.type === 'childList').forEach(m => {
            m.addedNodes.forEach(loadMenu);
        }));
    observer.observe(target, {childList: true, subtree: false});

    $.ajax({
        url: "/api/order/menu",
        success: function (result) {
            menuList = new MenuList(result);
        }
    });

    $('#menu-selector-container').on('click', '.tab-item', function() {
        let id = this.dataset.groupId;
        selectTabById(id);
    });
    $('#menu-selector-container').on('click', '.menu-item', function() {
        let menuId = this.dataset.menuId;
        let menu = menuList.getMenu(menuId);
        currentMenu = menu;
        $('#selected-menu').text(menu['name']);
    });
    $('#menu-selector-container').on('click', '#add-menu-button', function() {
        if (currentMenu == null)
            return;

        let item = {};
        item['menu'] = currentMenu['id'];
        item['arguments'] = {}
        let arguments = item['arguments'];
        for (const argument of currentMenu['required_arguments']) {
            if (argument === 'count')
                arguments[argument] = $('#count')[0].value *= 1;
            if (argument === 'decaffeinated')
                arguments[argument] = $('#decaffeinated')[0].checked;
            if (argument === 'shot')
                arguments[argument] = $('#shot')[0].value *= 1;
            if (argument == 'temperature') {
                let checkedButton = getCheckedRadioButton('temperature');
                if (checkedButton === null)
                    arguments[argument] = 'ICE';
                else
                    arguments[argument] = checkedButton.dataset.temperature;
            }
        }
        order.addItem(item);
    });
});

function getCheckedRadioButton(name) {
    for (const button of $(`input[name=${name}]`)) {
        if (button.checked)
            return button;
    }

    return null;
}

let loadMenu = function () {
    let tabs = $('.tab-item');

    let firstGroupId = tabs[0].dataset.groupId;
    selectTabById(firstGroupId);
}

function selectTabById(id) {
    let tabs = $('.tab-item');
    for (const tab of tabs) {
        if (tab.dataset.groupId === id)
            tab.classList.add('gray-background');
        else
            tab.classList.remove('gray-background');
    }

    let menuContainers = $('.menu-container');
    for (const menuContainer of menuContainers) {
        if (menuContainer.dataset.groupId === id)
            $(menuContainer).show();
        else $(menuContainer).hide();
    }
}