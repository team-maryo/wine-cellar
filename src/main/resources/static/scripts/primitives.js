class Dropdown {
    constructor(id, params)
    {
        // DOM Selector
        this.id = id;
        this.selector = "#" + id;

        // Params
        let expected_fields = [{"fallback": ["value", "text"]}, "items", "create"];
        this.params = new Params_Parser(expected_fields, params).params;

        // Data
        this.selected_item = null;
        this.items = [];

        // Events
        this.events = new Events();

        if (this.params.create == true) {
            this.create();
        } 

        this.init();
    }

    create() {
        let fallback_text = (this.params.fallback.value == undefined) ? "" : this.params.fallback.text;
        let innner_html = `
            <div class="ui fluid search selection dropdown" id="${this.id}-dropdown">
                <input type="hidden" name="${this.id}-textbox">
                <i class="dropdown icon"></i>
                <div class="default text">${fallback_text}</div>
                <div class="menu"></div> 
            </div>
        `;

        if (this.params.fallback.value != undefined) {
            this.selected_item = this.params.fallback.value;
        }

        $(this.selector).append(innner_html);

        if (this.params.items != undefined) {
            this.add_items(this.params.items);
        }
     }

    init() {
        let parent = this;
        $(this.selector + "-dropdown.ui.selection.dropdown").dropdown(
            {
                useLables: false,
                allowAdditions: false,
                onChange: function(data_value, text, item)
                    {
                        let params = {
                            parent: parent,
                            value: data_value
                        }
                        parent.events.trigger("change", params);
                    }
            }
        );

        // Events
        this.events.on("change", parent.update_selection);
    }

    add_item(value, text) {
        let item = `
            <div class="item mdc-ripple-surface" data-value="${value}">${text}</div>
        `;
        let dropdown = $(this.selector + "-dropdown div.menu");
        dropdown.append(item);
        this.items.push({value: value, text:text});

        this.add_ripple_to_last();
    }

    add_ripple_to_last() {
        let items = $(this.selector + "-dropdown div.menu .item");
        let last_item = items[items.length - 1];
        mdc.ripple.MDCRipple.attachTo(last_item);
    }

    add_items(items) {
        for (let item of items) {
            let value = item.value;
            let text = item.text;
            this.add_item(value, text);
        }
    }

    update_selection(params) {
        let parent = params.parent;
        let value = params.value;

        parent.selected_item = value;
    }

    set(value)
    {
        $(this.selector + "-dropdown.ui.selection.dropdown").dropdown('set selected', value.toString());
        this.set_default_text(this.get_text_from_value(value));
    }

    set_default_text(text) {
        $(this.selector + " div.default.text").html(text);
    }

    clear() {
        if (this.params.fallback.value != undefined) {
            this.set(this.params.fallback.value);
            this.set_default_text(this.params.fallback.text);
        } else {
            $(this.selector + "-dropdown").dropdown("clear");
        }
        
    }

    get_text_from_value(value) {
        let text = this.items[0].value;
        for (let item of this.items) {
            if (item.value == value) {
                text = item.text;
            }
        }

        return text;
    }
    
    get()
    {
        if(this.selected_item == null)
        {   
            if (this.params.fallback_value != undefined) {
                this.selected_item = this.params.fallback_value;
                let value = this.get_text_from_value(this.fallback_value);
                this.set(value);
            }
        }
        return this.selected_item;
    }
}

class Textbox {
    constructor(id, params) {
        this.id = id;
        this.selector = `#${id}`;

        let expected_fields = [
            "placeholder",
            "fallback_value",
            {"events": ["keyup_change"]}
        ];
        this.params = new Params_Parser(expected_fields, params).params;

        this.events = new Events();

        this.create();
        this.init();
    }

    init() {
        let parent = this;
        if (this.events.keyup_change == true) {
            $(this.selector).on("keyup", function() {
                let value = parent.get();
                parent.events.trigger("change", value);
            });
        }

        $(this.selector).on("change", function() {
            let value = parent.get();
            parent.events.trigger("change", value);
        });
    }

    create() {
        if (this.params.placeholder != undefined) {
            $(this.selector).attr("placeholder", this.params.placeholder);
        }
        if (this.params.fallback_value != undefined) {
            this.set(this.params.fallback_value);
        }
    }

    get() {
        let val = $(this.selector).val();
        if (this.params.fallback_value != undefined) {
            val = is_null_or_empty(val) ? this.params.fallback_value : val;
        }
        return val;
    }

    set(value) {
        $(this.selector).val(value);
    }

    clear() {
        this.set("");
        if (this.params.fallback_value != undefined) {
            this.set(this.params.fallback_value);
        }
    }
}

class Checkbox {
    constructor(id, params) {
        this.id = id;
        this.selector = `#${id}`;

        let expected_fields = [
            "fallback_value",
            {"content": ["true", "false"]}
        ];
        this.params = new Params_Parser(expected_fields, params).params;

        this.events = new Events();

        this.state = true;

        this.create();
        this.init();
    }

    init() {
        let parent = this;
        $(this.selector + " > .button").on("click", function () {
            let state = parent.handle_state();
            parent.events.trigger("change",state);
        });
    }

    handle_state(parent) {
        parent = parent == undefined ? this : parent;

        parent.state = !parent.state;
        this.set(parent.state);
    }

    create() {
        let inner_button = `
            <div class="button">
                <p>Activado</p>
            </div>
        `;
        $(this.selector).append(inner_button);

        if (this.params.fallback_value != undefined) {
            this.set(this.params.fallback_value);
        } else {
            this.set(true);
        }
    }

    get() {
        return this.state;
    }

    set(value) {
        this.state = value;
        let content = "Activado";
        if (this.params.content.true != undefined) {
            content = this.params.content.true;
        }

        if (!this.state) {
            content = "Desactivado";
            if (this.params.content.false != undefined) {
                content = this.params.content.false;
            }
        }

        $(this.selector + " > .button > p").html(content);
    }

    clear() {
        if (this.params.fallback_value != undefined) {
            this.set(this.params.fallback_value);
        }
    }
}

class Overlay {
    constructor(id, params) {
        this.id = id;
        this.selector = `#${this.id}`;

        let expected_fields = ["actions"];
        this.params = new Params_Parser(expected_fields, params).params;

        this.is_visible = true;

        this.events = new Events();

        this.create();
        this.init();
    }

    create() {
        if (!$(this.selector).hasClass("overlay")) {
            $(this.selector).addClass("overlay");
        }
    }

    init() {
        this.hide(false);

        let parent = this;
        $(window).resize(function() {
            parent.set_items_height(parent);
        });

        this.set_items_height();
    }

    set_items_height(parent) {
        parent = parent == undefined ? this : parent;

        let title_height = get_height(parent.selector + " > .title");
        $(this.selector + " > .form").css("height", `calc(100% - ${title_height}px)`);
    }

    show(animate=true) {
        if (this.is_visible) return;

        let parent = this;
        if ($(this.selector).hasClass("hidden")) {
            $("body").css("overflow", "hidden");
            $(this.selector).css("overflow-y", "auto");
            $(this.selector).removeClass("hidden");
            if (animate) {
                anime({
                    targets: parent.selector,
                    opacity: [1],
                    scale: [0.5, 1],
                    duration: 400,
                    complete: function() {
                        parent.set_items_height(parent);
                    }
                });
            }
            this.set_items_height();
            this.is_visible = true;
        }
    }

    hide(animate=true) {
        if (!this.is_visible) return;

        let parent = this;
        if (!$(parent.selector).hasClass("hidden")) {
            $("body").css("overflow", "auto");
            if (animate) {
                anime({
                    targets: parent.selector,
                    opacity: [1, 0],
                    duration: 200,
                    complete: function() {
                        $(parent.selector).addClass("hidden");
                        parent.events.trigger("closed");
                    }
                });
            } else {
                $(parent.selector).addClass("hidden");
                parent.events.trigger("closed");
            }

            this.is_visible = false;
        }
    }
}