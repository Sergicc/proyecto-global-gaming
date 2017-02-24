(function() {
    'use strict';

    angular
        .module('proyectoGlobalGamingApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('logro', {
            parent: 'entity',
            url: '/logro',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'proyectoGlobalGamingApp.logro.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/logro/logroes.html',
                    controller: 'LogroController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('logro');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('logro-detail', {
            parent: 'entity',
            url: '/logro/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'proyectoGlobalGamingApp.logro.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/logro/logro-detail.html',
                    controller: 'LogroDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('logro');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Logro', function($stateParams, Logro) {
                    return Logro.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'logro',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('logro-detail.edit', {
            parent: 'logro-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/logro/logro-dialog.html',
                    controller: 'LogroDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Logro', function(Logro) {
                            return Logro.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('logro.new', {
            parent: 'logro',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/logro/logro-dialog.html',
                    controller: 'LogroDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                nombre: null,
                                descripcion: null,
                                icono: null,
                                iconoContentType: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('logro', null, { reload: 'logro' });
                }, function() {
                    $state.go('logro');
                });
            }]
        })
        .state('logro.edit', {
            parent: 'logro',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/logro/logro-dialog.html',
                    controller: 'LogroDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Logro', function(Logro) {
                            return Logro.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('logro', null, { reload: 'logro' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('logro.delete', {
            parent: 'logro',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/logro/logro-delete-dialog.html',
                    controller: 'LogroDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Logro', function(Logro) {
                            return Logro.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('logro', null, { reload: 'logro' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
