(function() {
    'use strict';

    angular
        .module('younghipsterApp')
        .controller('SpesenDeleteController',SpesenDeleteController);

    SpesenDeleteController.$inject = ['$uibModalInstance', 'entity', 'Spesen'];

    function SpesenDeleteController($uibModalInstance, entity, Spesen) {
        var vm = this;

        vm.spesen = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Spesen.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
