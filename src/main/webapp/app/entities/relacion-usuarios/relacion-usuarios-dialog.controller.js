(function() {
    'use strict';

    angular
        .module('proyectoGlobalGamingApp')
        .controller('RelacionUsuariosDialogController', RelacionUsuariosDialogController);

    RelacionUsuariosDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'RelacionUsuarios', 'User'];

    function RelacionUsuariosDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, RelacionUsuarios, User) {
        var vm = this;

        vm.relacionUsuarios = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.users = User.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.relacionUsuarios.id !== null) {
                RelacionUsuarios.update(vm.relacionUsuarios, onSaveSuccess, onSaveError);
            } else {
                RelacionUsuarios.save(vm.relacionUsuarios, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('proyectoGlobalGamingApp:relacionUsuariosUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.fechaEmision = false;
        vm.datePickerOpenStatus.fechaResolucion = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
