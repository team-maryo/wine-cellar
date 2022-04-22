let wine = JSON.parse($("data#data-wine").html());
let preferences = JSON.parse($("data#data-preferences").html()); 

class Wine_Cell {
    constructor(params) {
        let expected_fields = [
            "cell_id",
            "data_id",
            "save_url",
            {"url_settings": ["add_id_wine", "add_data_id"]},
            "value",
            "add_euro_sign",
            "popup"
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
        $(cell_container).on("click", function() {
            parent.popup.open();
        });
        this.popup.events.on("save", function() {
            parent.save(parent);
        });
    }

    save(parent) {
        parent = parent == undefined ? this : parent;
        if (parent.params.save_url == undefined) return;
        // Get data
        let value = parent.popup.get();
        let payload = {};
        payload[parent.params.data_id] = value;

        let url = parent.params.save_url;
        if (parent.params.url_settings.add_id_wine) {
            url += wine.id_wine + "/";
        }

        if (parent.params.url_settings.add_data_id) {
            url += parent.params.data_id + "/";
        }

        $.ajax({
            url: url,
            dataType: "json",
            data: {
                csrfmiddlewaretoken: csrftoken,
                payload: JSON.stringify(payload)
            },
            method: "POST",
            success: function(response) {
                parent.popup.close();
                parent.events.trigger("saved", response);
            }
        })
    }

    create() {
        if (this.params.save_url != undefined) {
            this.params.popup.actions = [{
                id: "save",
                content: "Guardar"
            }];
        }
        this.popup = new Popup(this.popup_id, this.params.popup);
        this.popup.create();
        this.popup.init();

        this.set(this.params.value);
    }
}

let selling_price_cell = new Wine_Cell({
    cell_id: "selling_price",
    data_id: "selling_price",
    save_url: "/set/wine/",
    url_settings: {
        add_id_wine: true,
        add_data_id: true
    },
    value: round_num_opt(wine.selling_price),
    add_euro_sign: true,
    popup: {
        header: {
            title: "PVP Carta",
            subtitle: "Vino",
        },
        body: {
            number: round_num_opt(wine.selling_price),
            paragraph: "Precio que aparece en la carta."
        }
    }
});

selling_price_cell.events.on("saved", function(response) {
    let value = response.value;
    wine.selling_price = parseFloat(value);
    value = round_num_opt(value);
    selling_price_cell.set(value, value);
});


let quantity_cell = new Wine_Cell({
    cell_id: "quantity",
    data_id: "quantity",
    save_url: "/set/wine/",
    url_settings: {
        add_id_wine: true,
        add_data_id: true
    },
    value: wine.quantity,
    popup: {
        header: {
            title: "Existencias",
            subtitle: "Vino",
        },
        body: {
            number: wine.quantity,
            paragraph: "Numero de botellas restantes."
        }
    }
});
check_quantity();

quantity_cell.events.on("saved", function(response) {
    let value = response.value;
    wine.quantity = parseInt(value);
    quantity_cell.set(value, value);
    check_quantity();
});

let location_cell = new Wine_Cell({
    cell_id: "location",
    data_id: "location",
    save_url: "/set/wine/",
    url_settings: {
        add_id_wine: true,
        add_data_id: true
    },
    value: wine.location,
    popup: {
        header: {
            title: "Ubicación",
            subtitle: "Vino",
        },
        body: {
            input: wine.location,
            paragraph: "Ubicación de las botellas."
        }
    }
});

location_cell.events.on("saved", function(response) {
    let value = response.value;
    wine.location = parseFloat(value);
    location.set(value, value);
});

let purchase_price_cell = new Wine_Cell({
    cell_id: "purchase_price",
    data_id: "purchase_price",
    save_url: "/set/wine/",
    url_settings: {
        add_id_wine: true,
        add_data_id: true
    },
    value: round_num_opt(wine.purchase_price),
    add_euro_sign: true,
    popup: {
        header: {
            title: "Precio Adquisición",
            subtitle: "Vino",
        },
        body: {
            number: round_num_opt(wine.purchase_price),
            paragraph: "Precio al que se adquirió la botella."
        }
    }
});

purchase_price_cell.events.on("saved", function(response) {
    let value = response.value;
    wine.purchase_price = parseFloat(value);
    value = round_num_opt(value);
    purchase_price_cell.set(value, value);
});

let theoretical_pvp = parseFloat(wine.purchase_price) * (parseFloat(preferences.benefits_margin) + 100) / 100;
let adjusted_pvp = trunc_round(theoretical_pvp);
theoretical_pvp = round_num_opt(theoretical_pvp);

let theoretical_pvp_cell = new Wine_Cell({
    cell_id: "theoretical_pvp",
    data_id: "benefits_margin",
    save_url: "/set/preferences/",
    url_settings: {
        add_data_id: true
    },
    value: theoretical_pvp,
    add_euro_sign: true,
    popup: {
        header: {
            title: "Margen de beneficio",
            subtitle: "Preferencias"
        },
        body: {
            number: round_num_opt(preferences.benefits_margin),
            paragraph: "Porcentaje que se añade al precio de compra."
        }
    }
});

theoretical_pvp_cell.events.on("saved", function(response) {
    let value = response.value;
    preferences.benefits_margin = parseFloat(value);
    
    // Redo operations
    theoretical_pvp = parseFloat(wine.purchase_price) * (parseFloat(preferences.benefits_margin) + 100) / 100;
    adjusted_pvp = trunc_round(theoretical_pvp);
    theoretical_pvp = round_num_opt(theoretical_pvp);

    // Set new values
    theoretical_pvp_cell.set(theoretical_pvp, round_num_opt(value));
    adjusted_pvp_cell.set(adjusted_pvp);
});

let adjusted_pvp_cell = new Wine_Cell({
    cell_id: "adjusted_pvp",
    value: adjusted_pvp,
    add_euro_sign: true,
    popup: {
        header: {
            title: "PVP Ajustado",
            subtitle: "Vino"
        },
        body: {
            paragraph: "Este elemento no se puede modificar. Es el resultado de redondear el PVP Teórico.",
        }
    }
});


let lavinia_code_cell = new Wine_Cell({
    cell_id: "lavinia_code",
    data_id: "lavinia_code",
    save_url: "/set/wine/",
    url_settings: {
        add_id_wine: true,
        add_data_id: true
    },
    value: wine.lavinia_code,
    popup: {
        header: {
            title: "Código Lavinia",
            subtitle: "Vino",
        },
        body: {
            input: wine.lavinia_code,
            paragraph: "Código lavinia asociado a la botella."
        }
    }
});

lavinia_code_cell.events.on("saved", function(response) {
    let value = response.value;
    wine.lavinia_code = value;
    lavinia_code.set(value, value);
});

let delete_popup = new Popup("delete-wine-popup", {
    header: {
        title: "¡Atención!",
        subtitle: "Eliminar vino"
    },
    body: {
        paragraph: "¿Estás seguro de que quieres eliminar este vino?"
    },
    actions: [{
        id: "delete",
        content: "Eliminar"
    }]
});
delete_popup.create();
delete_popup.init();

$("#delete-wine").on("click", function() {
    delete_popup.open();
});

delete_popup.events.on("delete", function() {
    $.ajax({
        url: "/delete/wine/" + wine.id_wine + "/",
        method: "POST",
        dataType: "json",
        data: {
            csrfmiddlewaretoken: csrftoken,
        },
        success: function(response) {
            window.location = "/";
        }
    });
});

// HEADER

let header = new Header({
    nav: true,
    title: wine.nombre,
    subtitle: wine.origin.nombre + " · " + wine.tipo.nombre,
    back: true
});

// PAGE ACTIONS
let warning_popup = new Popup("quantity-warning", {
    header : {
        title: "¡Atención!",
        subtitle: "Fin de existencias"
    },
    body : {
        paragraph: "Se han acabado las botellas de " + wine.nombre + "."
    }
});
warning_popup.create();
warning_popup.init();

$("#action-take").on('click', function() {
    if (wine.quantity - 1 < 0) {
        warning_popup.open();
        return;
    }
    quantity_cell.set(wine.quantity - 1, wine.quantity - 1);
    quantity_cell.save();
});

$("#action-edit").on("click", function() {
    window.location = "/wine/" + wine.id_wine + "/edit/";
});

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