(function() {
    'use strict';

    angular
        .module('proyectoGlobalGamingApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('user-ext', {
            parent: 'entity',
            url: '/user-ext',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'proyectoGlobalGamingApp.userExt.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/user-ext/user-exts.html',
                    controller: 'UserExtController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('userExt');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('user-ext-detail', {
            parent: 'entity',
            url: '/user-ext/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'proyectoGlobalGamingApp.userExt.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/user-ext/user-ext-detail.html',
                    controller: 'UserExtDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('userExt');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'UserExt', function($stateParams, UserExt) {
                    return UserExt.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'user-ext',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('user-ext-detail.edit', {
            parent: 'user-ext-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/user-ext/user-ext-dialog.html',
                    controller: 'UserExtDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['UserExt', function(UserExt) {
                            return UserExt.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('user-ext.new', {
            parent: 'user-ext',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/user-ext/user-ext-dialog.html',
                    controller: 'UserExtDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                avatar: null,
                                avatarContentType: null,
                                nick: null,
                                idBattlenet: null,
                                idSteam: null,
                                idOrigin: null,
                                idLol: null,
                                pais: null,
                                puntos: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('user-ext', null, { reload: 'user-ext' });
                }, function() {
                    $state.go('user-ext');
                });
            }]
        })
        .state('user-ext.edit', {
            parent: 'user-ext',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/user-ext/user-ext-dialog.html',
                    controller: 'UserExtDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['UserExt', function(UserExt) {
                            return UserExt.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('user-ext', null, { reload: 'user-ext' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('user-ext.delete', {
            parent: 'user-ext',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/user-ext/user-ext-delete-dialog.html',
                    controller: 'UserExtDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['UserExt', function(UserExt) {
                            return UserExt.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('user-ext', null, { reload: 'user-ext' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
