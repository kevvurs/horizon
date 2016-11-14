$( window ).resize(function() {
    if ($( window ).width() < 640) {
        $( "#evote_listing" ).css("display", "none");
        $( "#evote_tile" ).css("width","100%");
        $( "#evote_tile" ).css("height","160px");
    } else {
        $( "#evote_listing" ).css("display", "table-cell");
        $( "#evote_tile" ).css("height","128px");
        $( "#evote_tile" ).css("width","25%");
    }
});

$( "#evote_tile" ).hover(
    function() {
        $( this ).css("background-color", "#F4F4F4");
    },
    function() {
        $( this ).css("background-color", "#F46464");
    }
);

$( "#evote_listing" ).hover(
    function() {

    },
    function() {

    }
);

$( "#evote_tile" ).click(function() {
    window.location.href = "evote/roadmap.html";
});