(function() {
    'use strict';

    angular
        .module('proyectoGlobalGamingApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('juego', {
            parent: 'entity',
            url: '/juego',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'proyectoGlobalGamingApp.juego.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/juego/juegos.html',
                    controller: 'JuegoController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('juego');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
            .state('buscadorJuego', {
                parent: 'entity',
                url: '/buscadorJuego',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'proyectoGlobalGamingApp.juego.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'app/entities/juego/buscadorJuego.html',
                        controller: 'JuegoController',
                        controllerAs: 'vm'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('juego');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
        .state('juego-detail', {
            parent: 'entity',
            url: '/juego/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'proyectoGlobalGamingApp.juego.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/juego/juego-detail.html',
                    controller: 'JuegoDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('juego');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Juego', function($stateParams, Juego) {
                    return Juego.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'juego',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('juego-detail.edit', {
            parent: 'juego-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/juego/juego-dialog.html',
                    controller: 'JuegoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Juego', function(Juego) {
                            return Juego.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('juego.new', {
            parent: 'juego',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/juego/juego-dialog.html',
                    controller: 'JuegoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                titulo: null,
                                portada: null,
                                portadaContentType: null,
                                descripcion: null,
                                trailer: null,
                                desarrollador: null,
                                genero: null,
                                edadRecomendada: null,
                                fechaLanzamiento: null,
                                capacidadJugadores: null,
                                valoracionWeb: null,
                                valoracionUsers: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('juego', null, { reload: 'juego' });
                }, function() {
                    $state.go('juego');
                });
            }]
        })
        .state('juego.edit', {
            parent: 'juego',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/juego/juego-dialog.html',
                    controller: 'JuegoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Juego', function(Juego) {
                            return Juego.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('juego', null, { reload: 'juego' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('juego.delete', {
            parent: 'juego',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/juego/juego-delete-dialog.html',
                    controller: 'JuegoDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Juego', function(Juego) {
                            return Juego.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('juego', null, { reload: 'juego' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
