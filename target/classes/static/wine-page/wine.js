let wine;
let preferences = {
    notifyOn: 5,
};

const getData = async () => {
    const urlChunks = window.location.href.split("/");
    let wineId = 0;
    for (let i = 0; i < urlChunks.length; i++) {
        if (urlChunks[i] == "wine") {
            wineId = urlChunks[i + 1];
        }
    }

    let request = await fetch("/api/v1/wines/" + wineId);
    if (request.ok) {
        wine = await request.json();
    }
};

class Wine_Cell {
    constructor(params) {
        let expected_fields = [
            "cell_id",
            "data_id",
            "save_url",
            { url_settings: ["add_id_wine", "add_data_id"] },
            "value",
            "add_euro_sign",
            "popup",
        ];
        this.params = new Params_Parser(expected_fields, params).params;

        this.cell_id = this.params.cell_id;
        this.cell_selector = `#${this.cell_id}`;

        this.popup = null;
        this.popup_id = this.params.cell_id + "-popup";
        this.popup_selector = `#${this.popup_id}`;

        this.events = new Events();

        this.create();
        this.init();
    }

    get() {
        return this.popup.get();
    }

    set(value, value_popup, parent) {
        parent = parent == undefined ? this : parent;

        let cell_value = value;
        if (parent.params.add_euro_sign == true) {
            cell_value += " &euro;";
        }
        $(parent.cell_selector).html(cell_value);

        if (parent.popup != null && value_popup != undefined) {
            parent.popup.set(value_popup);
        }
    }

    init() {
        let parent = this;
        // Add events
        let cell_container = $(this.cell_selector).parent();
        $(cell_container).on("click", function () {
            parent.popup.open();
        });
        this.popup.events.on("save", function () {
            parent.save(parent);
        });
    }

    save(parent) {
        parent = parent == undefined ? this : parent;
        if (parent.params.save_url == undefined) return;

        // Get data
        let value = parent.popup.get();
        let payload = wine;
        payload[parent.params.data_id] = value;

        $.ajax({
            url: "/api/v1/wines/" + wine.id + "/",
            dataType: "json",
            contentType: "application/json",
            data: JSON.stringify(payload),
            method: "PUT",
            success: function (response) {
                parent.popup.close();
                parent.events.trigger("saved", response[parent.params.data_id]);
            },
        });
    }

    create() {
        if (this.params.save_url != undefined) {
            this.params.popup.actions = [
                {
                    id: "save",
                    content: "Guardar",
                },
            ];
        }
        this.popup = new Popup(this.popup_id, this.params.popup);
        this.popup.create();
        this.popup.init();

        this.set(this.params.value);
    }
}

let quantity_cell;
const createQuantityCell = () => {
    quantity_cell = new Wine_Cell({
        cell_id: "quantity",
        data_id: "quantity",
        save_url: "/api/v1/wines/",
        url_settings: {
            add_id_wine: true,
            add_data_id: true,
        },
        value: wine.quantity,
        popup: {
            header: {
                title: "Existencias",
                subtitle: "Vino",
            },
            body: {
                number: wine.quantity,
                paragraph: "Numero de botellas restantes.",
            },
        },
    });
    check_quantity();

    quantity_cell.events.on("saved", function (value) {
        wine.quantity = parseInt(value);
        quantity_cell.set(value, value);
        check_quantity();
    });
};

let fromYear_cell;
const createFromYearCell = () => {
    fromYear_cell = new Wine_Cell({
        cell_id: "from-year",
        data_id: "fromYear",
        save_url: "/api/v1/wines/",
        url_settings: {
            add_id_wine: true,
            add_data_id: true,
        },
        value: wine.fromYear,
        popup: {
            header: {
                title: "Año",
                subtitle: "Vino",
            },
            body: {
                number: wine.fromYear,
                paragraph: "Año del vino",
            },
        },
    });

    fromYear_cell.events.on("saved", function (value) {
        wine.fromYear = parseInt(value);
        fromYear_cell.set(value, value);
        check_quantity();
    });
};

let rating_cell;
const createRatingCell = () => {
    rating_cell = new Wine_Cell({
        cell_id: "rating",
        data_id: "rating",
        save_url: "/api/v1/wines/",
        url_settings: {
            add_id_wine: true,
            add_data_id: true,
        },
        value: wine.rating,
        popup: {
            header: {
                title: "Rating",
                subtitle: "Vino",
            },
            body: {
                number: wine.rating,
                paragraph: "Rating del vino",
            },
        },
    });

    rating_cell.events.on("saved", function (value) {
        wine.rating = parseInt(value);
        rating_cell.set(value, value);
        check_quantity();
    });
};

let location_cell;
const createLocationCell = () => {
    location_cell = new Wine_Cell({
        cell_id: "location",
        data_id: "location",
        save_url: "/api/v1/wines/",
        url_settings: {
            add_id_wine: true,
            add_data_id: true,
        },
        value: wine.location,
        popup: {
            header: {
                title: "Ubicación",
                subtitle: "Vino",
            },
            body: {
                input: wine.location,
                paragraph: "Ubicación de las botellas.",
            },
        },
    });

    location_cell.events.on("saved", function (value) {
        wine.location = value;
        location_cell.set(value, value);
    });
};

let purchase_price_cell;
const createPurchasePriceCell = () => {
    purchase_price_cell = new Wine_Cell({
        cell_id: "purchase_price",
        data_id: "purchasePrice",
        save_url: "/api/v1/wines/",
        url_settings: {
            add_id_wine: true,
            add_data_id: true,
        },
        value: round_num_opt(wine.purchasePrice),
        add_euro_sign: true,
        popup: {
            header: {
                title: "Precio Adquisición",
                subtitle: "Vino",
            },
            body: {
                number: round_num_opt(wine.purchasePrice),
                paragraph: "Precio al que se adquirió la botella.",
            },
        },
    });

    purchase_price_cell.events.on("saved", function (value) {
        wine.purchase_price = parseFloat(value);
        value = round_num_opt(value);
        purchase_price_cell.set(value, value);
    });
};

let warning_popup;
const createWarningPopup = () => {
    // PAGE ACTIONS
    warning_popup = new Popup("quantity-warning", {
        header: {
            title: "¡Atención!",
            subtitle: "Fin de existencias",
        },
        body: {
            paragraph: "Se han acabado las botellas de " + wine.nombre + ".",
        },
    });
    warning_popup.create();
    warning_popup.init();

    $("#action-take").on("click", function () {
        if (wine.quantity - 1 < 0) {
            warning_popup.open();
            return;
        }
        quantity_cell.set(wine.quantity - 1, wine.quantity - 1);
        quantity_cell.save();
    });
};

let delete_popup;
const createDeletePopup = () => {
    delete_popup = new Popup("delete-wine-popup", {
        header: {
            title: "¡Atención!",
            subtitle: "Eliminar vino",
        },
        body: {
            paragraph: "¿Estás seguro de que quieres eliminar este vino?",
        },
        actions: [
            {
                id: "delete",
                content: "Eliminar",
            },
        ],
    });
    delete_popup.create();
    delete_popup.init();

    $("#delete-wine").on("click", function () {
        delete_popup.open();
    });

    delete_popup.events.on("delete", function () {
        $.ajax({
            url: "/api/v1/wines/" + wine.id + "/",
            method: "DELETE",
            dataType: "json",
            data: {
                csrfmiddlewaretoken: csrftoken,
            },
            success: function (response) {
                window.location = "/";
            },
        });
    });
};

let header;
const createHeader = () => {
    console.log(wine);
    // HEADER
    header = new Header({
        nav: true,
        title: wine.nombre,
        subtitle: wine.origin.nombre + " · " + wine.tipo.nombre,
        back: true,
    });
};

const createEvents = () => {
    $("#action-edit").on("click", function () {
        window.location = "/wine/" + wine.id + "/edit/";
    });
};

const init = async () => {
    await getData();
    createQuantityCell();
    createLocationCell();
    createPurchasePriceCell();
    createFromYearCell();
    createRatingCell();
    createWarningPopup();
    createDeletePopup();
    createHeader();
    createEvents();
};

function check_quantity() {
    if (wine.quantity <= preferences.notify_on) {
        if (!$(quantity_cell.cell_selector).hasClass("quantity low")) {
            $(quantity_cell.cell_selector).addClass("quantity low");
        }
    } else {
        if ($(quantity_cell.cell_selector).hasClass("quantity low")) {
            $(quantity_cell.cell_selector).removeClass("quantity low");
        }
    }
}

init();
