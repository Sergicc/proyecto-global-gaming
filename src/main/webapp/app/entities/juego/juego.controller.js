(function() {
    'use strict';

    angular
        .module('proyectoGlobalGamingApp')
        .controller('JuegoController', JuegoController);

    JuegoController.$inject = ['$scope', '$state', 'DataUtils', 'Juego', 'ParseLinks', 'AlertService', 'paginationConstants'];

    function JuegoController ($scope, $state, DataUtils, Juego, ParseLinks, AlertService, paginationConstants) {
        var vm = this;

        vm.juegos = [];
        vm.juegosByFilters = [];
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

        loadAll();

        function loadAll () {
            Juego.query({
                page: vm.page,
                size: vm.itemsPerPage,
                sort: sort()
            }, onSuccess, onError);

            Juego.byFilters({
                desarrollador: "Blizzard",
                minCapacidadJugadores: "8",
                maxCapacidadJugadores: "12"
            }, onSuccessByFilters, onError );

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
        }

        function reset () {
            vm.page = 0;
            vm.juegos = [];
            loadAll();
        }

        function loadPage(page) {
            vm.page = page;
            loadAll();
        }
    }
})();
