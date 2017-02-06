(function() {
    'use strict';

    angular
        .module('proyectoGlobalGamingApp')
        .controller('SalaDialogController', SalaDialogController);

    SalaDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'DataUtils', 'entity', 'Sala', 'Juego', 'Mensaje', 'Idioma'];

    function SalaDialogController ($timeout, $scope, $stateParams, $uibModalInstance, DataUtils, entity, Sala, Juego, Mensaje, Idioma) {
        var vm = this;

        vm.sala = entity;
        vm.clear = clear;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;
        vm.save = save;
        vm.juegos = Juego.query();
        vm.mensajes = Mensaje.query();
        vm.idiomas = Idioma.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.sala.id !== null) {
                Sala.update(vm.sala, onSaveSuccess, onSaveError);
            } else {
                Sala.save(vm.sala, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('proyectoGlobalGamingApp:salaUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


        vm.setImagen = function ($file, sala) {
            if ($file && $file.$error === 'pattern') {
                return;
            }
            if ($file) {
                DataUtils.toBase64($file, function(base64Data) {
                    $scope.$apply(function() {
                        sala.imagen = base64Data;
                        sala.imagenContentType = $file.type;
                    });
                });
            }
        };

    }
})();
