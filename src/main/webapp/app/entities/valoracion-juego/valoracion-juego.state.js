(function() {
    'use strict';

    angular
        .module('proyectoGlobalGamingApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('valoracion-juego', {
            parent: 'entity',
            url: '/valoracion-juego?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'proyectoGlobalGamingApp.valoracionJuego.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/valoracion-juego/valoracion-juegos.html',
                    controller: 'ValoracionJuegoController',
                    controllerAs: 'vm'
                }
            },
            params: {
                page: {
                    value: '1',
                    squash: true
                },
                sort: {
                    value: 'id,asc',
                    squash: true
                },
                search: null
            },
            resolve: {
                pagingParams: ['$stateParams', 'PaginationUtil', function ($stateParams, PaginationUtil) {
                    return {
                        page: PaginationUtil.parsePage($stateParams.page),
                        sort: $stateParams.sort,
                        predicate: PaginationUtil.parsePredicate($stateParams.sort),
                        ascending: PaginationUtil.parseAscending($stateParams.sort),
                        search: $stateParams.search
                    };
                }],
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('valoracionJuego');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('valoracion-juego-detail', {
            parent: 'entity',
            url: '/valoracion-juego/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'proyectoGlobalGamingApp.valoracionJuego.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/valoracion-juego/valoracion-juego-detail.html',
                    controller: 'ValoracionJuegoDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('valoracionJuego');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'ValoracionJuego', function($stateParams, ValoracionJuego) {
                    return ValoracionJuego.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'valoracion-juego',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('valoracion-juego-detail.edit', {
            parent: 'valoracion-juego-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/valoracion-juego/valoracion-juego-dialog.html',
                    controller: 'ValoracionJuegoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ValoracionJuego', function(ValoracionJuego) {
                            return ValoracionJuego.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('valoracion-juego.new', {
            parent: 'valoracion-juego',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/valoracion-juego/valoracion-juego-dialog.html',
                    controller: 'ValoracionJuegoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                valoracion: null,
                                timeStamp: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('valoracion-juego', null, { reload: 'valoracion-juego' });
                }, function() {
                    $state.go('valoracion-juego');
                });
            }]
        })
        .state('valoracion-juego.edit', {
            parent: 'valoracion-juego',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/valoracion-juego/valoracion-juego-dialog.html',
                    controller: 'ValoracionJuegoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ValoracionJuego', function(ValoracionJuego) {
                            return ValoracionJuego.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('valoracion-juego', null, { reload: 'valoracion-juego' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('valoracion-juego.delete', {
            parent: 'valoracion-juego',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/valoracion-juego/valoracion-juego-delete-dialog.html',
                    controller: 'ValoracionJuegoDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['ValoracionJuego', function(ValoracionJuego) {
                            return ValoracionJuego.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('valoracion-juego', null, { reload: 'valoracion-juego' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
