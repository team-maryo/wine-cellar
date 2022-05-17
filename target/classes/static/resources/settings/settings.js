let nav = new Nav("nav-settings");
nav.events.on("nav-inventory", function () {
    go_inventory();
});
nav.events.on("nav-running-low", function () {
    go_running_low();
});

function go_inventory() {
    window.location = "/";
}

function go_running_low() {
    window.location = "/running-low/";
}

nav.events.on("nav-purchases", function () {
    go_purchases();
});

function go_purchases() {
    window.location = "/purchases/";
}

let header = new Header({
    nav: true,
    title: "Ajustes",
    subtitle: "Bodega - Las Tejuelas",
});

let fab_undo = new FAB("fab-undo", {
    scroll_on_overflow: false,
});

// let preferences = JSON.parse($("data#data-preferences").html());
let user;
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

    request = await fetch("/api/v1/users");
    if (request.ok) {
        user = await request.json();
    }

    createUserForm();
    createOriginForm();
    createTiposForm();
};

const createUserForm = () => {
    user_form.create();

    user_form.add({
        id: "email",
        label: "Email*",
        input: user.email,
    });
};

let user_form = new Form("user-form", {
    post: {
        on_save: true,
        url: "/api/v1/users/",
        method: "PUT",
    },
    states: {
        editable: true,
    },
});

$("#action-user-save").on("click", function () {
    user_form.save();
});

let origins_dropdown_items = [];
let origin_component;
let editing_origin = null;

const createOriginForm = () => {
    origin_form.create();

    for (let origin of origins) {
        origins_dropdown_items.push({
            value: origin.originId,
            text: origin.nombre,
        });
    }

    origin_component = origin_form.add({
        id: "originId",
        label: "Denominaciones de origen",
        dropdown: {
            items: origins_dropdown_items,
        },
        popup: {
            header: {
                title: "Editar",
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
        popup_action_content: "Editar",
    });

    origin_component.manager.events.on("change", function () {
        let originId = origin_component.manager.get();
        console.log(originId);
        editing_origin = originId;
        let value = origin_component.manager.get_text_from_value(originId);
        origin_component.popup.set(value);
    });

    origin_component.popup.events.on("save", function () {
        let nombre = origin_component.popup.get();
        if (is_null_or_empty(nombre)) return;
        if (editing_origin == null || editing_origin == undefined) {
            add_origin(nombre);
            origin_component.popup.close();
            return;
        }

        $.ajax({
            url: "/api/v1/origins/" + editing_origin + "/",
            method: "PUT",
            dataType: "json",
            contentType: "application/json",
            data: JSON.stringify({
                nombre: nombre,
            }),
            success: function (response) {
                location.reload();
            },
        });
    });
};

let origin_form = new Form("origin-form", {
    post: {
        url: "/api/v1/origins/",
        on_save: false,
    },
});

let add_origin_popup = new Popup("add-origin", {
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
});
add_origin_popup.create();
add_origin_popup.init();

// Add events
$("#action-add-origin").on("click", function () {
    add_origin_popup.open();
});

add_origin_popup.events.on("save", function () {
    let nombre = add_origin_popup.get();
    add_origin(nombre);
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
            let origin = response;
            origins.push({
                originId: origin.originId,
                nombre: origin.nombre,
            });
            origin_component.manager.add_item(origin.originId, origin.nombre);
            add_origin_popup.close();
            add_origin_popup.set("");
        },
    });
}

/********************
 *      TIPOS
 ********************/
let tipos_dropdown_items = [];
let tipo_component;
let editing_tipo = null;

const createTiposForm = () => {
    for (let tipo of tipos) {
        tipos_dropdown_items.push({
            value: tipo.tipoId,
            text: tipo.nombre,
        });
    }

    tipo_component = tipos_form.add({
        id: "tipoId",
        label: "Tipos",
        dropdown: {
            items: tipos_dropdown_items,
        },
        popup: {
            header: {
                title: "Editar",
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
        popup_action_content: "Editar",
    });

    tipo_component.manager.events.on("change", function () {
        let tipoId = tipo_component.manager.get();
        console.log(tipoId);
        editing_tipo = tipoId;
        let value = tipo_component.manager.get_text_from_value(tipoId);
        tipo_component.popup.set(value);
    });

    tipo_component.popup.events.on("save", function () {
        let nombre = tipo_component.popup.get();
        if (is_null_or_empty(nombre)) return;
        if (editing_tipo == null || editing_tipo == undefined) {
            add_tipo(nombre);
            tipo_component.popup.close();
            return;
        }

        $.ajax({
            url: "/api/v1/tipos/" + editing_tipo + "/",
            method: "PUT",
            dataType: "json",
            contentType: "application/json",
            data: JSON.stringify({
                nombre: nombre,
            }),
            success: function (response) {
                location.reload();
            },
        });
    });
};

let tipos_form = new Form("tipos-form", {
    post: {
        on_save: false,
    },
});
tipos_form.create();

let add_tipo_popup = new Popup("add-tipo", {
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
});
add_tipo_popup.create();
add_tipo_popup.init();

// Add events
$("#action-add-tipo").on("click", function () {
    add_tipo_popup.open();
});

add_tipo_popup.events.on("save", function () {
    let nombre = add_tipo_popup.get();
    add_tipo(nombre);
});

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
            let tipo = response;
            tipos.push({
                tipoId: tipo.tipoId,
                nombre: tipo.nombre,
            });
            tipo_component.manager.add_item(tipo.tipoId, tipo.nombre);
            add_tipo_popup.close();
            add_tipo_popup.set("");
        },
    });
}

function undo_changes() {
    if (old_user != user) {
        username_input.manager.set(old_user.username);
        email_input.manager.set(old_user.email);

        user_form.save();
    }
}

fab_undo.events.on("click", function () {
    undo_changes();
});

let password_one_popup = new Popup("password-one", {
    header: {
        title: "Nueva contraseña",
        subtitle: "Introduzca la nueva contraseña",
    },
    body: {
        password: "",
        paragraph:
            "La contraseña debe tener como mínimo 8 caracteres y contener una letra.",
    },
    actions: [
        {
            id: "next",
            content: "Siguiente",
        },
    ],
});
password_one_popup.create();
password_one_popup.init();

let password_two_popup = new Popup("password-two", {
    header: {
        title: "Repita la contraseña",
        subtitle: "Introduzca de nuevo la contraseña",
    },
    body: {
        password: "",
    },
    actions: [
        {
            id: "next",
            content: "Cambiar",
        },
    ],
});
password_two_popup.create();
password_two_popup.init();

$("#action-change-password").on("click", function () {
    password_one_popup.open();
});

let password_one = null;
let password_two = null;
password_one_popup.events.on("next", function () {
    password_one = password_one_popup.get();
    password_one_popup.close();
    password_one_popup.events.on("closed", function () {
        password_one_popup.set("");
        password_two_popup.open();
    });
});

password_two_popup.events.on("next", function () {
    password_two = password_two_popup.get();
    // Verify that they are both the same
    if (password_one == password_two && !is_null_or_empty(password_one)) {
        // Save changes
        $.ajax({
            url: "/api/v1/users/password/",
            method: "PUT",
            dataType: "json",
            contentType: "application/json",
            data: JSON.stringify({
                password: password_one,
            }),
            success: function () {
                location.reload();
            },
        });
    } else {
        // Restart process
        password_two_popup.close();
        password_two_popup.set("");
        password_one_popup.open();
    }
});

const logout = async () => {
    let request = await fetch("/auth/logout", { method: "POST" });
    if (request.ok) {
        location.reload();
    }
};

$("#action-logout").on("click", function () {
    logout();
});

init();
