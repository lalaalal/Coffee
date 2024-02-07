$(document).ready(function() {
    let target = $('#order-viewer-container')[0];
    const observer = new MutationObserver(mutationList =>
        mutationList.filter(m => m.type === 'childList').forEach(m => {
            m.addedNodes.forEach(loadOrder);
        }));
    observer.observe(target, {childList: true, subtree: false});
});

let loadOrder = function() {
    let orderId = $('#order-viewer-container')[0].dataset.orderId;
    $.ajax({
        url: `/api/order/${orderId}`,
        success: function(result) {
            order.items = result.items;
            console.log(result);
            order.onChange();
        }
    });
}