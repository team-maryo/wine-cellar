let nav = new Nav("nav-settings");
nav.events.on("nav-inventory", function() {
    go_inventory();
});
nav.events.on("nav-running-low", function() {
    go_running_low();
});

function go_inventory() {
    window.location = "/";
}

function go_running_low() {
    window.location = "/running-low/";
}

let header = new Header({
    nav: true,
    title: "Ajustes",
    subtitle: "Bodega - Las Tejuelas",
});

let fab_undo = new FAB("fab-undo", {
    scroll_on_overflow: false
});

let preferences = JSON.parse($("data#data-preferences").html());
let user = JSON.parse($("data#data-user").html());
let origins = JSON.parse($("data#data-origins").html());
let tipos = JSON.parse($("data#data-tipos").html());

const is_staff = user.is_superuser || user.is_staff;
if (!user.is_superuser || !user.is_staff) {
    $("#invite-user-title").remove();
    $("#invite-user").parent().remove();
}

// In case the user wants to undo
let old_preferences = preferences;
let old_user = user;

let preferences_form = new Form("preferences-form", {
    post: {
        on_save:true,
        url: "/set/preferences/",
    },
    states: {
        editable:true,
    }
});
preferences_form.create();

let benefits_margin_number = preferences_form.add({
    id: "benefits_margin",
    label: "Margen de beneficios",
    number: round_num_opt(preferences.benefits_margin),
    paragraph: "Porcentaje que se le añade al precio de adqusición."
});

let notify_checkbox = preferences_form.add({
    id: "notify",
    label: "Notificaciones",
    checkbox: {
        fallback_value: preferences.notify,
        content: {
            true: "Activadas",
            false: "Desactivadas"
        }
    },
    paragraph: "Si están activadas se le enviará un email cada vez que haya pocas existencias."
});

let notify_on_number = preferences_form.add({
    id: "notify_on",
    label: "Umbral de existencias",
    number: preferences.notify_on,
    paragraph: "Cuando las existencias estén por debajo de este número, si las notificaciones están activadas, se le enviará un email."
});

preferences_form.events.on("change", function() {
    let payload = preferences_form.save();
    preferences_form.events.on("post-success", function() {
        preferences = payload;
    });
});

let user_form = new Form("user-form", {
    post: {
        on_save:true,
        url: "/set/user/"
    },
    states: {
        editable:true,
    }
});
user_form.create();

let first_name_input = user_form.add({
    id: "first_name",
    label: "Nombre",
    input: user.first_name
});

let last_name_input = user_form.add({
    id: "last_name",
    label: "Apellidos",
    input: user.last_name,
});

let username_input = user_form.add({
    id: "username",
    label: "Usuario*",
    input: user.username
});

let email_input = user_form.add({
    id: "email",
    label: "Email*",
    input: user.email
});

user_form.events.on("change", function() {
    let payload = user_form.save();
    user_form.events.on("post-success", function() {
        user = payload;
    });
});

let password_one_popup = new Popup("password-one", {
    header: {
        title: "Nueva contraseña",
        subtitle: "Introduzca la nueva contraseña"
    },
    body: {
        password: "",
        paragraph: "La contraseña debe tener como mínimo 8 caracteres y contener una letra."
    },
    actions: [{
        id: "next",
        content: "Siguiente"
    }]
});
password_one_popup.create();
password_one_popup.init();

let password_two_popup = new Popup("password-two", {
    header: {
        title: "Repita la contraseña",
        subtitle: "Introduzca de nuevo la contraseña"
    },
    body: {
        password: "",
    },
    actions: [{
        id: "next",
        content: "Cambiar"
    }]
});
password_two_popup.create();
password_two_popup.init();

$("#action-change-password").on("click", function() {
    password_one_popup.open();
});

let password_one = null;
let password_two = null;
password_one_popup.events.on("next", function() {
    password_one = password_one_popup.get();
    password_one_popup.close();
    password_one_popup.events.on("closed", function() {
        password_one_popup.set("");
        password_two_popup.open();
    });
});

password_two_popup.events.on("next", function() {
    password_two = password_two_popup.get();
    // Verify that they are both the same
    if (password_one == password_two && !is_null_or_empty(password_one)) {
        // Save changes
        $.ajax({
            url: "/set/password/",
            method: "POST",
            dataType: "json",
            data: {
                csrfmiddlewaretoken: csrftoken,
                payload: JSON.stringify({
                    password: password_one
                })
            } ,
            success: function() {
                location.reload();
            }
        });
    } else {
        // Restart process
        password_two_popup.close();
        password_two_popup.set("");
        password_one_popup.open();
    }
});

let origin_form = new Form("origin-form", {
    post: {
        on_save:false,
    }
});
origin_form.create();

let origins_dropdown_items = [];
for (let origin of origins) {
    origins_dropdown_items.push({
        value: origin.id_origin,
        text: origin.nombre
    });
}

let origin_component = origin_form.add({
    id: "id_origin",
    label: "Denominaciones de origen",
    dropdown: {
        items: origins_dropdown_items,
    },
    popup: {
        header: {
            title: "Editar",
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
    popup_action_content: "Editar"
});

let editing_origin = null;
origin_component.manager.events.on("change", function() {
    let id_origin = origin_component.manager.get();
    editing_origin = id_origin;
    let value = origin_component.manager.get_text_from_value(id_origin);
    origin_component.popup.set(value);
});

origin_component.popup.events.on("save", function() {
    let nombre = origin_component.popup.get();
    if (is_null_or_empty(nombre)) return;
    if (editing_origin == null || editing_origin == undefined) {
        add_origin(nombre);
        origin_component.popup.close();
        return;
    }

    $.ajax({
        url: "/set/origin/" + editing_origin + "/",
        method: "POST",
        dataType: "json",
        data: {
            csrfmiddlewaretoken: csrftoken,
            payload: JSON.stringify({
                nombre: nombre
            }),
        },
        success: function(response) {
            location.reload();
        }
    })
});

let add_origin_popup = new Popup("add-origin", {
    header: {
        title: "Añadir",
        subtitle: "Denominación / Origen"
    },
    body: {
        input: "",
    },
    actions: [{
        id: "save",
        content: "Guardar"
    }]
});
add_origin_popup.create();
add_origin_popup.init();

// Add events
$("#action-add-origin").on("click", function() {
    add_origin_popup.open();
});

add_origin_popup.events.on("save", function() {
    let nombre = add_origin_popup.get();
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
                nombre: nombre
            }),
        },
        success: function(response) {
            let origin = response.origin;
            origins.push({
                id_origin: origin.id_origin,
                nombre: origin.nombre
            });
            origin_component.manager.add_item(origin.id_origin, origin.nombre);
            add_origin_popup.close();
            add_origin_popup.set("");
        }
    });
}

/********************
 *      TIPOS
 ********************/
let tipos_form = new Form("tipos-form", {
    post: {
        on_save:false,
    }
});
tipos_form.create();

let tipos_dropdown_items = [];
for (let tipo of tipos) {
    tipos_dropdown_items.push({
        value: tipo.id_tipo,
        text: tipo.nombre
    });
}

let tipo_component = tipos_form.add({
    id: "id_tipo",
    label: "Tipos",
    dropdown: {
        items: tipos_dropdown_items,
    },
    popup: {
        header: {
            title: "Editar",
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
    popup_action_content: "Editar"
});

let editing_tipo = null;
tipo_component.manager.events.on("change", function() {
    let id_tipo = tipo_component.manager.get();
    editing_tipo = id_tipo;
    let value = tipo_component.manager.get_text_from_value(id_tipo);
    tipo_component.popup.set(value);
});

tipo_component.popup.events.on("save", function() {
    let nombre = tipo_component.popup.get();
    if (is_null_or_empty(nombre)) return;
    if (editing_tipo == null || editing_tipo == undefined) {
        add_tipo(nombre);
        tipo_component.popup.close();
        return;
    }

    $.ajax({
        url: "/set/tipo/" + editing_tipo + "/",
        method: "POST",
        dataType: "json",
        data: {
            csrfmiddlewaretoken: csrftoken,
            payload: JSON.stringify({
                nombre: nombre
            }),
        },
        success: function(response) {
            location.reload();
        }
    })
});

let add_tipo_popup = new Popup("add-tipo", {
    header: {
        title: "Añadir",
        subtitle: "Tipo"
    },
    body: {
        input: "",
    },
    actions: [{
        id: "save",
        content: "Guardar"
    }]
});
add_tipo_popup.create();
add_tipo_popup.init();

// Add events
$("#action-add-tipo").on("click", function() {
    add_tipo_popup.open();
});

add_tipo_popup.events.on("save", function() {
    let nombre = add_tipo_popup.get();
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
                nombre: nombre
            }),
        },
        success: function(response) {
            let tipo = response.tipo;
            tipos.push({
                id_tipo: tipo.id_tipo,
                nombre: tipo.nombre
            });
            tipo_component.manager.add_item(tipo.id_tipo, tipo.nombre);
            add_tipo_popup.close();
            add_tipo_popup.set("");
        }
    });
}

function undo_changes() {
    if (old_user != user) {
        username_input.manager.set(old_user.username);
        first_name_input.manager.set(old_user.first_name);
        last_name_input.manager.set(old_user.last_name);
        email_input.manager.set(old_user.email);

        user_form.save();
    }
    
    if (old_preferences != preferences) {
        benefits_margin_number.manager.set(round_num_opt(old_preferences.benefits_margin));
        notify_checkbox.manager.set(old_preferences.notify);
        notify_on_number.manager.set(old_preferences.notify_on);

        preferences_form.save();
    }
}

fab_undo.events.on("click", function() {
    undo_changes();
});

// INVITATIONS
let invitation_popup = new Popup("invitation-popup", {
    header: {
        title: "Email del nuevo usuario",
        subtitle: "Nuevos usuarios"
    },
    body: {
        input: "",
        paragraph: `Introduzca el email del nuevo usuario para que podamos enviarle una invitación`,
    },
    actions: [{
        id: "next",
        content: "Invitar"
    }]
});
invitation_popup.create();
invitation_popup.init();

$("#invite-user").on("click", function(){
    if(!is_staff) return;
    invitation_popup.open();
});

invitation_popup.events.on("next", function() {
    let email = invitation_popup.get();
    if (!is_staff) return;
    $.ajax({
        url: "/auth/invitation/",
        method: "POST",
        dataType: "json",
        data: {
            csrfmiddlewaretoken: csrftoken,
            payload: JSON.stringify({
                email: email
            })
        },
        success: function() {
            invitation_popup.close();
            invitation_popup.set("");
        },
        error: function() {
            invitation_popup.set("");
        }
    });
});

$("#action-logout").on("click", function() {
    window.location = "/auth/logout/";
})

$("#action-print").on("click", function() {
    window.location = "/wine/cart/";
});