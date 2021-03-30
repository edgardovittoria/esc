   $("#timepickerInizio").timepicker({
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
      $("#timepickerFine").remove();
      $("#inputFine").append(
        '<input id="timepickerFine" class="timepicker" name="oraFine" value=""/>'
      );
      $("#timepickerFine").timepicker({
        timeFormat: "HH:mm",
        minTime: $("#timepickerInizio").val(),
        maxTime: "22:00",
        defaultTime: $("#timepickerInizio").val(),
        startTime: $("#timepickerInizio").val(),
        dynamic: false,
        dropdown: true,
        scrollbar: true,
        change: function(){
         
          aggiornaImpiantiDisponibili();
        }
      });
      
    },
  });



function aggiornaImpiantiDisponibili() {
  var dataOraInizio = $("#start").val()+"T"+$("#timepickerInizio").val();
          var dataOraFine = $("#start").val()+"T"+$("#timepickerFine").val();
          var sportSelezionato = $("input[name='sportSelezionato']:checked").val();
  var appuntamento = [dataOraInizio, dataOraFine];
  var dati = {
    'orario': appuntamento,
    'sport' : sportSelezionato
  };
  console.log(dati);
  $.ajax({
    method: "POST",
    url: "http://localhost:8080/aggiornaOpzioni/impianti",
    contentType: "application/json; charset=UTF-8",
    dataType: "json",
    data: JSON.stringify(dati),
    success: 
       function (impianti) {
        console.log(impianti);
        $('#selezioneImpianto').html('');
        var options = '';    
        $.each(impianti, function(id, pavimentazione){
            options += '<option value="' + id + '">' + id + " " + pavimentazione + '</option>';  
        }) ;        
        $('#selezioneImpianto').append(options); 
      },
  });
}

