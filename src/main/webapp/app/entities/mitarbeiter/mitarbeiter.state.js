(function() {
    'use strict';

    angular
        .module('younghipsterApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('mitarbeiter', {
            parent: 'entity',
            url: '/mitarbeiter',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Mitarbeiters'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/mitarbeiter/mitarbeiters.html',
                    controller: 'MitarbeiterController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('mitarbeiter-detail', {
            parent: 'entity',
            url: '/mitarbeiter/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Mitarbeiter'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/mitarbeiter/mitarbeiter-detail.html',
                    controller: 'MitarbeiterDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Mitarbeiter', function($stateParams, Mitarbeiter) {
                    return Mitarbeiter.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'mitarbeiter',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('mitarbeiter-detail.edit', {
            parent: 'mitarbeiter-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/mitarbeiter/mitarbeiter-dialog.html',
                    controller: 'MitarbeiterDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Mitarbeiter', function(Mitarbeiter) {
                            return Mitarbeiter.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('mitarbeiter.new', {
            parent: 'mitarbeiter',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/mitarbeiter/mitarbeiter-dialog.html',
                    controller: 'MitarbeiterDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                vorname: null,
                                nachname: null,
                                rolle: null,
                                wochenstunden: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('mitarbeiter', null, { reload: true });
                }, function() {
                    $state.go('mitarbeiter');
                });
            }]
        })
        .state('mitarbeiter.edit', {
            parent: 'mitarbeiter',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/mitarbeiter/mitarbeiter-dialog.html',
                    controller: 'MitarbeiterDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Mitarbeiter', function(Mitarbeiter) {
                            return Mitarbeiter.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('mitarbeiter', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('mitarbeiter.delete', {
            parent: 'mitarbeiter',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/mitarbeiter/mitarbeiter-delete-dialog.html',
                    controller: 'MitarbeiterDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Mitarbeiter', function(Mitarbeiter) {
                            return Mitarbeiter.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('mitarbeiter', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
