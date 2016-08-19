(function() {
    'use strict';

    angular
        .module('younghipsterApp')
        .controller('AufwandDialogController', AufwandDialogController);

    AufwandDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Aufwand', 'Projekt', 'Mitarbeiter'];

    function AufwandDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Aufwand, Projekt, Mitarbeiter) {
        var vm = this;

        vm.aufwand = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.projekts = Projekt.query();
        vm.mitarbeiters = Mitarbeiter.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.aufwand.id !== null) {
                Aufwand.update(vm.aufwand, onSaveSuccess, onSaveError);
            } else {
                Aufwand.save(vm.aufwand, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('younghipsterApp:aufwandUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.datum = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
