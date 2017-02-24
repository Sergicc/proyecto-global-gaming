(function() {
    'use strict';

    angular
        .module('proyectoGlobalGamingApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('sala', {
            parent: 'entity',
            url: '/sala',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'proyectoGlobalGamingApp.sala.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/sala/salas.html',
                    controller: 'SalaController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('sala');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('sala-detail', {
            parent: 'entity',
            url: '/sala/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'proyectoGlobalGamingApp.sala.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/sala/sala-detail.html',
                    controller: 'SalaDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('sala');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Sala', function($stateParams, Sala) {
                    return Sala.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'sala',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('sala-detail.edit', {
            parent: 'sala-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/sala/sala-dialog.html',
                    controller: 'SalaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Sala', function(Sala) {
                            return Sala.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('sala.new', {
            parent: 'sala',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/sala/sala-dialog.html',
                    controller: 'SalaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                nombre: null,
                                imagen: null,
                                imagenContentType: null,
                                limiteUsuarios: null,
                                descripcion: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('sala', null, { reload: 'sala' });
                }, function() {
                    $state.go('sala');
                });
            }]
        })
        .state('sala.edit', {
            parent: 'sala',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/sala/sala-dialog.html',
                    controller: 'SalaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Sala', function(Sala) {
                            return Sala.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('sala', null, { reload: 'sala' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('sala.delete', {
            parent: 'sala',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/sala/sala-delete-dialog.html',
                    controller: 'SalaDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Sala', function(Sala) {
                            return Sala.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('sala', null, { reload: 'sala' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
