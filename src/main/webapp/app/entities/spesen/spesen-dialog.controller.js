(function() {
    'use strict';

    angular
        .module('younghipsterApp')
        .controller('SpesenDialogController', SpesenDialogController);

    SpesenDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Spesen', 'Mitarbeiter', 'Projekt'];

    function SpesenDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Spesen, Mitarbeiter, Projekt) {
        var vm = this;

        vm.spesen = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.mitarbeiters = Mitarbeiter.query();
        vm.projekts = Projekt.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.spesen.id !== null) {
                Spesen.update(vm.spesen, onSaveSuccess, onSaveError);
            } else {
                Spesen.save(vm.spesen, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('younghipsterApp:spesenUpdate', result);
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
