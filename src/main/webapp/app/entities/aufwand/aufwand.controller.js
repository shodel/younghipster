(function() {
    'use strict';

    angular
        .module('younghipsterApp')
        .controller('AufwandController', AufwandController);

    AufwandController.$inject = ['$scope', '$state', 'Aufwand'];

    function AufwandController ($scope, $state, Aufwand) {
        var vm = this;
        
        vm.aufwands = [];

        loadAll();

        function loadAll() {
            Aufwand.query(function(result) {
                vm.aufwands = result;
            });
        }
    }
})();
