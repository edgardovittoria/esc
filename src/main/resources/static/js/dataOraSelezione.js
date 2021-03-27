$(".timepicker").timepicker({
  timeFormat: "HH:mm",
  interval: 30,
  minTime: "10:00",
  maxTime: "21:00",
  defaultTime: "10",
  startTime: "10:00",
  dynamic: false,
  dropdown: true,
  scrollbar: true,
  change: function () {
    $(".timepickerFine").remove();
    $("#inputFine").append(
      '<input id="timepickerFine" class="timepickerFine" />'
    );
    $(".timepickerFine").timepicker({
      timeFormat: "HH:mm",
      minTime: $(".timepicker").val(),
      maxTime: "22:00",
      defaultTime: $(".timepicker").val(),
      startTime: $(".timepicker").val(),
      dynamic: false,
      dropdown: true,
      scrollbar: true,
    });
    var dataOraInizio = $("#start").val()+"T"+$(".timepicker").val();
    var dataOraFine = $("#start").val()+"T"+$(".timepickerFine").val();
    aggiornaImpiantiDisponibili(dataOraInizio, dataOraFine);
  },
});

function aggiornaImpiantiDisponibili(dataOraInizio, dataOraFine) {
  var appuntamento = [dataOraInizio, dataOraFine];
  console.log(JSON.stringify(appuntamento));
  $.ajax({
    method: "POST",
    url: "http://localhost:8080/aggiornaOpzioni/impianti",
    contentType: "application/json; charset=UTF-8",
    data: JSON.stringify(appuntamento),
    statusCode: {
      200: function (impianti) {
        console.log(impianti);
      },
    },
  });
}
