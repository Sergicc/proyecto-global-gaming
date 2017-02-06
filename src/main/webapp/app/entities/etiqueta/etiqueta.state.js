(function() {
    'use strict';

    angular
        .module('proyectoGlobalGamingApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('etiqueta', {
            parent: 'entity',
            url: '/etiqueta',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'proyectoGlobalGamingApp.etiqueta.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/etiqueta/etiquetas.html',
                    controller: 'EtiquetaController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('etiqueta');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('etiqueta-detail', {
            parent: 'entity',
            url: '/etiqueta/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'proyectoGlobalGamingApp.etiqueta.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/etiqueta/etiqueta-detail.html',
                    controller: 'EtiquetaDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('etiqueta');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Etiqueta', function($stateParams, Etiqueta) {
                    return Etiqueta.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'etiqueta',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('etiqueta-detail.edit', {
            parent: 'etiqueta-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/etiqueta/etiqueta-dialog.html',
                    controller: 'EtiquetaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Etiqueta', function(Etiqueta) {
                            return Etiqueta.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('etiqueta.new', {
            parent: 'etiqueta',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/etiqueta/etiqueta-dialog.html',
                    controller: 'EtiquetaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                nombre: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('etiqueta', null, { reload: 'etiqueta' });
                }, function() {
                    $state.go('etiqueta');
                });
            }]
        })
        .state('etiqueta.edit', {
            parent: 'etiqueta',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/etiqueta/etiqueta-dialog.html',
                    controller: 'EtiquetaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Etiqueta', function(Etiqueta) {
                            return Etiqueta.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('etiqueta', null, { reload: 'etiqueta' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('etiqueta.delete', {
            parent: 'etiqueta',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/etiqueta/etiqueta-delete-dialog.html',
                    controller: 'EtiquetaDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Etiqueta', function(Etiqueta) {
                            return Etiqueta.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('etiqueta', null, { reload: 'etiqueta' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
