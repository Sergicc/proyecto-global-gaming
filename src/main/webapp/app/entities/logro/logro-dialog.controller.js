(function() {
    'use strict';

    angular
        .module('proyectoGlobalGamingApp')
        .controller('LogroDialogController', LogroDialogController);

    LogroDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Logro', 'UserLogro'];

    function LogroDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Logro, UserLogro) {
        var vm = this;

        vm.logro = entity;
        vm.clear = clear;
        vm.save = save;
        vm.userlogroes = UserLogro.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.logro.id !== null) {
                Logro.update(vm.logro, onSaveSuccess, onSaveError);
            } else {
                Logro.save(vm.logro, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('proyectoGlobalGamingApp:logroUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
