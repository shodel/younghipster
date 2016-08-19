(function() {
    'use strict';

    angular
        .module('younghipsterApp')
        .controller('SpesenDetailController', SpesenDetailController);

    SpesenDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Spesen', 'Mitarbeiter', 'Projekt'];

    function SpesenDetailController($scope, $rootScope, $stateParams, previousState, entity, Spesen, Mitarbeiter, Projekt) {
        var vm = this;

        vm.spesen = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('younghipsterApp:spesenUpdate', function(event, result) {
            vm.spesen = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
