

class Menu {
    constructor(id, name, cost) {
        this.id = id;
        this.name = name;
        this.cost = cost;
    }
}

class Order {
    constructor() {
        this.items = [];
    }

    calculateCost() {

    }
}

$(document).ready(function() {
    $.ajax({
        url: "/part/order/editable",
        success: function(result) {
            
        }
    });
})