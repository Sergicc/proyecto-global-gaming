(function() {
    'use strict';

    angular
        .module('proyectoGlobalGamingApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('articulo', {
            parent: 'entity',
            url: '/articulo',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'proyectoGlobalGamingApp.articulo.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/articulo/articulos.html',
                    controller: 'ArticuloController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('articulo');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('articulo-detail', {
            parent: 'entity',
            url: '/articulo/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'proyectoGlobalGamingApp.articulo.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/articulo/articulo-detail.html',
                    controller: 'ArticuloDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('articulo');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Articulo', function($stateParams, Articulo) {
                    return Articulo.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'articulo',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('articulo-detail.edit', {
            parent: 'articulo-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/articulo/articulo-dialog.html',
                    controller: 'ArticuloDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Articulo', function(Articulo) {
                            return Articulo.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('articulo.new', {
            parent: 'articulo',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/articulo/articulo-dialog.html',
                    controller: 'ArticuloDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                titulo: null,
                                texto: null,
                                fecha: null,
                                url: null,
                                visitas: null,
                                comentarios: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('articulo', null, { reload: 'articulo' });
                }, function() {
                    $state.go('articulo');
                });
            }]
        })
        .state('articulo.edit', {
            parent: 'articulo',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/articulo/articulo-dialog.html',
                    controller: 'ArticuloDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Articulo', function(Articulo) {
                            return Articulo.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('articulo', null, { reload: 'articulo' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('articulo.delete', {
            parent: 'articulo',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/articulo/articulo-delete-dialog.html',
                    controller: 'ArticuloDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Articulo', function(Articulo) {
                            return Articulo.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('articulo', null, { reload: 'articulo' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
