(function() {
    'use strict';

    angular
        .module('younghipsterApp')
        .controller('MitarbeiterController', MitarbeiterController);

    MitarbeiterController.$inject = ['$scope', '$state', 'Mitarbeiter'];

    function MitarbeiterController ($scope, $state, Mitarbeiter) {
        var vm = this;
        
        vm.mitarbeiters = [];

        loadAll();

        function loadAll() {
            Mitarbeiter.query(function(result) {
                vm.mitarbeiters = result;
            });
        }
    }
})();
