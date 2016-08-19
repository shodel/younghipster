'use strict';

describe('Controller Tests', function() {

    describe('Aufwand Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockAufwand, MockProjekt, MockMitarbeiter;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockAufwand = jasmine.createSpy('MockAufwand');
            MockProjekt = jasmine.createSpy('MockProjekt');
            MockMitarbeiter = jasmine.createSpy('MockMitarbeiter');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Aufwand': MockAufwand,
                'Projekt': MockProjekt,
                'Mitarbeiter': MockMitarbeiter
            };
            createController = function() {
                $injector.get('$controller')("AufwandDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'younghipsterApp:aufwandUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
