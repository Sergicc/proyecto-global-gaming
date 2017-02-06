(function() {
    'use strict';

    angular
        .module('proyectoGlobalGamingApp')
        .controller('UserLogroDialogController', UserLogroDialogController);

    UserLogroDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'UserLogro', 'User', 'Logro'];

    function UserLogroDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, UserLogro, User, Logro) {
        var vm = this;

        vm.userLogro = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.users = User.query();
        vm.logroes = Logro.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.userLogro.id !== null) {
                UserLogro.update(vm.userLogro, onSaveSuccess, onSaveError);
            } else {
                UserLogro.save(vm.userLogro, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('proyectoGlobalGamingApp:userLogroUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.fecha = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
