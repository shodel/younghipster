(function() {
    'use strict';

    angular
        .module('younghipsterApp')
        .controller('MitarbeiterDetailController', MitarbeiterDetailController);

    MitarbeiterDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Mitarbeiter'];

    function MitarbeiterDetailController($scope, $rootScope, $stateParams, previousState, entity, Mitarbeiter) {
        var vm = this;

        vm.mitarbeiter = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('younghipsterApp:mitarbeiterUpdate', function(event, result) {
            vm.mitarbeiter = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
