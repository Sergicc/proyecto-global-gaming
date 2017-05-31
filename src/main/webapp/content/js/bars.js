/**
 * Created by 53868459K on 09/03/2017.
 */

$(document).ready(function(){

    $('#alternar-bar-left').toggle(

        /*
         Primer click.
         Función que descubre un panel oculto
         y cambia el texto del botón.
         */
        function(e){
            $('#bar-left').animate({width:'toggle'},2000)
            $(this).text('Ocultar');
            e.preventDefault();
        }, // Separamos las dos funciones con una coma

        /*
         Segundo click.
         Función que oculta el panel
         y vuelve a cambiar el texto del botón.
         */
        function(e){
            $('#bar-left').animate({width:'toggle'},2000)
            $(this).text('Mostrar');
            e.preventDefault();
        }
    );
});
