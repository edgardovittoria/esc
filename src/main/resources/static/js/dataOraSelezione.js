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
    },
  });