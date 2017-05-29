(function() {
    'use strict';

    angular
        .module('proyectoGlobalGamingApp')
        .controller('JuegoDetailController', JuegoDetailController);

    JuegoDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'DataUtils', 'entity', 'Juego', 'Sala', 'Idioma'];

    function JuegoDetailController($scope, $rootScope, $stateParams, previousState, DataUtils, entity, Juego, Sala, Idioma) {
        var vm = this;

        vm.juego = entity;
        vm.previousState = previousState.name;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;

        var unsubscribe = $rootScope.$on('proyectoGlobalGamingApp:juegoUpdate', function(event, result) {
            vm.juego = result;
        });
        $scope.$on('$destroy', unsubscribe);


        vm.addValoracion = function(){

            Juego.addValoracion({idJuego: vm.juego.id, valoracion: vm.juego.valoracion},{},successLike);

        };


        Juego.stats ({'id' : vm.juego.id}, function (result) {
            vm.juego.stats = result;
        })


        var successLike = function(result){
            console.log(result);
        }
    }
})();
