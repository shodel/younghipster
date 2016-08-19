(function() {
    'use strict';

    angular
        .module('younghipsterApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('projekt', {
            parent: 'entity',
            url: '/projekt',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Projekts'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/projekt/projekts.html',
                    controller: 'ProjektController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('projekt-detail', {
            parent: 'entity',
            url: '/projekt/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Projekt'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/projekt/projekt-detail.html',
                    controller: 'ProjektDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Projekt', function($stateParams, Projekt) {
                    return Projekt.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'projekt',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('projekt-detail.edit', {
            parent: 'projekt-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/projekt/projekt-dialog.html',
                    controller: 'ProjektDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Projekt', function(Projekt) {
                            return Projekt.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('projekt.new', {
            parent: 'projekt',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/projekt/projekt-dialog.html',
                    controller: 'ProjektDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('projekt', null, { reload: true });
                }, function() {
                    $state.go('projekt');
                });
            }]
        })
        .state('projekt.edit', {
            parent: 'projekt',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/projekt/projekt-dialog.html',
                    controller: 'ProjektDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Projekt', function(Projekt) {
                            return Projekt.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('projekt', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('projekt.delete', {
            parent: 'projekt',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/projekt/projekt-delete-dialog.html',
                    controller: 'ProjektDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Projekt', function(Projekt) {
                            return Projekt.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('projekt', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
