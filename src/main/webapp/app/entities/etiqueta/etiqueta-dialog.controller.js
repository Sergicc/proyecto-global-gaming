(function() {
    'use strict';

    angular
        .module('proyectoGlobalGamingApp')
        .controller('EtiquetaDialogController', EtiquetaDialogController);

    EtiquetaDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Etiqueta', 'Articulo'];

    function EtiquetaDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Etiqueta, Articulo) {
        var vm = this;

        vm.etiqueta = entity;
        vm.clear = clear;
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
            if (vm.etiqueta.id !== null) {
                Etiqueta.update(vm.etiqueta, onSaveSuccess, onSaveError);
            } else {
                Etiqueta.save(vm.etiqueta, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('proyectoGlobalGamingApp:etiquetaUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
