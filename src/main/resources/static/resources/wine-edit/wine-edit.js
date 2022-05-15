let wine;
let origins;
let tipos;

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

    request = await fetch("/api/v1/origins");
    if (request.ok) {
        origins = await request.json();
    }

    request = await fetch("/api/v1/tipos");
    if (request.ok) {
        tipos = await request.json();
    }
};

let wine_edit_form;
const createForm = () => {
    wine_edit_form = new Form("wine-edit", {
        post: {
            on_save: true,
            method: "PUT",
            url: "/api/v1/wines/" + wine.wineId + "/",
        },
        states: {
            editable: true,
        },
    });

    wine_edit_form.events.on("post-success", function () {
        go_to_wine_page();
    });
};

const createFormFields = () => {
    wine_edit_form.add({
        id: "nombre",
        label: "Nombre*",
        input: wine.nombre,
    });

    wine_edit_form.add({
        id: "quantity",
        label: "Existencias*",
        number: wine.quantity,
    });

    wine_edit_form.add({
        id: "price",
        label: "Precio de adquisición*",
        number: round_num_opt(wine.price),
    });

    wine_edit_form.add({
        id: "location",
        label: "Ubicación*",
        input: wine.location,
    });

    wine_edit_form.add({
        id: "year",
        label: "Año*",
        input: wine.year,
    });

    wine_edit_form.add({
        id: "rating",
        label: "Rating*",
        input: wine.rating,
    });
};

let origins_dropdown_items = [];
let origin_component;
const createOriginFormField = () => {
    for (let origin of origins) {
        origins_dropdown_items.push({
            value: origin.originId,
            text: origin.nombre,
        });
    }

    origin_component = wine_edit_form.add({
        id: "originId",
        label: "Denominación de origen",
        dropdown: {
            items: origins_dropdown_items,
            fallback: {
                value: wine.origin.originId,
                text: wine.origin.nombre,
            },
        },
        popup: {
            header: {
                title: "Añadir",
                subtitle: "Denominación / Origen",
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
};

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
            origin_component.manager.add_item(
                new_origin.originId,
                new_origin.nombre
            );
            origin_component.manager.set(new_origin.originId);
            // Close popup
            origin_component.popup.close();
        },
    });
}

let tipos_dropdown_items = [];
let tipo_component;
const createTiposFormField = () => {
    for (let tipo of tipos) {
        tipos_dropdown_items.push({
            value: tipo.tipoId,
            text: tipo.nombre,
        });
    }

    tipo_component = wine_edit_form.add({
        id: "tipoId",
        label: "Tipos",
        // isForeignKey: true,
        dropdown: {
            items: tipos_dropdown_items,
            fallback: {
                value: wine.tipo.tipoId,
                text: wine.tipo.nombre,
            },
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
};

function add_tipo(nombre) {
    $.ajax({
        url: "/api/v1/tipos/",
        method: "POST",
        dataType: "json",
        contentType: "application/json",
        data: JSON.stringify({
            nombre: nombre,
        }),
        success: function (response) {
            // Add new one to dropdown
            let new_tipo = response;
            tipo_component.manager.add_item(new_tipo.tipoId, new_tipo.nombre);
            tipo_component.manager.set(new_tipo.tipoId);
            // Close popup
            tipo_component.popup.close();
        },
    });
}

let header;
const createHeader = () => {
    // HEADER
    header = new Header({
        nav: true,
        title: "Editar vino",
        subtitle: wine.nombre,
        back: true,
    });
};

const init = async () => {
    await getData();
    createForm();
    createFormFields();
    createOriginFormField();
    createTiposFormField();
    createHeader();
};

init();

// PAGE ACTIONS
$("#action-close").on("click", function () {
    go_to_wine_page();
});

function go_to_wine_page() {
    window.location = "/wine/" + wine.wineId + "/";
}

$("#action-save").on("click", function () {
    wine_edit_form.save();
});
