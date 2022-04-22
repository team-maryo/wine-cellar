let header = new Header({
    nav: true,
    title: "Inventario",
    subtitle: "Bodega - Las Tejuelas",
});

let fab_new_wine = new FAB("fab-new-wine", {
    scroll_on_overflow: true,
});

fab_new_wine.events.on("click", function () {
    window.location = "/wine/new/";
});

let nav = new Nav("nav-inventory");
nav.events.on("nav-running-low", function () {
    go_running_low();
});
nav.events.on("nav-settings", function () {
    go_settings();
});

function go_settings() {
    window.location = "/settings/";
}

function go_running_low() {
    window.location = "/running-low/";
}

let wines;
let preferences = {
    notify_on: 5,
};
let origins;
let tipos;

let inventory_search;

const init = async () => {
    let request = await fetch("/api/v1/wines");
    if (request.ok) {
        wines = await request.json();
    }

    request = await fetch("/api/v1/origins");
    if (request.ok) {
        origins = await request.json();
        console.log(origins);
    }

    request = await fetch("/api/v1/tipos");
    if (request.ok) {
        tipos = await request.json();
    }

    inventory_search = new Inventory_Search();
    inventory_search.init();
};

init();
