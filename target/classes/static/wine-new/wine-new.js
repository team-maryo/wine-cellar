let origins;
let tipos;

const init = async () => {
    let request = await fetch("/api/v1/origins");
    if (request.ok) {
        origins = await request.json();
    }

    request = await fetch("/api/v1/tipos");
    if (request.ok) {
        tipos = await request.json();
    }

    createForm();
};

let tipo_component;
let origin_component;

let origins_dropdown_items = [];
let tipos_dropdown_items = [];

const createForm = () => {
    wine_edit_form.add({
        id: "nombre",
        label: "Nombre*",
        input: "",
    });

    wine_edit_form.add({
        id: "quantity",
        label: "Existencias*",
        number: "",
    });

    wine_edit_form.add({
        id: "purchasePrice",
        label: "Precio de adquisición*",
        number: "",
    });

    wine_edit_form.add({
        id: "location",
        label: "Ubicación*",
        input: "",
    });

    for (let origin of origins) {
        origins_dropdown_items.push({
            value: origin.id,
            text: origin.nombre,
        });
    }

    origin_component = wine_edit_form.add({
        id: "origin",
        label: "Denominación de origen",
        isForeignKey: true,
        dropdown: {
            items: origins_dropdown_items,
        },
        popup: {
            header: {
                title: "Añadir",
                subtitle: "Denominación de origen",
            },
            body: {
                input: "",
            },
            actions: [
                {
                    id: "save",
                    content: "Guardar",
                },
            ],
        },
        popup_action_content: "Añadir",
    });

    origin_component.popup.events.on("save", function () {
        let nombre = origin_component.popup.get();
        add_origin(nombre);
    });

    for (let tipo of tipos) {
        tipos_dropdown_items.push({
            value: tipo.id,
            text: tipo.nombre,
        });
    }

    tipo_component = wine_edit_form.add({
        id: "tipo",
        label: "Tipos",
        isForeignKey: true,
        dropdown: {
            items: tipos_dropdown_items,
        },
        popup: {
            header: {
                title: "Añadir",
                subtitle: "Tipo",
            },
            body: {
                input: "",
            },
            actions: [
                {
                    id: "save",
                    content: "Guardar",
                },
            ],
        },
        popup_action_content: "Añadir",
    });

    tipo_component.popup.events.on("save", function () {
        let nombre = tipo_component.popup.get();
        add_tipo(nombre);
    });

    wine_edit_form.events.on("post-success", function () {
        go_to_wine_page();
    });
};

init();

let wine_edit_form = new Form("wine-new", {
    post: {
        on_save: true,
        url: "/api/v1/wines/",
    },
    states: {
        editable: true,
    },
});

function add_origin(nombre) {
    $.ajax({
        url: "/api/v1/origins/",
        method: "POST",
        dataType: "json",
        contentType: "application/json",
        data: JSON.stringify({
            nombre: nombre,
        }),
        success: function (response) {
            // Add new one to dropdown
            let new_origin = response;
            origin_component.manager.add_item(new_origin.id, new_origin.nombre);
            origin_component.manager.set(new_origin.id);
            // Close popup
            origin_component.popup.close();
        },
    });
}

function add_tipo(nombre) {
    $.ajax({
        url: "/api/v1/tipos/",
        method: "POST",
        dataType: "json",
        contentType: "application/json",
        data: JSON.stringify({
            nombre: nombre,
            description: "fuck you",
        }),
        success: function (response) {
            // Add new one to dropdown
            let new_tipo = response;
            tipo_component.manager.add_item(new_tipo.id, new_tipo.nombre);
            tipo_component.manager.set(new_tipo.id);
            // Close popup
            tipo_component.popup.close();
        },
    });
}
// HEADER

let header = new Header({
    nav: true,
    title: "Nuevo vino",
    subtitle: "INVENTARIO",
    back: true,
});

// PAGE ACTIONS
$("#action-close").on("click", function () {
    go_to_wine_page();
});

function go_to_wine_page() {
    window.location = "/";
}

$("#action-save").on("click", function () {
    wine_edit_form.save();
});
