let purchase;
const getData = async () => {
    const urlChunks = window.location.href.split("/");
    let purchaseId = 0;
    for (let i = 0; i < urlChunks.length; i++) {
        if (urlChunks[i] == "purchases") {
            purchaseId = urlChunks[i + 1];
        }
    }

    let request = await fetch("/api/v1/extended/purchases/" + purchaseId);
    if (request.ok) {
        purchase = await request.json();
    }
};

class Purchase_Cell {
    constructor(params) {
        let expected_fields = [
            "cell_id",
            "data_id",
            "save_url",
            { url_settings: ["add_id_purchase", "add_data_id"] },
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
        let payload = purchase;
        payload[parent.params.data_id] = value;

        $.ajax({
            url: "/api/v1/purchases/" + purchase.purchaseId + "/",
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

let count_cell;
const createCountCell = () => {
    count_cell = new Purchase_Cell({
        cell_id: "count",
        data_id: "count",
        save_url: "/api/v1/purchases/",
        url_settings: {
            add_id_purchase: true,
            add_data_id: true,
        },
        value: purchase.count,
        popup: {
            header: {
                title: "Botellas",
                subtitle: "Vino",
            },
            body: {
                number: purchase.count,
                paragraph: "Número de botellas a comprar",
            },
        },
    });

    count_cell.events.on("saved", function (value) {
        purchase.count = parseInt(value);
        count_cell.set(value, value);
    });
};

let header;
const createHeader = () => {
    // HEADER
    header = new Header({
        nav: true,
        title: purchase.nombre,
        subtitle: purchase.location + " · " + purchase.price + "€",
        back: true,
    });
};

let delete_popup;
const createDeletePopup = () => {
    delete_popup = new Popup("delete-purchase-popup", {
        header: {
            title: "¡Atención!",
            subtitle: "Eliminar compra",
        },
        body: {
            paragraph: "¿Estás seguro de que quieres eliminar esta compra?",
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

    $("#delete-purchase").on("click", function () {
        delete_popup.open();
    });

    delete_popup.events.on("delete", function () {
        $.ajax({
            url: "/api/v1/purchases/" + purchase.purchaseId + "/",
            method: "DELETE",
            dataType: "json",
            success: function (response) {
                window.location = "/purchases";
            },
        });
    });
};

$("#action-add-one").on("click", function () {
    let value = purchase;
    value.count += 1;
    $.ajax({
        url: "/api/v1/purchases/" + purchase.purchaseId + "/",
        method: "PUT",
        contentType: "application/json",
        dataType: "json",
        data: JSON.stringify(value),
        success: function (response) {
            count_cell.set(value.count, value.count);
        },
    });
});

const init = async () => {
    await getData();
    createHeader();
    createCountCell();
    createDeletePopup();
};

init();
