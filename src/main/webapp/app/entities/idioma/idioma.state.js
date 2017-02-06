(function() {
    'use strict';

    angular
        .module('proyectoGlobalGamingApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('idioma', {
            parent: 'entity',
            url: '/idioma',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'proyectoGlobalGamingApp.idioma.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/idioma/idiomas.html',
                    controller: 'IdiomaController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('idioma');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('idioma-detail', {
            parent: 'entity',
            url: '/idioma/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'proyectoGlobalGamingApp.idioma.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/idioma/idioma-detail.html',
                    controller: 'IdiomaDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('idioma');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Idioma', function($stateParams, Idioma) {
                    return Idioma.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'idioma',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('idioma-detail.edit', {
            parent: 'idioma-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/idioma/idioma-dialog.html',
                    controller: 'IdiomaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Idioma', function(Idioma) {
                            return Idioma.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('idioma.new', {
            parent: 'idioma',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/idioma/idioma-dialog.html',
                    controller: 'IdiomaDialogController',
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
                    $state.go('idioma', null, { reload: 'idioma' });
                }, function() {
                    $state.go('idioma');
                });
            }]
        })
        .state('idioma.edit', {
            parent: 'idioma',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/idioma/idioma-dialog.html',
                    controller: 'IdiomaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Idioma', function(Idioma) {
                            return Idioma.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('idioma', null, { reload: 'idioma' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('idioma.delete', {
            parent: 'idioma',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/idioma/idioma-delete-dialog.html',
                    controller: 'IdiomaDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Idioma', function(Idioma) {
                            return Idioma.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('idioma', null, { reload: 'idioma' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
