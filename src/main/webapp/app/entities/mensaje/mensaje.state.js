(function() {
    'use strict';

    angular
        .module('proyectoGlobalGamingApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('mensaje', {
            parent: 'entity',
            url: '/mensaje',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'proyectoGlobalGamingApp.mensaje.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/mensaje/mensajes.html',
                    controller: 'MensajeController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('mensaje');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('mensaje-detail', {
            parent: 'entity',
            url: '/mensaje/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'proyectoGlobalGamingApp.mensaje.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/mensaje/mensaje-detail.html',
                    controller: 'MensajeDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('mensaje');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Mensaje', function($stateParams, Mensaje) {
                    return Mensaje.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'mensaje',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('mensaje-detail.edit', {
            parent: 'mensaje-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/mensaje/mensaje-dialog.html',
                    controller: 'MensajeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Mensaje', function(Mensaje) {
                            return Mensaje.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('mensaje.new', {
            parent: 'mensaje',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/mensaje/mensaje-dialog.html',
                    controller: 'MensajeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                horaEnvio: null,
                                contenido: null,
                                adjunto: null,
                                adjuntoContentType: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('mensaje', null, { reload: 'mensaje' });
                }, function() {
                    $state.go('mensaje');
                });
            }]
        })
        .state('mensaje.edit', {
            parent: 'mensaje',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/mensaje/mensaje-dialog.html',
                    controller: 'MensajeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Mensaje', function(Mensaje) {
                            return Mensaje.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('mensaje', null, { reload: 'mensaje' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('mensaje.delete', {
            parent: 'mensaje',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/mensaje/mensaje-delete-dialog.html',
                    controller: 'MensajeDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Mensaje', function(Mensaje) {
                            return Mensaje.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('mensaje', null, { reload: 'mensaje' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
