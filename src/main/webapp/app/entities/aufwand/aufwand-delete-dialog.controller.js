(function() {
    'use strict';

    angular
        .module('younghipsterApp')
        .controller('AufwandDeleteController',AufwandDeleteController);

    AufwandDeleteController.$inject = ['$uibModalInstance', 'entity', 'Aufwand'];

    function AufwandDeleteController($uibModalInstance, entity, Aufwand) {
        var vm = this;

        vm.aufwand = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Aufwand.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
