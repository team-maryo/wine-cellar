let header = new Header({
    nav: false,
    title: "Hola,",
    subtitle: "Bodega - Las Tejuelas",
});

$("#action-signup").on("click", () => {
    window.location = "/auth/signup";
});
