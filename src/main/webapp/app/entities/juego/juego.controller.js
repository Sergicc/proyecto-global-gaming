(function() {
    'use strict';

    angular
        .module('proyectoGlobalGamingApp')
        .controller('JuegoController', JuegoController);

    JuegoController.$inject = ['$scope', '$state', 'DataUtils', 'Juego', 'ParseLinks', 'AlertService', 'paginationConstants', 'searchJuego', 'Idioma'];

    function JuegoController ($scope, $state, DataUtils, Juego, ParseLinks, AlertService, paginationConstants, searchJuego, Idioma) {
        var vm = this;

        vm.juegos = [];
        vm.juegosByFilters = searchJuego;
        vm.listJuego = [];
        vm.loadPage = loadPage;
        vm.itemsPerPage = paginationConstants.itemsPerPage;
        vm.page = 0;
        vm.links = {
            last: 0
        };
        vm.predicate = 'id';
        vm.reset = reset;
        vm.reverse = true;
        vm.openFile = DataUtils.openFile;
        vm.byteSize = DataUtils.byteSize;
        vm.idioma = Idioma.query();

        // es igual a..
        //?location=Barcelona
        //parametro:valor

        vm.juegoByFilters = function () {
            if(vm.juegosByFilters.idioma==null){
                Juego.byFilters({
                    titulo: vm.juegosByFilters.titulo,
                    descripcion: vm.juegosByFilters.descripcion,
                    desarrollador: vm.juegosByFilters.desarrollador,
                    genero: vm.juegosByFilters.genero,
                    edadRecomendada: vm.juegosByFilters.edadRecomendada,
                    minCapacidadJugadores: vm.juegosByFilters.minCapacidadJugadores,
                    maxCapacidadJugadores: vm.juegosByFilters.maxCapacidadJugadores,
                    minValoracionWeb: vm.juegosByFilters.minValoracionWeb,
                    maxValoracionWeb: vm.juegosByFilters.maxValoracionWeb,
                    minValoracionUsers: vm.juegosByFilters.minValoracionUsers,
                    maxValoracionUsers: vm.juegosByFilters.maxValoracionUsers
                    //idioma: vm.juegosByFilters.idioma.nombre
                }, onSuccessByFilters, onError);
            }else {
                Juego.byFilters({
                    titulo: vm.juegosByFilters.titulo,
                    descripcion: vm.juegosByFilters.descripcion,
                    desarrollador: vm.juegosByFilters.desarrollador,
                    genero: vm.juegosByFilters.genero,
                    edadRecomendada: vm.juegosByFilters.edadRecomendada,
                    minCapacidadJugadores: vm.juegosByFilters.minCapacidadJugadores,
                    maxCapacidadJugadores: vm.juegosByFilters.maxCapacidadJugadores,
                    minValoracionWeb: vm.juegosByFilters.minValoracionWeb,
                    maxValoracionWeb: vm.juegosByFilters.maxValoracionWeb,
                    minValoracionUsers: vm.juegosByFilters.minValoracionUsers,
                    maxValoracionUsers: vm.juegosByFilters.maxValoracionUsers,
                    idioma: vm.juegosByFilters.idioma.nombre
                }, onSuccessByFilters, onError);
            }
            function sort() {
                var result = [vm.predicate + ',' + (vm.reverse ? 'asc' : 'desc')];
                if (vm.predicate !== 'id') {
                    result.push('id');
                }
                return result;
            }

            function onError(error) {
                toastr.error(error.data.error, 'Error!');
            }

            function onSuccessByFilters(data, headers) {
                // vm.links = ParseLinks.parse(headers('link'));
                vm.totalItems = headers('X-Total-Count');
                vm.queryCount = vm.totalItems;
                vm.listJuego = data;
                vm.page = pagingParams.page;
                console.log("onsuccess");
            }

        };

        loadAll();

        function loadAll() {

            Juego.query({
                page: vm.page,
                size: vm.itemsPerPage,
                sort: sort()
            }, onSuccess, onError);


        };

        function sort() {
            var result = [vm.predicate + ',' + (vm.reverse ? 'asc' : 'desc')];
            if (vm.predicate !== 'id') {
                result.push('id');
            }
            return result;
        }

        function onSuccess(data, headers) {
            vm.links = ParseLinks.parse(headers('link'));
            vm.totalItems = headers('X-Total-Count');
            for (var i = 0; i < data.length; i++) {
                vm.juegos.push(data[i]);
            }
        }

        function onSuccessByFilters(data, headers) {

            for (var i = 0; i < data.length; i++) {
                vm.juegosByFilters.push(data[i]);
            }
        }

        function onError(error) {
            AlertService.error(error.data.message);
        }


        function reset() {
            vm.page = 0;
            vm.juegos = [];
            loadAll();
        }

        function loadPage(page) {
            vm.page = page;
            loadAll();
        }

        vm.loadByFilters = function () {
            Juego.byFilters({
                titulo: vm.juegosByFilters.titulo
                // descripcion: vm.juegosByFilters.descripcion,
                // desarrollador: vm.juegosByFilters.desarrollador,
                // genero: vm.juegosByFilters.genero,
                // edadRecomendada: vm.juegosByFilters.edadRecomendada,
                // minCapacidadJugadores: vm.juegosByFilters.minCapacidadJugadores,
                // maxCapacidadJugadores: vm.juegosByFilters.maxCapacidadJugadores,
                // minValoracionWeb: vm.juegosByFilters.minValoracionWeb,
                // maxValoracionWeb: vm.juegosByFilters.maxValoracionWeb,
                // minValoracionUsers: vm.juegosByFilters.minValoracionUsers,
                // maxValoracionUsers: vm.juegosByFilters.maxValoracionUsers,
                // idioma: vm.juegosByFilters.idioma
            }, onSuccessByFilters, onError);

            function onSuccessByFilters(data, headers) {

                for (var i = 0; i < data.length; i++) {
                    vm.juegosByFilters.push(data[i]);
                }
            }

            function onError(error) {
                AlertService.error(error.data.message);
            }
        }
    }
})();
