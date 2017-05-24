(function () {
    'use strict';

    angular
        .module('proyectoGlobalGamingApp')
        .controller('UserExtController', UserExtController);

    UserExtController.$inject = ['$scope', '$state', 'DataUtils', 'UserExt', 'ParseLinks', 'AlertService', 'paginationConstants', 'searchUserExt', 'Pais'];

    function UserExtController($scope, $state, DataUtils, UserExt, ParseLinks, AlertService, paginationConstants, searchUserExt, Pais) {
        var vm = this;

        vm.userExts = [];
        vm.userExtsByFilters = searchUserExt;
        vm.listUserExt = [];
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
        vm.pais = Pais.query();

        vm.UserExtByFilters = function () {

            if(vm.userExtsByFilters.pais==null) {
                UserExt.byFilters({
                    nick: vm.userExtsByFilters.nick,
                    idBattlenet: vm.userExtsByFilters.idBattlenet,
                    idSteam: vm.userExtsByFilters.idSteam,
                    idOrigin: vm.userExtsByFilters.idOrigin,
                    idLol: vm.userExtsByFilters.idLol
                    //pais: vm.userExtsByFilters.pais.nombre
                }, onSuccessByFilters, onError);
            }else{
                UserExt.byFilters({
                    nick: vm.userExtsByFilters.nick,
                    idBattlenet: vm.userExtsByFilters.idBattlenet,
                    idSteam: vm.userExtsByFilters.idSteam,
                    idOrigin: vm.userExtsByFilters.idOrigin,
                    idLol: vm.userExtsByFilters.idLol,
                    pais: vm.userExtsByFilters.pais.nombre
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
                vm.listUserExt = data;
                vm.page = pagingParams.page;
                console.log("onsuccess");
            }

        };

        loadAll();

        function loadAll() {
            UserExt.query({
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
                    vm.userExts.push(data[i]);
                }
            }

            function onError(error) {
                AlertService.error(error.data.message);
            }
        }

        function reset() {
            vm.page = 0;
            vm.userExts = [];
            loadAll();
        }

        function loadPage(page) {
            vm.page = page;
            loadAll();
        }
    }
})();
