(function() {
    'use strict';

    angular
        .module('proyectoGlobalGamingApp')
        .controller('FotoDialogController', FotoDialogController);

    FotoDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'DataUtils', 'entity', 'Foto', 'Articulo'];

    function FotoDialogController ($timeout, $scope, $stateParams, $uibModalInstance, DataUtils, entity, Foto, Articulo) {
        var vm = this;

        vm.foto = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;
        vm.save = save;
        vm.articulos = Articulo.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.foto.id !== null) {
                Foto.update(vm.foto, onSaveSuccess, onSaveError);
            } else {
                Foto.save(vm.foto, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('proyectoGlobalGamingApp:fotoUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.fechaCreacion = false;

        vm.setImagen = function ($file, foto) {
            if ($file && $file.$error === 'pattern') {
                return;
            }
            if ($file) {
                DataUtils.toBase64($file, function(base64Data) {
                    $scope.$apply(function() {
                        foto.imagen = base64Data;
                        foto.imagenContentType = $file.type;
                    });
                });
            }
        };

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
