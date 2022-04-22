const DEBUG = true;
function log(data) {
    if (DEBUG) {
        console.log(data);
    }
}

const protectedKeyCodes = [
    27,112,113,114,115,116,117,118,
    119,120,121,123,124,125,44,45,46,
    37,38,39,40,20,91,18,17
]

function is_protected_key(keyCode) {
    if (protectedKeyCodes.includes(keyCode)) {
        return true;
    }
    return false;
}

//COOKIES
function get_cookie(name) {
    let cookieValue = null;
    if (document.cookie && document.cookie !== '') {
        const cookies = document.cookie.split(';');
        for (let i = 0; i < cookies.length; i++) {
            const cookie = cookies[i].trim();
            // Does this cookie string begin with the name we want?
            if (cookie.substring(0, name.length + 1) === (name + '=')) {
                cookieValue = decodeURIComponent(cookie.substring(name.length + 1));
                break;
            }
        }
    }
    return cookieValue;
}

//IS NULL OR EMPTY CHECK
function is_null_or_empty(variable) {
    if (variable == '' && typeof(variable) != "number") return true;
    if (variable == null) return true;
    return false;
}

function is_undefined_or_empty(variable) {
    if (variable == '' && typeof(variable) != "number") return true;
    if (variable == undefined) return true;
    return false;
}

//Round opt
function round_num_opt(a)
{
    var c = 0;
    10 > a && (c = 1);
    1 > a && (c = 2);
    return Math.round(a * Math.pow(10, c)) / Math.pow(10, c)
}

// CONVERT TO 2 DIGIT STRING
function covert_to_2_digit_string(number) {
    let suffix = String(number);
    let str_number = "";

    for (let i = 0; i < (2 - suffix.length); i++) {
        str_number += "0";
    }

    str_number += suffix;
    return str_number;
}

// REMOVE ELEMENT FROM STRING
function remove(elem, array) {
    let idx = null;
    for (let i = 0; i < array.length; i++) {
        if (array[i] == elem) {
            idx = i;
        }
    }
    if (idx != null) {
        array.splice(idx, 1);
    }

    return array;
}

function trunc_round(decimal) {
    let decimal_parts = decimal.toString().split(".");
    if (decimal_parts.length == 1) return decimal;
    let floating = decimal_parts[1];

    let output = decimal_parts[0];
    if (floating.length > 0) {
        let most_imp = floating[0];
        if (parseInt(most_imp) > 5) {
            output = parseInt(output) + 1;
            return output;
        } else if (parseInt(most_imp) < 5) {
            return parseInt(output);
        } else {
            output += '.5';
            return parseFloat(output);
        }
    }
}

// CONVERT BOOLEAN TO SPANISH
function boolean_to_spanish(variable) {
    let str_boolean = "Sí";
    if (!variable) str_boolean = "No";

    return str_boolean;
}

// JOIN ARRAY INTO STRING
function join_to_string(separator, array, ignore_last=false) {
    let output = "";
    for (let i = 0; i < array.length; i++) {
        if (ignore_last && (i+1) == array.length) {
            output += array[i];
            break;
        } 
        output += array[i] + separator;
    }
    return output;
}

// TEXT INCLUDES ANY PART FROM SEQUENCE
function includes_unordered(search, comparison) {
    let coincidences = 0;
    comparison = comparison.split(" ");
    search = search.split(" ");
    for (let a of comparison) {
        for (let j = 0; j < search.length; j++) {
            let b = search[j];
            if (a.toLowerCase().includes(b.toLowerCase())) {
                coincidences++;
                search = remove(b, search);
            }
        }
    }

    if (coincidences >= search.length) return true;
    return false;
}

function unidecode (str) {
    let map = {
        'a' : 'á|à|ã|â|À|Á|Ã|Â',
        'e' : 'é|è|ê|É|È|Ê',
        'i' : 'í|ì|î|Í|Ì|Î',
        'o' : 'ó|ò|ô|õ|Ó|Ò|Ô|Õ',
        'u' : 'ú|ù|û|ü|Ú|Ù|Û|Ü',
        'c' : 'ç|Ç',
        'n' : 'ñ|Ñ'
    };
    
    for (let pattern in map) {
        str = str.replace(new RegExp(map[pattern], 'g'), pattern);
    }

    return str.toLowerCase();
}

// PRETTIFY DECIMAL
function prettify_decimal(input, decimals, round_up=true) {
    // Divide by point
    let decimal = parseFloat(input);
    if (round_up) {
        decimal = parseFloat(input).toFixed(decimals);
    }
    
    let decimal_str = decimal.toString().split(".");
    let integer = decimal_str[0];
    if (decimal_str.length != 2) {
        return "0";
    }
    let floating = decimal_str[1];

    let n = decimals;
    if (n > floating.length) {
        n = floating.length;
    }
    let output = integer + ".";
    for (let i = 0; i < n; i++) {
        output += floating[i];
    }

    return output;
}

function get_height(selector) {
    let padding = parseFloat($(selector).css("padding-top")) + parseFloat($(selector).css("padding-bottom"));
    let margin = parseFloat($(selector).css("margin-top")) + parseFloat($(selector).css("margin-bottom"));
    let height = parseFloat($(selector).height());
    return height + padding + margin + 1; // Add one for correcition
}

function get_csrf_token() {
    let csrftoken_temp = get_cookie('csrftoken');
    if (csrftoken_temp == null || csrftoken_temp == undefined) {
        csrftoken_temp = $("input[name=csrfmiddlewaretoken]").val();
    }

    return csrftoken_temp;
}

// DJANGO REQUIRED CONSTS
const csrftoken = get_csrf_token();


// GENERIC EVENT HANDLER
class Events {
	constructor(parent) {
        this.event_triggers = {};
        this.parent = parent;
	}

	on(event, callback) {
		if (!this.event_triggers[event]) {
			this.event_triggers[event] = [];
		}

		this.event_triggers[event].push(callback);
	}

	trigger(event, params) {
		if (this.event_triggers[event]) {
			for (let trigger of this.event_triggers[event]) {
                if (this.parent == undefined) {
                    trigger(params);
                } else {
                    trigger(this.parent, params);
                }
				
			}
		}
	}

	remove_event(event) {
		if (this.event_triggers[event]) {
			let index = this.event_triggers.indexOf(event);
			this.event_triggers.splice(index, 1);
		}
	}

	remove_callback(event, callback) {
		if (this.event_triggers[event]) {
			let event_callbacks = this.event_triggers[event];
			if (event_callbacks[callback]) {
				let callback_index = event.indexOf(callback);
				this.event_triggers[event].splice(callback_index, 1);
			}
		}
    }
}

class Memory_Events {
	constructor(parent) {
		this.memory_events = {};
		this.parent = parent;
    }
    
    has_triggered(event) {
        if (this.memory_events[event]) {
            return this.memory_events[event].has_been_triggered;
        }
        return false;
    }

	on(event, callback, params) {
		if (!this.memory_events[event]) {
			this.memory_events[event] = {
				has_been_triggered: false,
				triggers: [],
			};
		}

        if (callback != undefined) {
            this.memory_events[event].triggers.push(callback);
        }
		

		if (this.memory_events[event].has_been_triggered) {
			this.trigger_callback(callback, params);
		}
	}

	trigger(event, params) {
		if (this.memory_events[event]) {
            this.memory_events[event].has_been_triggered = true;
			for (let callback of this.memory_events[event].triggers) {
				this.trigger_callback(callback,params);
			}
		} else {
            this.on(event);
            this.memory_events[event].has_been_triggered = true;
        }
    }
    
    trigger_callback(callback, params) {
        if (this.parent == undefined) {
            callback(params);
        } else {
            callback(this.parent, params);
        }
    }

	reset_trigger(event) {
		if (this.memory_events[event]) {
			this.memory_events[event].has_been_triggered = false;
		}
	}

	remove_event(event) {
		if (this.memory_events[event]) {
			let index = this.memory_events.indexOf(event);
			this.memory_events.splice(index, 1);
		}
	}

	removeCallback(event, callback) {
		if (this.memory_events[event]) {
			let event_triggers = this.memory_events[event].triggers;
			if (event_triggers[callback]) {
				let callback_index = event.indexOf(callback);
				this.memory_events[event].splice(callback_index, 1);
			}
		}
	}
}


class Params_Parser {
    constructor(fields, params) {
        fields = this.parse_array(fields);
        params = (params == undefined) ? {} : params;
        this.params = this.fill_in(fields, params);
    }

    parse_array(array) {
        let fields = {};
        for (let item of array) {
            if (typeof(item) != "object") {
                fields[item] = undefined;
            } else {
                for (let key of Object.keys(item)) {
                    fields[key] = this.parse_array(item[key]);
                }
            }
        }

        return fields;
    }

    fill_in(fields, params) {
        params = (params == null) ? {} : params;

        let params_keys = Object.keys(params);
        for (let field_key of Object.keys(fields)) {
            if (!params_keys.includes(field_key)) {
                params[field_key] = undefined;
            }

            if (fields[field_key] != undefined) {
                params[field_key] = this.fill_in(fields[field_key], params[field_key]);
            }
        }

        return params;
    }
}