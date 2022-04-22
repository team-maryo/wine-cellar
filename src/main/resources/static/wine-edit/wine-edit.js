let wine = JSON.parse($("data#data-wine").html());
let origins = JSON.parse($("data#data-origins").html());
let tipos = JSON.parse($("data#data-tipos").html());

let wine_edit_form = new Form("wine-edit", {
    post: {
        on_save: true,
        url: "/set/wine/" + wine.id_wine + "/",
    },
    states: {
        editable: true,
    }
});

wine_edit_form.add({
    id: "nombre",
    label: "Nombre*",
    input: wine.nombre,
});

wine_edit_form.add({
    id: "quantity",
    label: "Existencias*",
    number: wine.quantity
});

wine_edit_form.add({
    id: "selling_price",
    label: "PVP Carta*",
    number: round_num_opt(wine.selling_price)
});

wine_edit_form.add({
    id: "purchase_price",
    label: "Precio de adquisición*",
    number: round_num_opt(wine.purchase_price)
});

wine_edit_form.add({
    id: "location",
    label: "Ubicación*",
    input: wine.location
});

let origins_dropdown_items = [];
for (let origin of origins) {
    origins_dropdown_items.push({
        value: origin.id_origin,
        text: origin.nombre
    });
}

let origin_component = wine_edit_form.add({
    id: "id_origin",
    label: "Denominación de origen",
    dropdown: {
        items: origins_dropdown_items,
        fallback: {
            value: wine.origin.id_origin,
            text: wine.origin.nombre
        }
    },
    popup: {
        header: {
            title: "Añadir",
            subtitle: "Denominación / Origen"
        },
        body: {
            input: ""
        },
        actions: [{
            id: "save",
            content: "Guardar"
        }]
    },
    popup_action_content: "Añadir"
});

origin_component.popup.events.on("save", function() {
    let nombre = origin_component.popup.get();
    add_origin(nombre);
});

function add_origin(nombre) {
    $.ajax({
        url: "/new/origin/",
        method: "POST",
        dataType: "json",
        data: {
            csrfmiddlewaretoken: csrftoken,
            payload: JSON.stringify({
                nombre: nombre,
            })
        },
        success: function(response) {
            // Add new one to dropdown
            let new_origin = response.origin;
            origin_component.manager.add_item(new_origin.id_origin, new_origin.nombre);
            origin_component.manager.set(new_origin.id_origin);
            // Close popup
            origin_component.popup.close();
        }
    });
} 

let tipos_dropdown_items = [];
for (let tipo of tipos) {
    tipos_dropdown_items.push({
        value: tipo.id_tipo,
        text: tipo.nombre
    });
}

let tipo_component = wine_edit_form.add({
    id: "id_tipo",
    label: "Tipos",
    dropdown: {
        items: tipos_dropdown_items,
        fallback: {
            value: wine.tipo.id_tipo,
            text: wine.tipo.nombre
        }
    },
    popup: {
        header: {
            title: "Añadir",
            subtitle: "Tipo"
        },
        body: {
            input: ""
        },
        actions: [{
            id: "save",
            content: "Guardar"
        }]
    },
    popup_action_content: "Añadir"
});

tipo_component.popup.events.on("save", function() {
    let nombre = tipo_component.popup.get();
    add_tipo(nombre);
});

function add_tipo(nombre) {
    $.ajax({
        url: "/new/tipo/",
        method: "POST",
        dataType: "json",
        data: {
            csrfmiddlewaretoken: csrftoken,
            payload: JSON.stringify({
                nombre: nombre,
            })
        },
        success: function(response) {
            // Add new one to dropdown
            let new_tipo = response.tipo;
            tipo_component.manager.add_item(new_tipo.id_tipo, new_tipo.nombre);
            tipo_component.manager.set(new_tipo.id_tipo);
            // Close popup
            tipo_component.popup.close();
        }
    });
} 

wine_edit_form.add({
    id: "lavinia_code",
    label: "Código Lavinia",
    input: wine.lavinia_code
});

wine_edit_form.events.on("post-success", function() {
    go_to_wine_page();
});

// HEADER

let header = new Header({
    nav: true,
    title: "Editar vino",
    subtitle: wine.nombre,
    back: true
});

// PAGE ACTIONS
$('#action-close').on('click', function() {
    go_to_wine_page();
});

function go_to_wine_page() {
    window.location = "/wine/" + wine.id_wine + "/";
}

$('#action-save').on("click", function() {
    wine_edit_form.save();
});