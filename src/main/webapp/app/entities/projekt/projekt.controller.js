(function() {
    'use strict';

    angular
        .module('younghipsterApp')
        .controller('ProjektController', ProjektController);

    ProjektController.$inject = ['$scope', '$state', 'Projekt'];

    function ProjektController ($scope, $state, Projekt) {
        var vm = this;
        
        vm.projekts = [];

        loadAll();

        function loadAll() {
            Projekt.query(function(result) {
                vm.projekts = result;
            });
        }
    }
})();
