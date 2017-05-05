(function() {
    'use strict';

    angular
        .module('proyectoGlobalGamingApp')
        .controller('UserExtDialogController', UserExtDialogController);

    UserExtDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'DataUtils', 'entity','Pais', 'UserExt', 'User'];

    function UserExtDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, DataUtils, entity,Pais, UserExt, User) {
        var vm = this;

        vm.userExt = entity;
        vm.clear = clear;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;
        vm.save = save;
        vm.pais = Pais.query();
        vm.users = User.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.userExt.id !== null) {
                UserExt.update(vm.userExt, onSaveSuccess, onSaveError);
            } else {
                UserExt.save(vm.userExt, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('proyectoGlobalGamingApp:userExtUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


        vm.setAvatar = function ($file, userExt) {
            if ($file && $file.$error === 'pattern') {
                return;
            }
            if ($file) {
                DataUtils.toBase64($file, function(base64Data) {
                    $scope.$apply(function() {
                        userExt.avatar = base64Data;
                        userExt.avatarContentType = $file.type;
                    });
                });
            }
        };

    }
})();
