(function() {
    'use strict';

    angular
        .module('proyectoGlobalGamingApp')
        .controller('LogroDialogController', LogroDialogController);

    LogroDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'DataUtils', 'entity', 'Logro', 'UserLogro'];

    function LogroDialogController ($timeout, $scope, $stateParams, $uibModalInstance, DataUtils, entity, Logro, UserLogro) {
        var vm = this;

        vm.logro = entity;
        vm.clear = clear;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;
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


        vm.setIcono = function ($file, logro) {
            if ($file && $file.$error === 'pattern') {
                return;
            }
            if ($file) {
                DataUtils.toBase64($file, function(base64Data) {
                    $scope.$apply(function() {
                        logro.icono = base64Data;
                        logro.iconoContentType = $file.type;
                    });
                });
            }
        };

    }
})();
