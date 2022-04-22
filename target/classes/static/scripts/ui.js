class Rippleize {
    all() {
        this.buttons();
        this.cards();
        this.data_grid_cells();
        this.nav();
    }

    element(selector) {
        let items = $(selector);
        for (let item of items) {
            $(item).addClass("mdc-ripple-surface");
            mdc.ripple.MDCRipple.attachTo(item);
        }
    }

    buttons(selector) {
        let items = null;
        if (selector == undefined) {
            items = $('.button');
        } else {
            items = $(selector);
        }

        for (let item of items) {
            let children = $(item).children();
            if (children.length == 1) {
                $(item).addClass("mdc-ripple-surface");
                mdc.ripple.MDCRipple.attachTo(item);
            } else {
                for (let child of children) {
                    $(child).addClass("mdc-ripple-surface");
                    mdc.ripple.MDCRipple.attachTo(child);
                }
            } 
        }
    }

    cards(selector) {
        let items = $('.card');

        for (let item of items) {
            $(item).addClass("mdc-ripple-surface");
            mdc.ripple.MDCRipple.attachTo(item);
        }
    }

    data_grid_cells() {
        let items = $('.data-grid .cell');

        for (let item of items) {
            $(item).addClass("mdc-ripple-surface");
            mdc.ripple.MDCRipple.attachTo(item);
        }
    }

    nav() {
        let items = $('.nav > .nav-item');

        for (let item of items) {
            $(item).addClass("mdc-ripple-surface");
            mdc.ripple.MDCRipple.attachTo(item);
        }
    }
}

let rippleize = new Rippleize();
$(document).ready(function() {
    rippleize.all();

    let fab = $(".fab");
    if (fab.length > 0) {
        rippleize.element(".fab div");
    }
});

class Header {
    constructor(params) {
        this.selector = "body > header";

        let expected_fields = ["nav", "title", "subtitle", "back"];
        this.params = new Params_Parser(expected_fields, params).params;

        this.events = new Events();

        this.init();
    }

    init() {
        // Set content
        $("#nav-page-title h3").html(this.params.title);
        $("body > .header > .foreground > .title h1").html(this.params.title);
        $("body > .header > .foreground > .subtitle h3").html(this.params.subtitle);

        let parent = this;

        if (this.params.nav == undefined || this.params.nav == false) {
            $("body > .header > .nav").addClass("hidden");
        } else {
            rippleize.element("#nav-action div");
            $("#nav-action").on("click", function() {
                parent.events.trigger("action");
            });
        }

        if (this.params.nav) {
            $("#nav-page-title h3").addClass("hidden");
        }

        $(window).scroll(function() {
            if (parent.params.nav == true) {
                parent.handle_nav_title($(window).scrollTop());
            }
        });

        if (this.params.back == true) {
            $("#nav-action span.icon").html("keyboard_backspace");
            $("#nav-action").on("click", function() {
                window.location = "/";
            })
        } else {
            $("#nav-action").addClass("hidden");
        }
    }

    handle_nav_title(scroll_top) {
        let header_heigth = $("body > .header").offset().top + $("body > .header").height();
        if (scroll_top >= header_heigth) {
            if ($("#nav-page-title h3").hasClass("hidden")) {
                $("#nav-page-title h3").removeClass("hidden");
                $("body > .header > .nav").addClass("white");
                anime({
                    targets: '#nav-page-title h3',
                    opacity: [0, 1],
                    marginTop: ["-2rem", "0"],
                    duration: 800
                });
                $("body > .header > .nav").addClass("shadow");
            }
        } else {
            if (!$("#nav-page-title h3").hasClass("hidden")) {
                anime({
                    targets: "#nav-page-title h3",
                    opacity: [1, 0],
                    marginTop: ["0", "-2rem"],
                    duration: 400,
                    easing: "linear",
                    complete: function() {
                        $("#nav-page-title h3").addClass("hidden");
                        $("body > .header > .nav").removeClass("white");
                    }
                });
                $("body > .header > .nav").removeClass("shadow");
            }
        }
    }
}

class FAB {
    constructor(id, params) {
        this.id = id;
        this.selector = `#${id}`;

        let expected_fields = ["scroll_on_overflow"];
        this.params = new Params_Parser(expected_fields, params).params;
        
        this.events = new Events();

        this.current_icon = $(this.selector + " span.icon").html();
        this.scroll_up_activated = false;

        this.init();
    }

    init() {
        let parent = this;
        if (this.params.scroll_on_overflow) {
            $(window).scroll(function() {
                let scroll_top = $(window).scrollTop();
                parent.handle_scroll(scroll_top, parent);
            });
        }

        $(this.selector).on("click", function() {
            if (parent.scroll_up_activated) {
                $("html, body").animate({scrollTop:0}, 200, 'swing');
            } else {
                parent.events.trigger("click");
            }   
        });

        let nav = $('body > .nav');
        if (nav.length > 0) {
            $(this.selector).css("bottom", "calc(3rem + 2*var(--padding-big))");
        }
    }

    handle_scroll(scroll_top, parent) {
        if (scroll_top > $(window).height()) {
            if (parent.scroll_up_activated == false) {
                $(parent.selector + " span.icon").html("vertical_align_top");
                anime({
                    targets: parent.selector + " span.icon",
                    marginTop: ["3rem", "0"],
                    duration: 1000,
                });
                
            }   
            parent.scroll_up_activated = true;
        } else {
            if (parent.scroll_up_activated == true) {
                $(parent.selector + " span.icon").html(parent.current_icon);
                anime({
                    targets: parent.selector + " span.icon",
                    marginTop: ["-3rem", "0"],
                    duration: 1000,
                });
            }
            parent.scroll_up_activated = false;
        }
    }
}

class Nav {
    constructor(id_active) {
        this.id_active = id_active;
        this.selector_active = `#${id_active}`;

        this.selector = "body > .nav";
        this.events = new Events();

        this.init();
    }

    init() {
        let nav_items = $(this.selector + " > .nav-item");
        let parent = this;
        for (let nav_item of nav_items) {
            $(nav_item).on("click", function() {
                parent.events.trigger($(nav_item).attr("id"));
            });
        }

        if (this.id_active != undefined) {
            $(this.selector_active).addClass("active");
        }
    }
}