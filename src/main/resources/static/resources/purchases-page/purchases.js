let header = new Header({
    nav: true,
    title: "Compras",
    subtitle: "Bodega",
});

let fab_new_wine = new FAB("fab-new-wine", {
    scroll_on_overflow: true,
});

fab_new_wine.events.on("click", function () {
    window.location = "/wine/new/";
});

let nav = new Nav("nav-purchases");
nav.events.on("nav-inventory", function () {
    go_inventory();
});

nav.events.on("nav-running-low", function () {
    go_running_low();
});
nav.events.on("nav-settings", function () {
    go_settings();
});

function go_inventory() {
    window.location = "/";
}

function go_settings() {
    window.location = "/settings/";
}

function go_running_low() {
    window.location = "/running-low/";
}

let purchases;
let preferences = {
    notify_on: 5,
};

let inventory_search;

const init = async () => {
    let request = await fetch("/api/v1/extended/purchases");
    if (request.ok) {
        purchases = await request.json();
    }

    inventory_search = new Purchase_Search();
    inventory_search.init();
};

class Purchase_Card {
    constructor(id, wine, params) {
        this.id = id;
        this.selector = `#card-${id}`;

        this.is_hidden = false;

        this.wine = wine;
        let expectedFields = [{ header: ["title"] }, { body: ["count"] }];
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
        let html = `
            <div class="header table space-between">
                <h1 class="title">${this.params.header.title}</h1>
                <h1 class="title">${this.params.body.count}</h1>
            </div>
        `;

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

        $(this.selector).append(html);
        $(this.selector).append(body);
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

class Purchase_Cards {
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

    filter(params) {
        let expected_fields = ["nombre"];
        params = new Params_Parser(expected_fields, params).params;
        let matched_cards = this.cards;

        if (!is_undefined_or_empty(params.nombre)) {
            matched_cards = this.filter_nombre(params.nombre, matched_cards);
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

        let purchaseCard = new Purchase_Card(id, wine, params);
        purchaseCard.create();
        purchaseCard.init();
        this.cards.push(purchaseCard);

        let parent = this;
        purchaseCard.events.on("click", function (purchaseCard) {
            parent.events.trigger("click", purchaseCard);
        });
    }
}

class Purchase_Search {
    constructor() {
        this.search_bar = new Search_Bar("purchase-search");
        this.purchaseCards = null;
    }

    init() {
        this.purchaseCards = new Purchase_Cards("#purchase-cards");
        for (let purchase of purchases) {
            let params = {
                header: {
                    title: purchase.nombre,
                },
                body: {
                    count: purchase.count,
                    price: prettify_decimal(purchase.price, 2),
                    location: purchase.location,
                    quantity: purchase.quantity,
                },
            };
            this.purchaseCards.create_card(
                purchase.purchaseId,
                purchase,
                params
            );
        }

        let parent = this;
        this.search_bar.events.on("change", function () {
            parent.filter(parent);
        });

        this.search_bar.events.on("clear", function () {
            parent.remove_all_filters(parent);
        });

        this.purchaseCards.events.on("click", function (wine_card) {
            let purchaseId = wine_card.id;
            window.location = "/purchases/" + purchaseId + "/";
        });
    }

    filter(parent) {
        parent = parent == undefined ? this : parent;

        let nombre = parent.search_bar.get();
        let filters = {
            nombre: nombre,
        };

        parent.purchaseCards.filter(filters);
    }

    remove_filters(parent) {
        parent = parent == undefined ? this : parent;

        parent.filter_form.clear();
        parent.filter();
    }

    remove_all_filters(parent) {
        parent = parent == undefined ? this : parent;

        parent.search_bar.set("");
        parent.filter();
    }
}

init();
