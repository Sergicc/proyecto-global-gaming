(function() {
    'use strict';

    angular
        .module('proyectoGlobalGamingApp')
        .controller('UserLogroDeleteController',UserLogroDeleteController);

    UserLogroDeleteController.$inject = ['$uibModalInstance', 'entity', 'UserLogro'];

    function UserLogroDeleteController($uibModalInstance, entity, UserLogro) {
        var vm = this;

        vm.userLogro = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            UserLogro.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
