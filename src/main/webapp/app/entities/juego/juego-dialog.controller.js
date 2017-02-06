(function() {
    'use strict';

    angular
        .module('proyectoGlobalGamingApp')
        .controller('JuegoDialogController', JuegoDialogController);

    JuegoDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'DataUtils', 'entity', 'Juego', 'Sala', 'Idioma'];

    function JuegoDialogController ($timeout, $scope, $stateParams, $uibModalInstance, DataUtils, entity, Juego, Sala, Idioma) {
        var vm = this;

        vm.juego = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;
        vm.save = save;
        vm.salas = Sala.query();
        vm.idiomas = Idioma.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.juego.id !== null) {
                Juego.update(vm.juego, onSaveSuccess, onSaveError);
            } else {
                Juego.save(vm.juego, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('proyectoGlobalGamingApp:juegoUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


        vm.setPortada = function ($file, juego) {
            if ($file && $file.$error === 'pattern') {
                return;
            }
            if ($file) {
                DataUtils.toBase64($file, function(base64Data) {
                    $scope.$apply(function() {
                        juego.portada = base64Data;
                        juego.portadaContentType = $file.type;
                    });
                });
            }
        };
        vm.datePickerOpenStatus.fechaLanzamiento = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
