(function() {
    'use strict';

    angular
        .module('younghipsterApp')
        .controller('SpesenController', SpesenController);

    SpesenController.$inject = ['$scope', '$state', 'Spesen'];

    function SpesenController ($scope, $state, Spesen) {
        var vm = this;
        
        vm.spesens = [];

        loadAll();

        function loadAll() {
            Spesen.query(function(result) {
                vm.spesens = result;
            });
        }
    }
})();
