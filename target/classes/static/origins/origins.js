let header = new Header({
    nav: true,
    title: "Denominaciones",
    subtitle: "Bodega - Las Tejuelas",
});

let fab_new_origin = new FAB("fab-new-origin", {
    scroll_on_overflow: true
});

let nav = new Nav("nav-origins");
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

class Origin_Card {
    constructor(id, params) {
        this.id = id;
        this.selector = `#${id}`;

        this.is_hidden = false;

        let expectedFields = ["title"];
        this.params = new Params_Parser(expectedFields, params).params;

        this.events = new Events();
    }

    init() {
        let parent = this;
        $(this.selector).on("click", function() {
            parent.events.trigger("click", parent);
        });

        $(this.selector + " > .body > .button").on("click", function(event) {
            event.stopPropagation();
            parent.events.trigger("delete", parent);
        });

        rippleize.cards(this.selector);
    }

    delete() {
        let parent = this;
        $.ajax({
            url: "/delete/origin/" + this.id + "/",
            method: "POST",
            dataType: "json",
            data: {
                csrfmiddlewaretoken: csrftoken,
            },
            success: function() {
                $(parent.selector).remove();
            },
            error: function() {
                console.log("There's been an error");
            }
        });
    }

    // A program will create a div.class with and id, create a card
    // inside said div
    create() {
        let header = `<div class="header">`;
        if (this.params.title != undefined) {
            header += `<h1 class="title">${this.params.title}</h1>`;
        }
        header += `</div>`;

        let body = `
            <div class="body">
                <div class="button">
                    <p>Eliminar</p>
                </div>
            </div>`;

        $(this.selector).append(header);
        $(this.selector).append(body);
    }

    set(title) {
        this.params.title = title;
        $(this.selector + " h1.title").html(title);
    }

    hide() {
        // Change
        $(this.selector).css("display", "none");
        this.is_hidden = true;
    }

    show() {
        $(this.selector).css("display", "flex");
        this.is_hidden = false;
    }
}

class Origin_Cards {
    constructor(container_selector) {
        this.container_selector = container_selector;

        this.cards = [];
        this.events = new Events();
    }

    get_by_id(id) {
        for (let card of this.cards) {
            if (card.id == id) {
                return card;
            }
        }

        return null;
    }

    search_title(search_text) {
        for (let card of this.cards) {
            let title = card.params.title;
            let is_included = title.toLowerCase().includes(search_text.toLowerCase());
            if (!is_included) {
                card.hide();
            } else {
                card.show();
            }
        }
    }

    create_card(id, params) {
        let card_container = `
            <div class="card action" id="${id}"></div>
        `;
        $(this.container_selector).append(card_container);

        let origin_card = new Origin_Card(id, params);
        origin_card.create();
        origin_card.init();
        this.cards.push(origin_card);

        let parent = this;
        origin_card.events.on("click", function(origin_card) {
            parent.events.trigger("click", origin_card);
        });

        origin_card.events.on("delete", function(origin_card) {
            parent.delete(origin_card, parent);
        });
    }

    delete(origin_card, parent) {
        parent = parent == undefined ? this : parent;
        remove(origin_card, parent.cards);
        origin_card.delete();
    }
}

class Origin_Search {
    constructor() {
        this.search_bar = new Search_Bar("origin_search");
        
        this.origins = JSON.parse($("data").html());
        this.origin_cards = null;
    }

    init() {
        this.origin_cards = new Origin_Cards("#origin-cards");
        for (let origin of this.origins) {
            let params = {
                title: origin.nombre
            }
            this.origin_cards.create_card(origin.id_origin, params);
        }

        let parent = this;
        this.search_bar.events.on("change", function() {
            let search_text = parent.search_bar.get();
            parent.search(search_text, parent);
        });

        this.origin_cards.events.on("click", function(origin_card) {
            // Do something
        });
    }

    search(search_text, parent) {
        parent = parent == undefined ? this : parent;
        parent.origin_cards.search_title(search_text);
    }
}

let origin_search = new Origin_Search();
origin_search.init();

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
fab_new_origin.events.on("click", function() {
    add_origin_popup.open();
});

add_origin_popup.events.on("save", function() {
    let nombre = add_origin_popup.get();
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
            origin_search.origins.push({
                id_origin: origin.id_origin,
                nombre: origin.nombre
            });

            let params = {
                title: origin.nombre
            }
            origin_search.origin_cards.create_card(origin.id_origin, params);
            add_origin_popup.close();
            add_origin_popup.set("");
        }
    });
});

let edit_origin_popup = new Popup("edit-origin", {
    header: {
        title: "Editar",
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
edit_origin_popup.create();
edit_origin_popup.init();

let editing_origin = null;
origin_search.origin_cards.events.on("click", function(origin_card) {
    editing_origin = origin_card;
    edit_origin_popup.set(origin_card.params.title);
    edit_origin_popup.open();
});

edit_origin_popup.events.on("save", function() {
    let nombre = edit_origin_popup.get();
    $.ajax({
        url: "/set/origin/" + editing_origin.id + "/",
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
            editing_origin.set(origin.nombre);
            editing_origin = null;
            edit_origin_popup.set("");
            edit_origin_popup.close();
        }
    })
});

