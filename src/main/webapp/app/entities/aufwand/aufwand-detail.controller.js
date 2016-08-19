(function() {
    'use strict';

    angular
        .module('younghipsterApp')
        .controller('AufwandDetailController', AufwandDetailController);

    AufwandDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Aufwand', 'Projekt', 'Mitarbeiter'];

    function AufwandDetailController($scope, $rootScope, $stateParams, previousState, entity, Aufwand, Projekt, Mitarbeiter) {
        var vm = this;

        vm.aufwand = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('younghipsterApp:aufwandUpdate', function(event, result) {
            vm.aufwand = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
