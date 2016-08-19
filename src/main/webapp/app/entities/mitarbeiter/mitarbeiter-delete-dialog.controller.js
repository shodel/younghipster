(function() {
    'use strict';

    angular
        .module('younghipsterApp')
        .controller('MitarbeiterDeleteController',MitarbeiterDeleteController);

    MitarbeiterDeleteController.$inject = ['$uibModalInstance', 'entity', 'Mitarbeiter'];

    function MitarbeiterDeleteController($uibModalInstance, entity, Mitarbeiter) {
        var vm = this;

        vm.mitarbeiter = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Mitarbeiter.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
