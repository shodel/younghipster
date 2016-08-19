(function() {
    'use strict';

    angular
        .module('younghipsterApp')
        .controller('ProjektDeleteController',ProjektDeleteController);

    ProjektDeleteController.$inject = ['$uibModalInstance', 'entity', 'Projekt'];

    function ProjektDeleteController($uibModalInstance, entity, Projekt) {
        var vm = this;

        vm.projekt = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Projekt.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
