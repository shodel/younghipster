(function() {
    'use strict';

    angular
        .module('younghipsterApp')
        .controller('MitarbeiterDialogController', MitarbeiterDialogController);

    MitarbeiterDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Mitarbeiter'];

    function MitarbeiterDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Mitarbeiter) {
        var vm = this;

        vm.mitarbeiter = entity;
        vm.clear = clear;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.mitarbeiter.id !== null) {
                Mitarbeiter.update(vm.mitarbeiter, onSaveSuccess, onSaveError);
            } else {
                Mitarbeiter.save(vm.mitarbeiter, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('younghipsterApp:mitarbeiterUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
