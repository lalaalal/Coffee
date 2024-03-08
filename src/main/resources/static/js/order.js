const ORDER_ITEM_HTML = "<div class=\"order-item\"></div>"

$(document).ready(function () {
    $('#order-viewer-container').on('click', '.item-remove-button', function () {
        order.removeItem(this.value);
    });
});

class Order {
    constructor() {
        this.items = [];
    }

    addItem(item) {
        for (const element of this.items) {
            if (canBeCombined(element, item)) {
                element['arguments']['count'] += item['arguments']['count'];
                this.onChange();
                return;
            }
        }
        this.items.push(item);
        this.onChange();
    }

    removeItem(index) {
        this.items.splice(index, 1);
        this.onChange();
    }

    calculateCost() {
        let sum = 0;

        for (const item of this.items) {

            let menuName = item['menu'];
            let menu = menuList.getMenu(menuName);
            let cost = menu['cost'];
            // TODO : parameterize
            let count = item['count'];
            let decaffeinate = item['decaffeinate'];
            let shot = item['shot'];

            if (decaffeinate)
                cost += 500;
            if (shot)
                cost += shot * 500;
            sum += cost * count;
        }

        return sum;
    }

    onChange() {
        let text = '';
        for (const item of this.items) {
            text += this.makeOrderItemHTMLText(item)
        }
        if (this.items.length === 0)
            text = "<div class=\"order-item-container row-content\"><div class=\"order-item\">비어있음</div></div>"

        $('#order-viewer')[0].innerHTML = text;
    }

    makeOrderItemHTMLText(item) {
        let menu = menuList.getMenu(item['menu']);
        let name = menu['name'];

        let text = '';
        
        if (item['arguments']['temperature'])
            text += item['arguments']['temperature'] + ' ';
        text += name;
        if (item['arguments']['decaffeinated'])
            text += ' 디카페인';
        if (item['arguments']['shot']) {
            text += ' [' + item['arguments']['shot'] + ' 샷추가]';
        }
        text += ' × ' + item['arguments']['count'];
        let index = this.items.indexOf(item, 0);
        if ($('#order-viewer')[0].dataset.editable === "true")
            return `<div class="order-item-container row-direction-container row-content"><p class="row-content-item order-item">${text}</p><button class="rectangle-button rectangle-button--compact item-remove-button" value="${index}">제거</button></div>`;
        return `<div class="order-item-container row-direction-container row-content"><p class="row-content-item order-item">${text}</p></div>`;
    }
}

function canBeCombined(a, b) {
    if (a['menu'] !== b['menu'])
        return false;

    const CHECKING_ARGUMENTS = ['temperature', 'decaffeinated', 'shot'];
    for (const argument of CHECKING_ARGUMENTS) {
        if (!checkArgument(a, b, argument))
            return false;
    }
    
    return true;
}

function checkArgument(a, b, argument) {
    if (a['arguments'][argument] !== undefined) {
        if (a['arguments'][argument] !== b['arguments'][argument])
            return false;
    }
    return true;
}

let order = new Order();

