(function() {
    'use strict';

    angular
        .module('younghipsterApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('spesen', {
            parent: 'entity',
            url: '/spesen',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Spesens'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/spesen/spesens.html',
                    controller: 'SpesenController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('spesen-detail', {
            parent: 'entity',
            url: '/spesen/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Spesen'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/spesen/spesen-detail.html',
                    controller: 'SpesenDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Spesen', function($stateParams, Spesen) {
                    return Spesen.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'spesen',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('spesen-detail.edit', {
            parent: 'spesen-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/spesen/spesen-dialog.html',
                    controller: 'SpesenDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Spesen', function(Spesen) {
                            return Spesen.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('spesen.new', {
            parent: 'spesen',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/spesen/spesen-dialog.html',
                    controller: 'SpesenDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                datum: null,
                                posten: null,
                                betrag: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('spesen', null, { reload: true });
                }, function() {
                    $state.go('spesen');
                });
            }]
        })
        .state('spesen.edit', {
            parent: 'spesen',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/spesen/spesen-dialog.html',
                    controller: 'SpesenDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Spesen', function(Spesen) {
                            return Spesen.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('spesen', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('spesen.delete', {
            parent: 'spesen',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/spesen/spesen-delete-dialog.html',
                    controller: 'SpesenDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Spesen', function(Spesen) {
                            return Spesen.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('spesen', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
