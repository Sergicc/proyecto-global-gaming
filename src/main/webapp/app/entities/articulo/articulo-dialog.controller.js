(function() {
    'use strict';

    angular
        .module('proyectoGlobalGamingApp')
        .controller('ArticuloDialogController', ArticuloDialogController);

    ArticuloDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'DataUtils', 'entity', 'Articulo', 'Etiqueta', 'Foto'];

    function ArticuloDialogController ($timeout, $scope, $stateParams, $uibModalInstance, DataUtils, entity, Articulo, Etiqueta, Foto) {
        var vm = this;

        vm.articulo = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;
        vm.save = save;
        vm.etiquetas = Etiqueta.query();
        vm.fotos = Foto.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.articulo.id !== null) {
                Articulo.update(vm.articulo, onSaveSuccess, onSaveError);
            } else {
                Articulo.save(vm.articulo, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('proyectoGlobalGamingApp:articuloUpdate', result);
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
