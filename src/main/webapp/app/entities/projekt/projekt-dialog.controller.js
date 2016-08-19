(function() {
    'use strict';

    angular
        .module('younghipsterApp')
        .controller('ProjektDialogController', ProjektDialogController);

    ProjektDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Projekt'];

    function ProjektDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Projekt) {
        var vm = this;

        vm.projekt = entity;
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
            if (vm.projekt.id !== null) {
                Projekt.update(vm.projekt, onSaveSuccess, onSaveError);
            } else {
                Projekt.save(vm.projekt, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('younghipsterApp:projektUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
