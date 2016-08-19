(function() {
    'use strict';

    angular
        .module('younghipsterApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('aufwand', {
            parent: 'entity',
            url: '/aufwand',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Aufwands'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/aufwand/aufwands.html',
                    controller: 'AufwandController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('aufwand-detail', {
            parent: 'entity',
            url: '/aufwand/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Aufwand'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/aufwand/aufwand-detail.html',
                    controller: 'AufwandDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Aufwand', function($stateParams, Aufwand) {
                    return Aufwand.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'aufwand',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('aufwand-detail.edit', {
            parent: 'aufwand-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/aufwand/aufwand-dialog.html',
                    controller: 'AufwandDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Aufwand', function(Aufwand) {
                            return Aufwand.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('aufwand.new', {
            parent: 'aufwand',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/aufwand/aufwand-dialog.html',
                    controller: 'AufwandDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                datum: null,
                                stunden: null,
                                typ: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('aufwand', null, { reload: true });
                }, function() {
                    $state.go('aufwand');
                });
            }]
        })
        .state('aufwand.edit', {
            parent: 'aufwand',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/aufwand/aufwand-dialog.html',
                    controller: 'AufwandDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Aufwand', function(Aufwand) {
                            return Aufwand.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('aufwand', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('aufwand.delete', {
            parent: 'aufwand',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/aufwand/aufwand-delete-dialog.html',
                    controller: 'AufwandDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Aufwand', function(Aufwand) {
                            return Aufwand.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('aufwand', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
