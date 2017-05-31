$(function(){

  $('#rangeslider').slider({
    range: true,
    min: 1,
    max: 10,
    values: [ 3, 7 ],
    slide: function( event, ui ) {
      $('#rangeval').html(ui.values[0]+" - "+ui.values[1]);
    }
  });
});
