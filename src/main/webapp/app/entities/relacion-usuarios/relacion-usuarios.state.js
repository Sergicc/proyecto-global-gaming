(function() {
    'use strict';

    angular
        .module('proyectoGlobalGamingApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('relacion-usuarios', {
            parent: 'entity',
            url: '/relacion-usuarios',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'proyectoGlobalGamingApp.relacionUsuarios.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/relacion-usuarios/relacion-usuarios.html',
                    controller: 'RelacionUsuariosController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('relacionUsuarios');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('relacion-usuarios-detail', {
            parent: 'entity',
            url: '/relacion-usuarios/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'proyectoGlobalGamingApp.relacionUsuarios.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/relacion-usuarios/relacion-usuarios-detail.html',
                    controller: 'RelacionUsuariosDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('relacionUsuarios');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'RelacionUsuarios', function($stateParams, RelacionUsuarios) {
                    return RelacionUsuarios.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'relacion-usuarios',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('relacion-usuarios-detail.edit', {
            parent: 'relacion-usuarios-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/relacion-usuarios/relacion-usuarios-dialog.html',
                    controller: 'RelacionUsuariosDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['RelacionUsuarios', function(RelacionUsuarios) {
                            return RelacionUsuarios.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('relacion-usuarios.new', {
            parent: 'relacion-usuarios',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/relacion-usuarios/relacion-usuarios-dialog.html',
                    controller: 'RelacionUsuariosDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                fechaEmision: null,
                                fechaResolucion: null,
                                resultado: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('relacion-usuarios', null, { reload: 'relacion-usuarios' });
                }, function() {
                    $state.go('relacion-usuarios');
                });
            }]
        })
        .state('relacion-usuarios.edit', {
            parent: 'relacion-usuarios',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/relacion-usuarios/relacion-usuarios-dialog.html',
                    controller: 'RelacionUsuariosDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['RelacionUsuarios', function(RelacionUsuarios) {
                            return RelacionUsuarios.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('relacion-usuarios', null, { reload: 'relacion-usuarios' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('relacion-usuarios.delete', {
            parent: 'relacion-usuarios',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/relacion-usuarios/relacion-usuarios-delete-dialog.html',
                    controller: 'RelacionUsuariosDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['RelacionUsuarios', function(RelacionUsuarios) {
                            return RelacionUsuarios.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('relacion-usuarios', null, { reload: 'relacion-usuarios' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
