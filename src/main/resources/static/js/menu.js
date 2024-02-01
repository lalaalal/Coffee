$(document).ready(function () {
    let target = $('#menu-selector-container')[0];
    const observer = new MutationObserver(mutationList =>
        mutationList.filter(m => m.type === 'childList').forEach(m => {
            m.addedNodes.forEach(loadMenu);
        }));
    observer.observe(target, {childList: true, subtree: false});
});

let loadMenu = function () {
    let tabs = $('.tab-item');
    for (const tab of tabs) {
        tab.addEventListener('click', function () {
            let id = tab.dataset.groupId;
            selectTabById(id);
        })
    }
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