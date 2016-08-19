(function() {
    'use strict';

    angular
        .module('younghipsterApp')
        .controller('ProjektDetailController', ProjektDetailController);

    ProjektDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Projekt'];

    function ProjektDetailController($scope, $rootScope, $stateParams, previousState, entity, Projekt) {
        var vm = this;

        vm.projekt = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('younghipsterApp:projektUpdate', function(event, result) {
            vm.projekt = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
