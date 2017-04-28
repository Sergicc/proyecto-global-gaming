(function() {
    'use strict';

    angular
        .module('proyectoGlobalGamingApp')
        .controller('SalaController', SalaController);

    SalaController.$inject = ['$scope', '$state', 'DataUtils', 'Sala', 'ParseLinks', 'AlertService', 'paginationConstants', 'searchSala'];

    function SalaController ($scope, $state, DataUtils, Sala, ParseLinks, AlertService, paginationConstants, searchSala) {
        var vm = this;

        vm.salas = [];
        vm.salasByFilters = searchSala;
        vm.listSala = [];
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

        vm.salaByFilters = function () {


            Sala.byFilters({
                nombre: vm.salasByFilters.nombre,
                descripcion: vm.salasByFilters.descripcion,
                minLimiteUsuarios: vm.salasByFilters.minLimiteUsuarios,
                maxLimiteUsuarios: vm.salasByFilters.maxLimiteUsuarios,
                juego: vm.salasByFilters.juego
            }, onSuccessByFilters, onError);

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
                vm.listSala = data;
                vm.page = pagingParams.page;
                console.log("onsuccess");
            }

        };

        loadAll();

        function loadAll () {
            Sala.query({
                page: vm.page,
                size: vm.itemsPerPage,
                sort: sort()
            }, onSuccess, onError);
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
                    vm.salas.push(data[i]);
                }
            }

            function onError(error) {
                AlertService.error(error.data.message);
            }
        }

        function reset () {
            vm.page = 0;
            vm.salas = [];
            loadAll();
        }

        function loadPage(page) {
            vm.page = page;
            loadAll();
        }
    }
})();
