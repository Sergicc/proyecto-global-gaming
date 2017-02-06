(function() {
    'use strict';

    angular
        .module('proyectoGlobalGamingApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('user-logro', {
            parent: 'entity',
            url: '/user-logro',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'proyectoGlobalGamingApp.userLogro.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/user-logro/user-logroes.html',
                    controller: 'UserLogroController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('userLogro');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('user-logro-detail', {
            parent: 'entity',
            url: '/user-logro/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'proyectoGlobalGamingApp.userLogro.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/user-logro/user-logro-detail.html',
                    controller: 'UserLogroDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('userLogro');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'UserLogro', function($stateParams, UserLogro) {
                    return UserLogro.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'user-logro',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('user-logro-detail.edit', {
            parent: 'user-logro-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/user-logro/user-logro-dialog.html',
                    controller: 'UserLogroDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['UserLogro', function(UserLogro) {
                            return UserLogro.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('user-logro.new', {
            parent: 'user-logro',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/user-logro/user-logro-dialog.html',
                    controller: 'UserLogroDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                fecha: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('user-logro', null, { reload: 'user-logro' });
                }, function() {
                    $state.go('user-logro');
                });
            }]
        })
        .state('user-logro.edit', {
            parent: 'user-logro',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/user-logro/user-logro-dialog.html',
                    controller: 'UserLogroDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['UserLogro', function(UserLogro) {
                            return UserLogro.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('user-logro', null, { reload: 'user-logro' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('user-logro.delete', {
            parent: 'user-logro',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/user-logro/user-logro-delete-dialog.html',
                    controller: 'UserLogroDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['UserLogro', function(UserLogro) {
                            return UserLogro.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('user-logro', null, { reload: 'user-logro' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
