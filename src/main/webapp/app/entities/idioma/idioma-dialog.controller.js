(function() {
    'use strict';

    angular
        .module('proyectoGlobalGamingApp')
        .controller('IdiomaDialogController', IdiomaDialogController);

    IdiomaDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Idioma', 'Juego', 'Sala'];

    function IdiomaDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Idioma, Juego, Sala) {
        var vm = this;

        vm.idioma = entity;
        vm.clear = clear;
        vm.save = save;
        vm.juegos = Juego.query();
        vm.salas = Sala.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.idioma.id !== null) {
                Idioma.update(vm.idioma, onSaveSuccess, onSaveError);
            } else {
                Idioma.save(vm.idioma, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('proyectoGlobalGamingApp:idiomaUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
