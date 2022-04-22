let header = new Header({
    nav: true,
    title: "Reponer",
    subtitle: "Bodega - Las Tejuelas",
});

let fab_new_wine = new FAB("fab-new-wine", {
    scroll_on_overflow: true
});

fab_new_wine.events.on("click", function() {
    window.location = "/wine/new/";
});

let nav = new Nav("nav-running-low");
nav.events.on("nav-inventory", function() {
    go_inventory();
});
nav.events.on("nav-settings", function() {
    go_settings();
});

function go_settings() {
    window.location = "/settings/";
}

function go_inventory() {
    window.location = "/";
}

let wines = JSON.parse($("data#data-wines").html());
let preferences = JSON.parse($("data#data-preferences").html());
let origins = JSON.parse($("data#data-origins").html());
let tipos = JSON.parse($("data#data-tipos").html());

let inventory_search = new Inventory_Search();
inventory_search.init();