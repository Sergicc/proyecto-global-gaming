(function() {
    'use strict';

    angular
        .module('proyectoGlobalGamingApp')
        .controller('PaisDialogController', PaisDialogController);

    PaisDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'DataUtils', 'entity', 'Pais'];

    function PaisDialogController ($timeout, $scope, $stateParams, $uibModalInstance, DataUtils, entity, Pais) {
        var vm = this;

        vm.pais = entity;
        vm.clear = clear;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.pais.id !== null) {
                Pais.update(vm.pais, onSaveSuccess, onSaveError);
            } else {
                Pais.save(vm.pais, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('proyectoGlobalGamingApp:paisUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


        vm.setBandera = function ($file, pais) {
            if ($file && $file.$error === 'pattern') {
                return;
            }
            if ($file) {
                DataUtils.toBase64($file, function(base64Data) {
                    $scope.$apply(function() {
                        pais.bandera = base64Data;
                        pais.banderaContentType = $file.type;
                    });
                });
            }
        };

    }
})();
