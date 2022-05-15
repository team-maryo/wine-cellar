class Popup {
    constructor(id, params) {
        this.id = id;
        this.selector = `#${id}`;

        let expected_fields = [
            { header: ["title", "subtitle"] },
            { body: ["input", "number", "paragraph", "dropdown"] },
            "actions",
        ];
        this.params = new Params_Parser(expected_fields, params).params;
        this.input = false;
        this.dropdown = null;
        this.events = new Events();
    }

    init() {
        // Events
        let parent = this;
        $(this.selector + " .background").on("click", function () {
            parent.close(parent);
        });

        $(this.selector + " .body .footer #" + this.id + "_close").on(
            "click",
            function () {
                parent.close(parent);
            }
        );

        let actions = this.params.actions;
        if (actions != undefined) {
            for (let action of actions) {
                $(
                    this.selector +
                        " > .body .footer #" +
                        this.id +
                        "_" +
                        action.id
                ).on("click", function () {
                    parent.events.trigger(action.id);
                });
            }
        }

        rippleize.buttons(this.selector + " > .body .footer .button");
        rippleize.element(this.selector + " > .background");
    }

    // Change for animations
    close(parent) {
        parent = parent == undefined ? this : parent;
        if (!$(parent.selector).hasClass("hidden")) {
            $("body").css("overflow", "inherit");
            anime({
                targets: this.selector + " > .body",
                bottom: ["0", "-100%"],
                duration: 250,
                easing: "cubicBezier(.5, .05, .1, .3)",
                complete: function () {
                    $(parent.selector).addClass("hidden");
                    parent.events.trigger("closed");
                },
            });
        }

        parent.events.trigger("close");
    }

    open() {
        let parent = this;
        if ($(this.selector).hasClass("hidden")) {
            $("body").css("overflow", "hidden");
            $(this.selector).removeClass("hidden");
            anime({
                targets: this.selector + " > .body",
                bottom: ["-100%", "0"],
                duration: 250,
                easing: "cubicBezier(.5, .05, .1, .3)",
                complete: function () {
                    parent.events.trigger("opened");
                },
            });
        }
    }

    get() {
        if (this.input) {
            let val = this.input.get();
            return val;
        } else if (this.dropdown) {
            let val = this.dropdown.get();
            return val;
        }
        return null;
    }

    set(value) {
        if (this.input) {
            this.input.set(value);
        } else if (this.dropdown) {
            this.dropdown.set(value);
        } else if (this.params.paragraph != undefined) {
            $(this.selector + " .body > .body > p").html(value);
        }
    }

    set_title(value) {
        if (this.params.header.title != undefined) {
            $(this.selector + " .body > .header > h1").html(value);
        }
    }

    set_subtitle(value) {
        if (this.params.header.title != undefined) {
            $(this.selector + " .body > .header > h3").html(value);
        }
    }

    create() {
        let popup = `
            <div class="popup hidden" id="${this.id}">
                <div class="background"></div>
                <div class="body"></div>
            </div>
        `;
        $("body").append(popup);

        let header = this.params.header;
        if (header != undefined) {
            $(this.selector + " .body").append(`
                <div class="header"></div>
            `);
            if (header.title != undefined) {
                $(this.selector + " .body .header").append(`
                    <h1 class="title">${header.title}</h1>
                `);
            }
            if (header.subtitle != undefined) {
                $(this.selector + " .body .header").append(`
                    <h3 class="subtitle">${header.subtitle}</h3>
                `);
            }
        }

        let body = this.params.body;
        if (body != undefined) {
            $(this.selector + " .body").append(`
                <div class="body"></div>
            `);
            if (body.input != undefined) {
                $(this.selector + " .body .body").append(`
                    <input type="text" value="${body.input}" id="${this.id}-input">
                `);
                this.input = new Textbox(this.id + "-input", {
                    fallback_value: body.input,
                });
            }
            if (body.number != undefined) {
                $(this.selector + " .body .body").append(`
                    <input type="number" value="${body.number}" step="any" id="${this.id}-input">
                `);
                this.input = new Textbox(this.id + "-input", {
                    fallback_value: body.number,
                });
            }
            if (body.password != undefined) {
                $(this.selector + " .body .body").append(`
                    <input type="password" autocomplete="false" value="${body.password}" id="${this.id}-input">
                `);
                this.input = new Textbox(this.id + "-input", {
                    fallback_value: body.password,
                });
            }
            if (body.dropdown != undefined && !body.input) {
                $(this.selector + " .body .body").append(`
                    <div class="dpdown" id="${this.id}-dpdown"></div>
                `);
                this.dropdown.create = true;
                this.dropdown = new Dropdown(this.id + "-dpdown", body.dpdown);
            }
            if (body.paragraph != undefined) {
                $(this.selector + " .body .body").append(`
                    <p>${body.paragraph}</p>
                `);
            }
        }

        $(this.selector + " > .body").append(`
            <div class="footer">
                <div class="button raised">
                    <div><p id="${this.id}_close">Cerrar</p></div>
                </div>
            </div>
        `);

        let actions = this.params.actions;
        if (actions != undefined) {
            for (let action of actions) {
                let action_id = this.id + "_" + action.id;
                let content = action.content;

                $(this.selector + " .body .footer .button.raised").append(`
                    <div><p id="${action_id}">${content}</p></div>
                `);
            }
        }
    }
}

class Search_Bar {
    constructor(id) {
        this.id = id;
        this.selector = `#${this.id}`;

        this.events = new Events();

        this.init();
    }

    init() {
        rippleize.element(this.selector + " .clear");
        rippleize.element(this.selector + " .filters");
        let parent = this;

        $(this.selector + " .clear").on("click", function () {
            parent.clear(parent);
        });

        $(this.selector + " .filters").on("click", function () {
            parent.filters(parent);
        });

        $(this.selector + " .textbox input").on("keyup", function () {
            parent.events.trigger("change");
        });

        $(this.selector + " .textbox input").on("change", function () {
            parent.events.trigger("change");
        });
    }

    get() {
        let val = $(this.selector + " .textbox input").val();
        return val;
    }

    set(text) {
        $(this.selector + " .textbox input").val(text);
    }

    filters(parent) {
        parent = parent == undefined ? this : parent;
        parent.events.trigger("filters");
    }

    clear(parent) {
        parent = parent == undefined ? this : parent;
        parent.events.trigger("clear");
    }
}

class Form {
    constructor(id, params) {
        this.id = id;
        this.selector = `#${this.id}`;

        let expected_fields = [
            { post: ["on_save", "url"] },
            { states: ["editable", "readonly"] },
        ];
        this.params = new Params_Parser(expected_fields, params).params;

        this.events = new Events();

        this.components = [];
    }

    create() {
        if (!$(this.selector).hasClass("form")) {
            $(this.selector).addClass("form");
        }
    }

    save() {
        let payload = {};
        for (let component of this.components) {
            let key = component.id;
            let value = component.manager.get();

            if (component.isForeignKey == true) {
                payload[key] = {
                    id: value,
                };
            } else {
                payload[key] = value;
            }
        }

        console.log(payload);

        if (this.params.post.on_save) {
            this.post(payload);
        }

        return payload;
    }

    post(payload) {
        let parent = this;
        $.ajax({
            url: this.params.post.url,
            method: this.params.post.method ? this.params.post.method : "POST",
            dataType: "json",
            contentType: "application/json",
            data: JSON.stringify(payload),
            success: function (response) {
                parent.events.trigger("post-success", response);
            },
            error: function (response) {
                parent.events.trigger("post-error", response);
            },
        });
    }

    create_base(component) {
        let base = `
            <div class="component" id="${component.id}"></div>
        `;
        $(this.selector).append(base);
        component.selector = `#${component.id}`;

        if (component.label) {
            let label = `
                <div class="header">
                    <h2 class="title">${component.label}</h2>
                </div>
            `;
            $(component.selector).append(label);
        }
    }

    create_input(component) {
        let input_html = `
            <div class="body">
                <div class="textbox child">
                    <input type="text" id="${component.id}-input">
                <div>
            </div>
        `;
        $(component.selector).append(input_html);

        // Create class
        let input = new Textbox(component.id + "-input", {
            fallback_value: component.input,
        });
        // Save
        component.manager = input;
        return component;
    }

    create_password(component) {
        let password_html = `
            <div class="body">
                <div class="textbox child">
                    <input type="password" id="${component.id}-input">
                <div>
            </div>
        `;
        $(component.selector).append(password_html);

        // Create class
        let password = new Textbox(component.id + "-input", {
            fallback_value: component.password,
        });
        // Save
        component.manager = password;
        return component;
    }

    create_number(component) {
        let number_html = `
            <div class="body">
                <div class="textbox child">
                    <input type="number" step="any" id="${component.id}-input">
                <div>
            </div>
        `;
        $(component.selector).append(number_html);

        // Create class
        let number = new Textbox(component.id + "-input", {
            fallback_value: component.number,
        });
        // Save
        component.manager = number;
        return component;
    }

    create_dropdown(component) {
        let dropdown_html = `
            <div class="body">
                <div id="${component.id}-dropdown" class="dpdown child"></div>
            </div>
        `;
        $(component.selector).append(dropdown_html);

        component.dropdown.create = true; // Make sure it generate necessary DOM elements
        let dropdown = new Dropdown(
            component.id + "-dropdown",
            component.dropdown
        );

        // Save
        component.manager = dropdown;
        return component;
    }

    create_checkbox(component) {
        let checkbox_html = `
            <div class="body">
                <div id="${component.id}-checkbox" class="checkbox child"></div>
            </div>
        `;
        $(component.selector).append(checkbox_html);

        let checkbox = new Checkbox(
            component.id + "-checkbox",
            component.checkbox
        );

        // Save
        component.manager = checkbox;
        return component;
    }

    create_paragraph(component) {
        let paragraph_html = `<p>${component.paragraph}</p>`;
        $(component.selector + " > .body").append(paragraph_html);
    }

    create_popup(component) {
        let popup_action = `
        <div class="button" id="${component.id}-popup-action">
            <p>${component.popup_action_content}</p>
        </div>`;

        let popup = new Popup(component.id + "-popup", component.popup);
        popup.create();
        popup.init();
        component.popup = popup;

        $(component.selector + " > .body").append(popup_action);
        $(component.selector + "-popup-action").on("click", function () {
            popup.open();
        });

        return component;
    }

    add(component) {
        let expected_fields = [
            "id",
            "label",
            "dropdown",
            "input",
            "password",
            "number",
            "checkbox",
            "paragraph",
            "isForeignKey",
            "popup",
            "popup_action_content",
        ];
        component = new Params_Parser(expected_fields, component).params;

        if (component.id == undefined) return;

        this.create_base(component);

        // Methods to modify information
        if (component.input != undefined) {
            component = this.create_input(component);
        } else if (component.number != undefined) {
            component = this.create_number(component);
        } else if (component.dropdown != undefined) {
            component = this.create_dropdown(component);
        } else if (component.checkbox != undefined) {
            component = this.create_checkbox(component);
        } else if (component.password != undefined) {
            component = this.create_password(component);
        }

        // Paragraph
        if (component.paragraph) {
            this.create_paragraph(component);
        }

        // Popup
        if (component.popup && component.popup_action_content) {
            component = this.create_popup(component);
        }

        // Events
        let parent = this;
        if (component.manager != undefined) {
            component.manager.events.on("change", function (component) {
                parent.events.trigger("change", component);
            });
        }

        this.components.push(component);
        return component;
    }

    clear() {
        for (let component of this.components) {
            component.manager.clear();
        }
    }
}

class Wine_Card {
    constructor(id, wine, params) {
        this.id = id;
        this.selector = `#card-${id}`;

        this.is_hidden = false;

        this.wine = wine;
        let expectedFields = [
            { header: ["title", "subtitle"] },
            { body: ["price", "location", "quantity"] },
        ];
        this.params = new Params_Parser(expectedFields, params).params;

        this.events = new Events();
    }

    init() {
        let parent = this;
        $(this.selector).on("click", function () {
            parent.events.trigger("click", parent);
        });
    }

    // A program will create a div.class with and id, create a card
    // inside said div
    create() {
        let header = `<div class="header">`;
        if (this.params.header.title != undefined) {
            header += `<h2 class="title">${this.params.header.title}</h2>`;
        }
        if (this.params.header.subtitle != undefined) {
            header += `<h3 class="subtitle">${this.params.header.subtitle}</h3>`;
        }
        header += `</div>`;

        let body = `<div class="body table">`;
        if (this.params.body.price != undefined) {
            body += `
            <div class="cell">
                <div class="table">
                    <div class="cell"><span class="icon">local_offer</span></div>
                    <div class="cell"><p>${round_num_opt(
                        this.params.body.price
                    )} &euro;</p></div>
                </div>
            </div>
            `;
        }
        if (
            this.params.body.location != undefined &&
            this.params.body.location != ""
        ) {
            body += `
            <div class="cell">
                <div class="table">
                    <div class="cell"><span class="icon">place</span></div>
                    <div class="cell"><p>${this.params.body.location}</p></div>
                </div>
            </div>
            `;
        }
        if (this.params.body.quantity != undefined) {
            body += `
            <div class="cell">
                <div class="table quantity">
                    <div class="cell"><span class="icon">assessment</span></div>
                    <div class="cell"><p>${this.params.body.quantity}</p></div>
                </div>
            </div>
            `;
        }

        body += `</div>`;

        $(this.selector).append(header);
        $(this.selector).append(body);

        if (preferences.notify_on >= this.wine.quantity) {
            $(this.selector + "> .body .quantity").addClass("low");
        }
    }

    hide() {
        // Change
        let parent = this;
        if (!$(parent.selector).hasClass("hidden")) {
            $(parent.selector).addClass("hidden");
        }
        this.is_hidden = true;
    }

    show(animate = true) {
        let parent = this;
        if ($(this.selector).hasClass("hidden")) {
            $(this.selector).removeClass("hidden");
        }

        if (animate) {
            anime({
                targets: parent.selector,
                scale: [0.5, 1],
                duration: 400,
            });
        }

        this.is_hidden = false;
    }
}

class Wine_Cards {
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

    show() {
        for (let card of this.cards) {
            if (card.is_hidden) {
                card.show();
            }
        }
    }

    filter_nombre(nombre, cards) {
        let matched_cards = [];
        for (let card of cards) {
            let title = card.params.header.title;
            let search_space = this.challenge(
                unidecode(nombre),
                unidecode(title)
            );

            if (search_space.length == 0) {
                matched_cards.push(card);
            }
        }

        return matched_cards;
    }

    challenge(search_space, challenge) {
        if (is_null_or_empty(search_space)) {
            return "";
        }
        if (is_null_or_empty(challenge)) {
            return search_space;
        }
        search_space = search_space.split(" ");
        let output = search_space.slice();
        challenge = challenge.toString();

        for (let keyword of search_space) {
            if (challenge.includes(keyword)) {
                remove(keyword, output);
            }
        }

        return join_to_string(" ", output, true);
    }

    filter_min_price(min_price, cards) {
        let matched_cards = [];
        for (let card of cards) {
            if (card.wine.price >= min_price) {
                matched_cards.push(card);
            }
        }
        return matched_cards;
    }

    filter_max_price(max_price, cards) {
        let matched_cards = [];
        for (let card of cards) {
            if (card.wine.price <= max_price) {
                matched_cards.push(card);
            }
        }
        return matched_cards;
    }

    filter_min_quanity(min_quantity, cards) {
        let matched_cards = [];
        for (let card of cards) {
            if (card.wine.quantity >= min_quantity) {
                matched_cards.push(card);
            }
        }
        return matched_cards;
    }

    filter_max_quantity(max_quantity, cards) {
        let matched_cards = [];
        for (let card of cards) {
            if (card.wine.quantity <= max_quantity) {
                matched_cards.push(card);
            }
        }
        return matched_cards;
    }

    filter_origin(origin, cards) {
        let matched_cards = [];
        for (let card of cards) {
            if (card.wine.origin.originId == origin) {
                matched_cards.push(card);
            }
        }
        return matched_cards;
    }

    filter_tipo(tipo, cards) {
        let matched_cards = [];
        for (let card of cards) {
            if (card.wine.tipo.tipoId == tipo) {
                matched_cards.push(card);
            }
        }
        return matched_cards;
    }

    filter_location(location, cards) {
        let matched_cards = [];
        for (let card of cards) {
            if (is_null_or_empty(card.wine.location)) continue;
            if (unidecode(card.wine.location) == unidecode(location)) {
                matched_cards.push(card);
            }
        }
        return matched_cards;
    }

    filter(params) {
        let expected_fields = [
            "nombre",
            "min_price",
            "max_price",
            "origin",
            "tipo",
            "location",
            "min_quantity",
            "max_quantity",
        ];
        params = new Params_Parser(expected_fields, params).params;
        let matched_cards = this.cards;

        if (!is_undefined_or_empty(params.nombre)) {
            matched_cards = this.filter_nombre(params.nombre, matched_cards);
        }
        if (!is_undefined_or_empty(params.min_price)) {
            matched_cards = this.filter_min_price(
                params.min_price,
                matched_cards
            );
        }
        if (!is_undefined_or_empty(params.max_price)) {
            matched_cards = this.filter_max_price(
                params.max_price,
                matched_cards
            );
        }
        if (!is_null_or_empty(params.origin)) {
            matched_cards = this.filter_origin(params.origin, matched_cards);
        }
        if (!is_null_or_empty(params.tipo)) {
            matched_cards = this.filter_tipo(params.tipo, matched_cards);
        }
        if (!is_undefined_or_empty(params.location)) {
            matched_cards = this.filter_location(
                params.location,
                matched_cards
            );
        }
        if (!is_undefined_or_empty(params.min_quantity)) {
            matched_cards = this.filter_min_quanity(
                params.min_quantity,
                matched_cards
            );
        }
        if (!is_undefined_or_empty(params.max_quantity)) {
            matched_cards = this.filter_max_quantity(
                params.max_quantity,
                matched_cards
            );
        }

        for (let card of this.cards) {
            if (matched_cards.includes(card)) {
                if (card.is_hidden) {
                    card.show();
                }
            } else {
                if (!card.is_hidden) {
                    card.hide();
                }
            }
        }
    }

    create_card(id, wine, params) {
        let card_container = `
            <div class="card" id="card-${id}"></div>
        `;
        $(this.container_selector).append(card_container);

        let wine_card = new Wine_Card(id, wine, params);
        wine_card.create();
        wine_card.init();
        this.cards.push(wine_card);

        let parent = this;
        wine_card.events.on("click", function (wine_card) {
            parent.events.trigger("click", wine_card);
        });
    }
}

class Inventory_Search {
    constructor() {
        this.search_bar = new Search_Bar("inventory-search");
        this.overlay = new Overlay("inventory-filters-overlay");
        this.filter_form = new Form("inventory-filters-form");
        this.wine_cards = null;

        this.create();
    }

    create() {
        this.filter_form.add({
            id: "min_price",
            label: "Precio mínimo",
            number: "",
        });

        this.filter_form.add({
            id: "max_price",
            label: "Precio máximo",
            number: "",
        });

        let origins_dropdown_items = [];
        for (let origin of origins) {
            origins_dropdown_items.push({
                value: origin.originId,
                text: origin.nombre,
            });
        }

        this.filter_form.add({
            id: "origin",
            label: "Denominacion de origen",
            dropdown: {
                items: origins_dropdown_items,
            },
        });

        let tipos_dropdown_items = [];
        for (let tipo of tipos) {
            tipos_dropdown_items.push({
                value: tipo.tipoId,
                text: tipo.nombre,
            });
        }

        this.filter_form.add({
            id: "tipo",
            label: "Tipo",
            dropdown: {
                items: tipos_dropdown_items,
            },
        });

        this.filter_form.add({
            id: "location",
            label: "Ubicación",
            input: "",
        });

        this.filter_form.add({
            id: "min_quantity",
            label: "Número mínimo de botellas",
            number: "",
            paragraph:
                "Se mostrarán botellas cuyo número de exitencias se encuentre por encima o sea igual al número introducido.",
        });

        this.filter_form.add({
            id: "max_quantity",
            label: "Número máximo de botellas",
            number: "",
            paragraph:
                "Se mostrarán botellas cuyo número de exitencias se encuentre por debajo o sea igal al número introducido.",
        });

        this.filter_form.create();
    }

    init() {
        this.wine_cards = new Wine_Cards("#wine-cards");
        for (let wine of wines) {
            let subtitle = wine.origin.nombre + " · " + wine.tipo.nombre;

            let params = {
                header: {
                    title: wine.nombre,
                    subtitle: subtitle,
                },
                body: {
                    price: prettify_decimal(wine.price, 2),
                    location: wine.location,
                    quantity: wine.quantity,
                },
            };
            this.wine_cards.create_card(wine.wineId, wine, params);
        }

        let parent = this;
        this.search_bar.events.on("change", function () {
            parent.filter(parent);
        });

        this.search_bar.events.on("clear", function () {
            parent.remove_all_filters(parent);
        });

        this.search_bar.events.on("filters", function () {
            parent.open_filters(parent);
        });

        this.wine_cards.events.on("click", function (wine_card) {
            let id_wine = wine_card.id;
            window.location = "/wine/" + id_wine + "/";
        });

        $("#overlay-action-remove-filters").on("click", function () {
            parent.remove_filters(parent);
            parent.close_filters(parent);
        });

        $("#overlay-action-set-filters").on("click", function () {
            parent.filter(parent);
            parent.close_filters(parent);
        });
    }

    open_filters(parent) {
        parent = parent == undefined ? this : parent;
        parent.overlay.show();
    }

    close_filters(parent) {
        parent = parent == undefined ? this : parent;
        parent.overlay.hide();
    }

    filter(parent) {
        parent = parent == undefined ? this : parent;

        let nombre = parent.search_bar.get();
        let filters = parent.filter_form.save();
        filters.nombre = nombre;

        parent.wine_cards.filter(filters);
    }

    filter_low_quantity() {
        this.wine_cards.filter({
            max_quantity: preferences.notify_on,
        });
    }

    remove_filters(parent) {
        parent = parent == undefined ? this : parent;

        parent.filter_form.clear();
        parent.filter();
    }

    remove_all_filters(parent) {
        parent = parent == undefined ? this : parent;

        parent.search_bar.set("");
        parent.filter_form.clear();
        parent.filter();
    }
}
