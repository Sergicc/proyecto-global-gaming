(function() {
    'use strict';

    angular
        .module('proyectoGlobalGamingApp')
        .controller('ValoracionJuegoDialogController', ValoracionJuegoDialogController);

    ValoracionJuegoDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'ValoracionJuego', 'User', 'Juego'];

    function ValoracionJuegoDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, ValoracionJuego, User, Juego) {
        var vm = this;

        vm.valoracionJuego = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.users = User.query();
        vm.juegos = Juego.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.valoracionJuego.id !== null) {
                ValoracionJuego.update(vm.valoracionJuego, onSaveSuccess, onSaveError);
            } else {
                ValoracionJuego.save(vm.valoracionJuego, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('proyectoGlobalGamingApp:valoracionJuegoUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.timeStamp = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
