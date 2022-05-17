let header = new Header({
    nav: false,
    title: "Bienvenido,",
    subtitle: "Aquí te puedes crear un nuevo usuario",
});

const createForm = () => {
    signupForm.add({
        id: "username",
        label: "Usuario",
        input: "",
    });

    signupForm.add({
        id: "email",
        label: "Email",
        input: "",
    });

    signupForm.add({
        id: "password",
        label: "Contraseña",
        password: "",
    });

    signupForm.events.on("post-success", () => {
        window.location = "/";
    });
};

let signupForm = new Form("user-new", {
    post: {
        on_save: true,
        url: "/api/v1/users",
    },
    states: {
        editable: true,
    },
});

$("#action-save").on("click", () => {
    signupForm.save();
});

createForm();
